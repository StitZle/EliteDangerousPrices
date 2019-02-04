package eliteDangerousRestUpdater.functions;

import static eliteDangerousRestUpdater.utils.Constants.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseHandler
{
	SystemLogger systemLogger = SystemLogger.getInstance();

	String className = this.getClass().getSimpleName();

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
			systemLogger.error( className, "SQLExecption: " + e.getMessage() );
		}
		return connection;
	}


	public void generateNewDatabase()
	{
		try (Connection conn = connect())
		{
			systemLogger.info( className, "Connection is: " + conn );
			if( conn != null )
			{
				DatabaseMetaData meta = conn.getMetaData();
				systemLogger.info( className, "The database driver name is: " + meta.getDriverName() );
				systemLogger.info( className, "Database driver version: " + meta.getDriverVersion() );
				systemLogger.info( className, "Database Path: " + DB_URL + DB_NAME );
			}
		}
		catch( SQLException e )
		{
			systemLogger.error( className, "SQLExecption: " + e );
		}
	}


	public void generateTables()
	{
		String tablePrice = "CREATE TABLE IF NOT EXISTS " + DB_TABLE_STATION_COMMODITY + "(\n"
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

		String tableCommodityMapping = "CREATE TABLE IF NOT EXISTS " + DB_TABLE_COMMODITY_MAPPING + " (\n"
				+ "id integer PRIMARY KEY, \n"
				+ "name text\n"
				+ ");";

		String tableStations = "CREATE TABLE IF NOT EXISTS " + DB_TABLE_STATIONS + " (\n"
				+ "id integer PRIMARY KEY, \n"
				+ "system_id integer,\n"
				+ "name text,\n"
				+ "updated_at integer,\n"
				+ "max_landing_pad_size text,\n"
				+ "distance_to_star real,\n"
				+ "has_commodities integer,\n"
				+ "market_updated_at integer,\n"
				+ "is_planetary integer\n"
				+ ");";

		try (Connection conn = connect();
				Statement stmt = conn.createStatement())
		{

			stmt.execute( tablePrice );
			stmt.execute( tableSystems );
			stmt.execute( tableCommodityMapping );
			stmt.execute( tableStations );
			systemLogger.info( className, "Created table: " + DB_TABLE_STATION_COMMODITY );
			systemLogger.info( className, "Created table: " + DB_TABLE_SYSTEMS );
			systemLogger.info( className, "Created table: " + DB_TABLE_COMMODITY_MAPPING );
			systemLogger.info( className, "Created table: " + DB_TABLE_STATIONS );

		}
		catch( SQLException e )
		{
			systemLogger.error( className, "SQLExecption: " + e.getMessage() );
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

			systemLogger.info( className, "Successfully droped table: " + savePath + ". Dropping last " + time
					+ " seconds." );

		}
		catch( SQLException e )
		{
			systemLogger.error( className, "SQLException: " + e.getMessage() );
		}

	}
}
