package eliteDangerousPrice.handler.systemsHandler;

import static eliteDangerousPrice.utils.Constants.*;

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

import eliteDangerousPrice.functions.SystemLogger;
import eliteDangerousPrice.functions.DatabaseHandler;


public class SystemsReader
{
	SystemLogger systemLogger = SystemLogger.getInstance();

	DatabaseHandler databaseHandler = DatabaseHandler.getInstance();


	public void readJson()
	{

		Gson gson = new Gson();

		int count = 0;
		int batchSize = 5000;

		Connection connection = databaseHandler.connect();
		PreparedStatement preparedStatement = null;

		final long timeStart = System.currentTimeMillis();

		try
		{
			preparedStatement = connection.prepareStatement( SQL_SYSTEMS_INSERT );
			connection.setAutoCommit( false );
		}
		catch( SQLException e )
		{
			systemLogger.error( e.getMessage() );
		}

		try (BufferedReader br = Files.newBufferedReader( Paths.get( SAVE_PATH_SYSTEMS ), StandardCharsets.UTF_8 ))
		{
			for( String line = null; ( line = br.readLine() ) != null; )
			{
				try
				{
					JSONObject jsonObject = new JSONObject( line );
					SystemsGson systemsGson = gson.fromJson( jsonObject.toString(), SystemsGson.class );

					try
					{

						preparedStatement.setFloat( 1, systemsGson.getId() );
						preparedStatement.setFloat( 2, systemsGson.getEdsmId() );
						preparedStatement.setString( 3, systemsGson.getName() );

						preparedStatement.setFloat( 4, systemsGson.getX() );
						preparedStatement.setFloat( 5, systemsGson.getY() );
						preparedStatement.setFloat( 6, systemsGson.getZ() );

						preparedStatement.setBoolean( 7, systemsGson.getNeedsPermit() );
						preparedStatement.setLong( 8, systemsGson.getUpdatedAt() );

						preparedStatement.addBatch();

						count++;

						if( count % batchSize == 0 )
						{
							systemLogger.info( "Commit the Batch !" + count );
							preparedStatement.executeBatch();
							preparedStatement.clearBatch();
						}

					}
					catch( SQLException e )
					{
						systemLogger.error( "SQLException in SystemsReader: " + e.getMessage() );
					}
				}
				catch( Exception e )
				{
					systemLogger.error( "Exception in SystemsReader: " + e.getMessage() );
				}
			}
		}
		catch( FileNotFoundException e )
		{
			systemLogger.error( "File: " + SAVE_PATH_SYSTEMS + " not found. " + e.getMessage() );
		}
		catch( IOException e )
		{
			systemLogger.error( "Error in SystemsReader, BufferedReader: " + e.getMessage() );
		}

		try
		{
			preparedStatement.executeBatch();
			connection.commit();
			connection.close();

			systemLogger.info( "Inserted last Batch!" );
			systemLogger.info( "Inserted rows: " + count );

		}
		catch( SQLException e )
		{
			systemLogger.error( "SQLException in SystemsReader: " + e.getMessage() );
		}

		final long timeEnd = System.currentTimeMillis();
		long time = ( timeEnd - timeStart ) / 1000;
		systemLogger.info( "Last " + time + " seconds to inserted all rows from json file to database." );

	}
}


/*
						preparedStatement.setLong( 7, systemsGson.getPopulation() );
						preparedStatement.setInt( 8, systemsGson.getGovernmentId() );
						preparedStatement.setString( 9, systemsGson.getGovernment() );

						preparedStatement.setInt( 10, systemsGson.getAllegianceId() );
						preparedStatement.setString( 11, systemsGson.getAllegiance() );

						preparedStatement.setInt( 12, systemsGson.getSecurityId() );
						preparedStatement.setString( 13, systemsGson.getSecurity() );

						preparedStatement.setInt( 14, systemsGson.getPrimaryEconomyId() );
						preparedStatement.setString( 15, systemsGson.getPrimaryEconomy() );
						preparedStatement.setString( 16, systemsGson.getPower() );
						preparedStatement.setString( 17, systemsGson.getPowerState() );
						preparedStatement.setInt( 18, systemsGson.getPowerStateId() );
 */