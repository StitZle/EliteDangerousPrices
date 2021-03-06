package eliteDangerousRestUpdater.handler.stationHandler;

import static eliteDangerousRestUpdater.utils.Constants.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONObject;

import com.google.gson.Gson;

import eliteDangerousRestUpdater.functions.DatabaseHandler;
import eliteDangerousRestUpdater.functions.SystemLogger;
import eliteDangerousRestUpdater.functions.TimeMeasurment;


public class Stations
{
	SystemLogger systemLogger = SystemLogger.getInstance();

	String className = this.getClass().getSimpleName();

	DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

	TimeMeasurment time = new TimeMeasurment();


	public void insertStations()
	{

		Gson gson = new Gson();

		int count = 0;
		int batchSize = 25000;

		Connection connection = databaseHandler.connect();
		PreparedStatement preparedStatement = null;

		time.startTime();

		try
		{
			preparedStatement = connection.prepareStatement( SQL_STATIONS );
			connection.setAutoCommit( false );
		}
		catch( SQLException e )
		{
			systemLogger.error( className, "SQLException: " + e.getMessage() );
		}

		try (BufferedReader br = Files.newBufferedReader( Paths.get( SAVE_PATH_STATIONS ), StandardCharsets.UTF_8 ))
		{
			for( String line = null; ( line = br.readLine() ) != null; )
			{
				try
				{
					JSONObject jsonObject = new JSONObject( line );
					StationsGson stationsGson = gson.fromJson( jsonObject.toString(), StationsGson.class );

					if( stationsGson.getHasCommodities() == false )
					{
						stationsGson.setMarketUpdatedAt( 0 );
					}
					if( stationsGson.getDistanceToStar() == null )
					{
						stationsGson.setDistanceToStar( 0 );
					}
					try
					{

						preparedStatement.setLong( 1, stationsGson.getId() );
						preparedStatement.setLong( 2, stationsGson.getSystemId() );
						preparedStatement.setString( 3, stationsGson.getName() );
						preparedStatement.setLong( 4, stationsGson.getUpdatedAt() );

						preparedStatement.setString( 5, stationsGson.getMaxLandingPadSize() );
						preparedStatement.setFloat( 6, stationsGson.getDistanceToStar() );
						preparedStatement.setBoolean( 7, stationsGson.getHasCommodities() );

						preparedStatement.setLong( 8, stationsGson.getMarketUpdatedAt() );
						preparedStatement.setBoolean( 9, stationsGson.getPlanetary() );

						preparedStatement.addBatch();

						count++;

						if( count % batchSize == 0 )
						{
							preparedStatement.executeBatch();
							preparedStatement.clearBatch();
						}

					}
					catch( SQLException e )
					{
						try
						{
							connection.rollback();
							systemLogger.warning( className, "Rollback in CommodityPrices" + e.getMessage() );
						}
						catch( SQLException e1 )
						{
							systemLogger.error( className, "SQLException: " + e.getMessage() );
						}
					}
				}
				catch( Exception e )
				{
					systemLogger.error( className, "Exception in Stations: " + e.getMessage() );
				}
			}
		}
		catch( FileNotFoundException e )
		{
			systemLogger.error( className, "File: " + SAVE_PATH_STATIONS + " not found. " + e.getMessage() );
		}
		catch( IOException e )
		{
			systemLogger.error( className, "Error in Stations, BufferedReader: " + e.getMessage() );
		}

		try
		{
			preparedStatement.executeBatch();
			connection.commit();
			connection.close();

			systemLogger.info( className, "Inserted rows: " + count );

		}
		catch( SQLException e )
		{
			systemLogger.error( className, "SQLException in GetStations: " + e.getMessage() );
		}

		time.measuretTime( className, "Inset in " + DB_TABLE_STATIONS );
	}
}
