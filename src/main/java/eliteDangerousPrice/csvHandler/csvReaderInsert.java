package eliteDangerousPrice.csvHandler;

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

import eliteDangerousPrice.functions.SystemLogger;
import eliteDangerousPrice.handler.DatabaseHandler;


public class csvReaderInsert
{
	SystemLogger systemLogger = SystemLogger.getInstance();

	DatabaseHandler databaseHandler = DatabaseHandler.getInstance();


	public void read()
	{
		Connection connection = databaseHandler.connect();

		PreparedStatement preparedStatement = null;
		try
		{
			preparedStatement = connection.prepareStatement( SQL_STATION_COMMODITY );
			connection.setAutoCommit( false );
		}
		catch( SQLException e )
		{
			e.printStackTrace();
		}

		float count = 0;
		int batchSize = 25000;

		final long timeStart = System.currentTimeMillis();

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
						systemLogger.info( "Commit the Batch!" + count );

						preparedStatement.executeBatch();
						preparedStatement.clearBatch();

					}

				}
				catch( SQLException e )
				{
					try
					{
						connection.rollback();
						systemLogger.warning( "Rollback in csvReaderInsert" + e.getMessage() );
					}
					catch( SQLException e1 )
					{
						e1.printStackTrace();
						systemLogger.error( "Error: " + e.getMessage() );
					}
				}
			}

			preparedStatement.executeBatch();

			systemLogger.info( "Inserted last Batch!" );
			systemLogger.info( "Inserted rows: " + count );

			reader.close();
			connection.commit();
			connection.close();
			//( (CSVParser)records ).close();

			final long timeEnd = System.currentTimeMillis();
			long time = ( timeEnd - timeStart ) / 1000;
			systemLogger.info( "Last " + time + " seconds to inserted all rows from csv file to database." );

		}
		catch( FileNotFoundException e )
		{
			systemLogger.error( e.getMessage() );
		}
		catch( IOException e )
		{
			systemLogger.error( e.getMessage() );
		}
		catch( NumberFormatException e )
		{
			systemLogger.error( e.getMessage() );
		}
		catch( SQLException e )
		{
			systemLogger.error( e.getMessage() );
		}
	}
}
