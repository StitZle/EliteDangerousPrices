package eliteDangerousPrice.handler.commodityPricesHandler;

import static eliteDangerousPrice.utils.Constants.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import eliteDangerousPrice.functions.DatabaseHandler;
import eliteDangerousPrice.functions.SystemLogger;
import eliteDangerousPrice.functions.TimeMeasurment;


public class CommodityPrices
{
	SystemLogger systemLogger = SystemLogger.getInstance();

	String className = this.getClass().getSimpleName();

	DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

	TimeMeasurment time = new TimeMeasurment();


	public void insertCommodityPrices()
	{

		time.startTime();

		Connection connection = databaseHandler.connect();

		PreparedStatement preparedStatement = null;
		try
		{
			preparedStatement = connection.prepareStatement( SQL_STATION_COMMODITY );
			connection.setAutoCommit( false );
		}
		catch( SQLException e )
		{
			systemLogger.error( className, "SQLException: " + e.getMessage() );
		}

		float count = 0;
		int batchSize = 25000;

		try
		{
			Reader reader = new FileReader( SAVE_PATH_STATION_COMMODITY );
			Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse( reader );
			for( CSVRecord record : records )
			{
				Long ID = Long.valueOf( record.get( "id" ) );
				Long station_ID = Long.valueOf( record.get( "station_id" ) );
				Long commodityID = Long.valueOf( record.get( "commodity_id" ) );
				Long supply = Long.valueOf( record.get( "supply" ) );
				Long buyPrice = Long.valueOf( record.get( "buy_price" ) );
				Long sellPrice = Long.valueOf( record.get( "sell_price" ) );
				Long demand = Long.valueOf( record.get( "demand" ) );
				Long collectedAt = Long.valueOf( record.get( "collected_at" ) );

				try
				{
					preparedStatement.setLong( 1, ID );
					preparedStatement.setLong( 2, station_ID );
					preparedStatement.setLong( 3, commodityID );
					preparedStatement.setLong( 4, supply );
					preparedStatement.setLong( 5, buyPrice );
					preparedStatement.setLong( 6, sellPrice );
					preparedStatement.setLong( 7, demand );
					preparedStatement.setLong( 8, collectedAt );
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

			preparedStatement.executeBatch();

			reader.close();
			connection.commit();
			connection.close();

			systemLogger.info( className, "Inserted rows: " + count );
		}
		catch( FileNotFoundException e )
		{
			systemLogger.error( className, "Could not find file from Path: " + SAVE_PATH_STATION_COMMODITY );
		}
		catch( IOException e )
		{
			systemLogger.error( className, "IOException: " + e.getMessage() );
		}
		catch( SQLException e )
		{
			systemLogger.error( className, "SQLException: " + e.getMessage() );
		}

		time.measuretTime( className, "Inset in " + DB_TABLE_STATION_COMMODITY );
	}
}
