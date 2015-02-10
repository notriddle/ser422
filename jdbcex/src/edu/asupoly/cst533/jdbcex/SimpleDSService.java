package edu.asupoly.cst533.jdbcex;

import javax.naming.*;
import java.util.Hashtable;
import org.postgresql.jdbc2.optional.SimpleDataSource;
import org.postgresql.jdbc2.optional.PoolingDataSource;

public class SimpleDSService {

	// obviously these props should be configurable and not hardcoded
	public static boolean initSimpleDataSource(String server, String dbname,
			String user,   String passwd,
			int port, String name)  {
		boolean rval = true;

		SimpleDataSource theSource  = new SimpleDataSource();
		theSource.setServerName(server);
		theSource.setDatabaseName(dbname);
		theSource.setUser(user);
		theSource.setPassword(passwd);
		theSource.setPortNumber(port);

		// bind it under a JNDI name
		// Make ctx points to a path within the shared space
		Hashtable env = new Hashtable();
		env.put( Context.INITIAL_CONTEXT_FACTORY, 
		"tyrex.naming.MemoryContextFactory" );
		env.put( Context.PROVIDER_URL, "cet591db" );

		try {
			// also hardcoded, should not be
			Context ctx = new InitialContext(env);
			ctx.bind(name, theSource);
		}
		catch (NamingException exc) {
			exc.printStackTrace();
			rval = false;
		}
		return rval;
	}

	public static boolean initPooledDataSource(String server, String dbname,
			String user,   String passwd,
			int port, String name,
			int initConnections,
			int maxConnections)  {
		boolean rval = true;

		try {
			PoolingDataSource pooledSource  = new PoolingDataSource();
			pooledSource.setServerName(server);
			pooledSource.setDatabaseName(dbname);
			pooledSource.setUser(user);
			pooledSource.setPassword(passwd);
			pooledSource.setPortNumber(port);
			pooledSource.setDataSourceName(name); // note we do this here
			pooledSource.setInitialConnections(initConnections);
			pooledSource.setMaxConnections(maxConnections);

			// bind it under a JNDI name
			// Make ctx points to a path within the shared space
			Hashtable env = new Hashtable();
			env.put( Context.INITIAL_CONTEXT_FACTORY, 
			"tyrex.naming.MemoryContextFactory" );
			env.put( Context.PROVIDER_URL, "cet591db" );

			// also hardcoded, should not be
			Context ctx = new InitialContext(env);
			ctx.bind(name, pooledSource);
		}
		catch (NamingException exc) {
			exc.printStackTrace();
			rval = false;
		}
		catch (Exception exc2) {
			exc2.printStackTrace();
			rval = false;
		}
		return rval;
	}
}
