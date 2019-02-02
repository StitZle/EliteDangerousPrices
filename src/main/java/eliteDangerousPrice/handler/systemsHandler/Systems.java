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
import eliteDangerousPrice.functions.TimeMeasurment;


public class Systems
{
	SystemLogger systemLogger = SystemLogger.getInstance();

	String className = this.getClass().getSimpleName();

	DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

	TimeMeasurment time = new TimeMeasurment();


	public void insertSystems()
	{

		time.startTime();

		Gson gson = new Gson();

		int count = 0;
		int batchSize = 5000;

		Connection connection = databaseHandler.connect();
		PreparedStatement preparedStatement = null;

		try
		{
			preparedStatement = connection.prepareStatement( SQL_SYSTEMS_INSERT );
			connection.setAutoCommit( false );
		}
		catch( SQLException e )
		{
			systemLogger.error( className, "SQLException: " + e.getMessage() );
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
					systemLogger.error( className, "Exception: " + e.getMessage() );
				}
			}
		}
		catch( FileNotFoundException e )
		{
			systemLogger.error( className, "File: " + SAVE_PATH_SYSTEMS + " not found. " + e.getMessage() );
		}
		catch( IOException e )
		{
			systemLogger.error( className, "Error in Systems, BufferedReader: " + e.getMessage() );
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
			systemLogger.error( className, "Exception: " + e.getMessage() );
		}
		time.measuretTime( className, "Inset in " + DB_TABLE_SYSTEMS );
	}
}
