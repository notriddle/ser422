package edu.asupoly.cst533;

import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;

import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

import edu.asupoly.cst533.Cst533DbWrapperException;
import edu.asupoly.cst533.Cst533DbUtils;

public final class XRayLOBStandardImpl implements IXRayService {
    private static final int __BUFSIZE = 2048;

    public boolean readXRayImage(int id, File outfile)
            throws Cst533DbWrapperException {
        byte[] buf = new byte[__BUFSIZE];
        int numBytesRead = 0;
        int numRead = 0;

        if (outfile.exists() && !outfile.canWrite()) {
            return false;
        }

        boolean oldAutoCommit = false;
        Connection conn = Cst533DbUtils.getConnection("kgary", "blah", "jdbc:postgresql://localhost/lobtest", "org.postgresql.Driver");
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        InputStream is = null;
        FileOutputStream os = null;
        try {
            oldAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false); // required by Postgres

            pstmt = conn.prepareStatement("SELECT Image FROM xray_image WHERE Image_ID=?");
            pstmt.setInt(1, id);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                is = rs.getBinaryStream(1);
                os = new FileOutputStream(outfile);

                while ((numRead = is.read(buf)) != -1) {
                    numBytesRead += numRead;
                    // push them back out to the output file
                    os.write(buf, 0, numRead);
                    os.flush(); // flush if we're buffered
                }
                is.close();
                is = null;
                os.flush();
                os.close();
                os = null;
            }
            rs.close();
            rs = null;
            pstmt.close();
            pstmt = null;
            conn.setAutoCommit(oldAutoCommit);
            Cst533DbUtils.releaseConnection(conn);
            conn = null;
            return true;
        } catch (IOException ie) {
            System.err.println("IO Exception reading Xray with id: " + id
                    + ", message: " + ie.getMessage());
            throw new Cst533DbWrapperException(ie);
        } catch (SQLException se) {
            System.err.println("SQL Exception reading Xray with id: " + id
                    + ", message: " + se.getMessage());
            throw new Cst533DbWrapperException(se);
        } finally {
            try {
                if (os != null)
                    os.close();
            } catch (Throwable t0) {
                t0.printStackTrace();
            }
            try {
                if (is != null)
                    is.close();
            } catch (Throwable t1) {
                t1.printStackTrace();
            }
            try {
                if (rs != null)
                    rs.close();
            } catch (Throwable t2) {
                t2.printStackTrace();
            }
            try {
                if (pstmt != null)
                    pstmt.close();
            } catch (Throwable t3) {
                t3.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.setAutoCommit(oldAutoCommit);
                    Cst533DbUtils.releaseConnection(conn);
                    conn = null;
                }
            } catch (Throwable t4) {
                t4.printStackTrace();
            }

        }
    }

    public boolean readXRayDiagnosis(int id, File file)
            throws Cst533DbWrapperException {
        return false;
    }

    public boolean writeXRayImage(int id, File file)
            throws Cst533DbWrapperException {
        FileInputStream fis = null;
        PreparedStatement ps = null;
        Connection conn = Cst533DbUtils.getConnection("kgary", "blah", "jdbc:postgresql://localhost/lobtest", "org.postgresql.Driver");
        boolean oldAutoCommit = true;

        try {
            fis = new FileInputStream(file);
            oldAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            ps = conn
                    .prepareStatement("INSERT INTO xray_image (image_id, idate, image) VALUES (?, ?, ?)");
            ps.setInt(1, id);
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis())); // or 'now'
            ps.setBinaryStream(3, fis, (int) file.length());
            ps.executeUpdate();
            ps.close();
            ps = null;
            fis.close();

            conn.setAutoCommit(oldAutoCommit);
            Cst533DbUtils.releaseConnection(conn);
            conn = null;
        } catch (Throwable t) {
            throw new Cst533DbWrapperException(
                    "XRayLOBStandardImpl::writeXRayImage", t);
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (Throwable t3) {
                t3.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.setAutoCommit(oldAutoCommit);
                    Cst533DbUtils.releaseConnection(conn);
                    conn = null;
                }
            } catch (Throwable t4) {
                t4.printStackTrace();
            }
        }
        return true;
    }

    public boolean writeXRayDiagnosis(int id, File file)
            throws Cst533DbWrapperException {
        return false;
    }

}
