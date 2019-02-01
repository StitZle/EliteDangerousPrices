package eliteDangerousPrice.handler;

import static eliteDangerousPrice.utils.Constants.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import eliteDangerousPrice.functions.SystemLogger;


public class DatabaseHandler
{
	SystemLogger systemLogger = SystemLogger.getInstance();

	//Singelton Stuff
	private static final DatabaseHandler instace = new DatabaseHandler();


	private DatabaseHandler()
	{
	}


	public static DatabaseHandler getInstance()
	{
		return instace;
	}


	public Connection connect()
	{
		Connection connection = null;
		try
		{
			connection = DriverManager.getConnection( DB_URL + DB_NAME );
		}
		catch( SQLException e )
		{
			systemLogger.error( "SQLExecption:" + e );
		}
		return connection;
	}


	public void generateNewDatabase()
	{
		try (Connection conn = connect())
		{
			systemLogger.info( "Connection: " + conn );
			if( conn != null )
			{
				DatabaseMetaData meta = conn.getMetaData();
				systemLogger.info( "The database driver name is: " + meta.getDriverName() );
				systemLogger.info( "Database driver version: " + meta.getDriverVersion() );
				systemLogger.info( "Database Path: " + DB_URL + DB_NAME );
			}
		}
		catch( SQLException e )
		{
			systemLogger.error( "SQLExecption: " + e );
		}
	}


	public void generateTables()
	{
		String tablePrice = "CREATE TABLE IF NOT EXISTS " + DB_TABLE_STATION_COMMODITY + "(\n"
				+ "id integer PRIMARY KEY ,\n"
				+ "station_id integer,\n"
				+ "commodity_id integer,\n"
				+ "supply integer,\n"
				+ "buy_price integer,\n"
				+ "sell_price integer,\n"
				+ "demand integer,\n"
				+ "collected_at integer\n"
				+ ");";

		String tableSystems = "CREATE TABLE IF NOT EXISTS " + DB_TABLE_SYSTEMS + " (\n"
				+ "id integer PRIMARY KEY, \n"
				+ "edsm_id integer,\n"
				+ "system_name text,\n"
				+ "x_pos real,\n"
				+ "y_pos real,\n"
				+ "z_pos real,\n"
				+ "needs_permit integer,\n"
				+ "updated_at integer\n"
				+ ");";

		String tableNearbySystems = "CREATE TABLE IF NOT EXISTS " + DB_TABLE_NEARBY_SYSTEMS + " (\n"
				+ "fromSystem integer, \n"
				+ "toSystem integer,\n"
				+ "distance real\n"
				+ ");";

		try (Connection conn = connect();
				Statement stmt = conn.createStatement())
		{

			stmt.execute( tablePrice );
			stmt.execute( tableSystems );
			stmt.execute( tableNearbySystems );
			systemLogger.info( "Table name: " + DB_TABLE_STATION_COMMODITY );
			systemLogger.info( "Table name: " + DB_TABLE_SYSTEMS );
			systemLogger.info( "Table name: " + DB_TABLE_NEARBY_SYSTEMS );

		}
		catch( SQLException e )
		{
			systemLogger.error( "SQL ERROR in createTable: " + e.getMessage() );
		}
	}


	public void dropTable( String savePath )
	{

		final long timeStart = System.currentTimeMillis();

		String dropSQL = "DELETE FROM " + savePath;

		try (Connection connection = connect())
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate( dropSQL );
			connection.close();

			final long timeEnd = System.currentTimeMillis();
			long time = ( timeEnd - timeStart ) / 1000;

			systemLogger.info( "Successfully Droped Table: " + savePath + " Dropping last " + time
					+ " seconds." );

		}
		catch( SQLException e )
		{
			systemLogger.error( "SQL ERROR in dropTable: " + e.getMessage() );
		}

	}
}




/*
				+ "population real,\n"
				+ "government_id integer,\n"
				+ "government text,\n"
				+ "allegiance_id integer,\n"
				+ "allegiance text,\n"
				+ "security_id integer,\n"
				+ "security text,\n"
				+ "primary_economy_id integer,\n"
				+ "primary_economy text,\n"
				+ "power text,\n"
				+ "power_state text,\n"
				+ "power_state_id integer,\n"
 */