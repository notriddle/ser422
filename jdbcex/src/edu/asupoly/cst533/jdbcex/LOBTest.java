package edu.asupoly.cst533.jdbcex;

import java.io.*;

import edu.asupoly.cst533.Cst533DbWrapperException;
import edu.asupoly.cst533.IXRayService;
import edu.asupoly.cst533.XRayLOBStandardImpl;
import edu.asupoly.cst533.XRayLOBPostgresImpl;
import edu.asupoly.cst533.XRayLOBJavaImpl;

public class LOBTest {

    public static void main(String[] args) {

        if (args.length != 4) {
            System.out.println("USAGE: java edu.asupoly.cst533.jdbcex.LOBTest read|write  s|p|j <id> <filename>, where s is Standard impl, p is Postgres LargeObject, and J is Java.sql.Blob");
            System.exit(0);
        }

        File file = new File(args[3]);

        try {
            IXRayService xrayService = null;
            if (args[1].startsWith("s")) {
            		xrayService = new XRayLOBStandardImpl();
            } else if (args[1].startsWith("j")) {
            		xrayService = new XRayLOBJavaImpl();
            } else {
            		xrayService = new XRayLOBPostgresImpl();
            }
            
            if (args[0].equalsIgnoreCase("read")) {
                if (xrayService.readXRayImage(Integer.parseInt(args[2]), file)) {
                    System.out.println("Successfully wrote file: "
                            + file.getName());
                } else {
                    System.out.println("Failure writing file: "
                            + file.getName());
                }
            } else {
                if (xrayService.writeXRayImage(Integer.parseInt(args[2]), file)) {
                    System.out.println("Successfully read file: "
                            + file.getName());
                } else {
                    System.out.println("Failure reading file: "
                            + file.getName());
                }
            }

        } catch (Cst533DbWrapperException we) {
            we.printStackTrace();
            Throwable t = we.getCause();
            if (t != null) {
                System.out.println("\nROOT CAUSE:\n");
                t.printStackTrace();
            }
        }
        System.exit(0);
    }
}
