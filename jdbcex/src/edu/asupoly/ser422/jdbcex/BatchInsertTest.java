package edu.asupoly.ser422.jdbcex;

import java.sql.*;

public class BatchInsertTest {
	public static void main(String[] args) {

		Connection conn = null;
		Statement  stmt = null;

		if (args.length != 6) {
			System.out.println("USAGE: java edu.asu.ser422.jdbcex.BatchInsertTest <driver><url><user><passwd><num tuples><init id>");
			System.exit(0);
		}

		try {
			Class.forName(args[0]);
			conn = DriverManager.getConnection(args[1], args[2], args[3]);
			stmt = conn.createStatement();
			String sql = null;
			int initialId = Integer.parseInt(args[5]);
			long ts = System.currentTimeMillis();
			for (int i = 0; i < Integer.parseInt(args[4]); i++) {
				sql = "INSERT INTO TESTDATA VALUES (" + (initialId+i) + ", '" + i + "')";
				stmt.addBatch(sql);
			}
			stmt.executeBatch();
			long ts2 = System.currentTimeMillis();

			for (int i = 0; i < Integer.parseInt(args[4]); i++) {
				sql = "INSERT INTO TESTDATA VALUES (" + (initialId*2+i) + ", '" + i + "')";
				stmt.executeUpdate(sql);
			}
			long ts3 = System.currentTimeMillis();

			System.out.println("Batch   update took " + (ts2-ts));
			System.out.println("Regular update took " + (ts3-ts2));
			stmt.close();
		}
		catch (BatchUpdateException be) {
			be.printStackTrace();
			System.out.println("Threw BatchUpdateException, update counts: ");
			int[] counts = be.getUpdateCounts();
			for (int j = 0; counts != null && j < counts.length; j++) {
				System.out.print(counts[j] + " ");
			}
			System.out.println("\n\nUnwinding next exceptions:\n\n");
			Exception ne = be.getNextException();
			while (ne != null) {
				ne.printStackTrace();
				if (ne instanceof SQLException) {
					ne = ((SQLException)ne).getNextException();
				}
			}
		}
		catch (Exception se) {
			se.printStackTrace();
		}
		finally {
			try {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
		}
	}
}    
