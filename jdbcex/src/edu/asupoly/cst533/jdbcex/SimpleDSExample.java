package edu.asupoly.cst533.jdbcex;

import java.util.Hashtable;
import javax.naming.*;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import edu.asupoly.cst533.Cst533DbUtils;

public class SimpleDSExample {
	public static void main(String[] args) {
		Connection conn = null;
		Statement  stmt = null;
		ResultSet  rs   = null;

		if (args.length != 7)
		{
			System.out.println("USAGE: java edu.asupoly.cst533.SimpleDSExample <query><server><port><dbname><user><passwd><name>");
			System.exit(0);
		}
		// first init
		if (SimpleDSService.initSimpleDataSource(args[1], args[3], args[4], args[5],
				Integer.parseInt(args[2]), args[6])) {
			System.out.println("Datasource bound under name " + args[6]);
		}
		else {
			System.out.println("Error binding datasource, exiting");
			System.exit(0);
		}

		// get a connection
		try {
			Hashtable env = new Hashtable();
			env.put( Context.INITIAL_CONTEXT_FACTORY, 
					"tyrex.naming.MemoryContextFactory" );
			env.put( Context.PROVIDER_URL, "cet591db" );

			Context context = new InitialContext(env);
			DataSource ds = (DataSource)context.lookup(args[6]);
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(args[0]);
			Cst533DbUtils.printResultSet(rs);

			System.out.println("\nNow with a pooled source");
			String poolname = "pool" + args[6];
			if (SimpleDSService.initPooledDataSource(args[1], args[3], args[4], args[5],
					Integer.parseInt(args[2]), poolname,
					2, 10)) {
				System.out.println("Datasource bound under name " + poolname);
			}
			else {
				System.out.println("Error binding datasource, exiting");
				System.exit(0);
			}

			// do it again
			DataSource ds2 = (DataSource)context.lookup(poolname);
			conn = ds2.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(args[0]);
			Cst533DbUtils.printResultSet(rs);
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		finally {
			try {
				if (rs != null) { rs.close(); }
				if (stmt != null) { stmt.close(); }
				if (conn != null) { conn.close(); }
			}
			catch (Exception exc) {
				System.out.println("Exception cleaning up DB resources!");
				exc.printStackTrace();
			}
			System.exit(0);
		}
	}
}
