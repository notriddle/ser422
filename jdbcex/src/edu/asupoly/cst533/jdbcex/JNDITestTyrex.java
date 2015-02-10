//using tyrex

package edu.asupoly.cst533.jdbcex;

import javax.naming.*;
import java.util.Hashtable;

public class JNDITestTyrex {

	public static void main(String args[]) {

		if (args.length < 2 || args.length % 2 == 1) {
			System.out.println("USAGE: java edu.asupoly.cst533.jdbcex.JNDITestTyrex object name [object name|...]");
			System.exit(0);
		}

		// Make ctx points to a path within the shared space
		Hashtable env = new Hashtable();
		env.put( Context.INITIAL_CONTEXT_FACTORY, 
		"tyrex.naming.MemoryContextFactory" );
		env.put( Context.PROVIDER_URL, "cet533" );

		try {
			Context tyrexCtx = new InitialContext( env );
			StoreObject so = null;	
			for (int i = 0; i < args.length; i = i+2) {
				so = new StoreObject(args[i+1]);
				System.out.println("Binding " + args[i] + " to " + so.getString());
				tyrexCtx.bind(args[i], so);
			}

			System.out.println("\n***DONE PUTTING OBJECTS IN NAMING SERVICE***\n");

			for (int i = 0; i < args.length; i = i+2) {
				System.out.print("Retrieving " + args[i]);
				so = (StoreObject)tyrexCtx.lookup(args[i]);
				System.out.println(": " + so.getString());
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		System.exit(0);
	}

}

class StoreObject implements java.io.Serializable {
	StoreObject(String s) { __str = s; }

	String getString() { return __str; }

	private String __str = "NOT INITIALIZED";
}
