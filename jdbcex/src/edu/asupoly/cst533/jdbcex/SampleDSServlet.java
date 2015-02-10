package edu.asupoly.cst533.jdbcex;

import javax.naming.*;
import javax.sql.DataSource;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SampleDSServlet extends HttpServlet {
    private static Connection conn = null;

    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;
        PrintWriter out = res.getWriter();
        StringBuffer pageBuffer = new StringBuffer();
        pageBuffer
                .append("<html>\n<head>\n<title>Final Exam</title>\n</head>\n<body>\n"
                        + "<H3>Employees:</H3>\n");
        String lname = req.getParameter("lastname");
        try {
            Context initCtx = new InitialContext();
            Context ctx = (Context) initCtx.lookup("java:comp/env");
            /* I have been unable to get datasource configurations to work inside server.xml */
            DataSource ds2 = (DataSource) ctx.lookup("jdbc/cst533DemoDB-pool2");
            conn = ds2.getConnection();
            stmt = conn
                    .prepareStatement("SELECT last_name, first_name FROM Authors WHERE last_name LIKE ?");
            stmt.setString(1, lname);
            rs = stmt.executeQuery();
            pageBuffer
                    .append("<table>\n<tr><td>Last Name</td><td>First Name</td></tr>\n");
            while (rs.next()) {
                pageBuffer.append("<tr><td>" + rs.getString("last_name")
                        + "</td><td>");
                pageBuffer.append(rs.getString("first_name"));
                pageBuffer.append("</td></tr>\n");
            }
            rs.close();
            rs = null;
            stmt.close();
            stmt = null;
            conn.close();
            conn = null;

        } catch (SQLException se) {
            se.printStackTrace();
            pageBuffer.append("\n\nERROR processing query\n");
        } catch (Throwable t) {
            t.printStackTrace();
            pageBuffer.append("\n\nUnknown ERROR\n");
        } finally {
            try {
                pageBuffer.append("</table>\n</body>\n</html>\n");
                out.println(pageBuffer.toString());
            } catch (Exception exc) {
                exc.printStackTrace();
            }
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException sexc) {
                sexc.printStackTrace();
            }
        }
    }
}
