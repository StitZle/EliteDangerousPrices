package eliteDangerousPrice.handler.commodityMappingHandler;

import static eliteDangerousPrice.utils.Constants.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.Gson;

import eliteDangerousPrice.functions.DatabaseHandler;
import eliteDangerousPrice.functions.SystemLogger;
import eliteDangerousPrice.functions.TimeMeasurment;


public class CommodityMapping
{
	SystemLogger systemLogger = SystemLogger.getInstance();

	String className = this.getClass().getSimpleName();

	DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

	TimeMeasurment time = new TimeMeasurment();


	public void insertCommodityMapping()
	{
		time.startTime();

		String jsonString = null;

		try (BufferedReader bufferedReader = new BufferedReader( new FileReader( SAVE_PATH_COMMODITY_MAPPING ) ))
		{
			jsonString = bufferedReader.readLine();
		}
		catch( FileNotFoundException e )
		{
			systemLogger.error( className, "Could not find file from Path: " + SAVE_PATH_COMMODITY_MAPPING );
		}
		catch( IOException e )
		{
			systemLogger.error( className, "IOException: " + e.getMessage() );
		}

		Gson gson = new Gson();
		CommodityMappingGson[] data = gson.fromJson( jsonString, CommodityMappingGson[].class );

		Connection connection = databaseHandler.connect();
		PreparedStatement preparedStatement = null;

		try
		{
			preparedStatement = connection.prepareStatement( SQL_COMMODITY_MAPPING );
			connection.setAutoCommit( false );
		}
		catch( SQLException e )
		{
			systemLogger.error( className, "SQLException: " + e.getMessage() );
		}

		int count = 0;

		for( count = 0; count < data.length; count++ )
		{
			try
			{
				preparedStatement.setInt( 1, data[count].getId() );
				preparedStatement.setString( 2, data[count].getName() );
				preparedStatement.addBatch();

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

		try
		{
			preparedStatement.executeBatch();
			connection.commit();
			connection.close();

			systemLogger.info( className, "Inserted rows: " + count );
		}
		catch( SQLException e )
		{
			systemLogger.error( className, "SQLException: " + e.getMessage() );
		}

		time.measuretTime( className, "Inset in " + DB_TABLE_COMMODITY_MAPPING );

	}
}
