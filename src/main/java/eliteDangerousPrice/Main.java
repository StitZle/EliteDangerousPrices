package eliteDangerousPrice;

import static eliteDangerousPrice.utils.Constants.*;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import eliteDangerousPrice.functions.Delete;
import eliteDangerousPrice.functions.Download;
import eliteDangerousPrice.functions.SystemLogger;
import eliteDangerousPrice.handler.commodityPricesHandler.csvReaderInsert;
import eliteDangerousPrice.functions.DatabaseHandler;
import eliteDangerousPrice.rest.NearbySystems;
import eliteDangerousPrice.handler.systemsHandler.SystemsReader;


public class Main
{

	SystemLogger systemLogger = SystemLogger.getInstance();


	public static void main( String[] args )
	{

		// Init
		Main main = new Main();
		main.init();

	}


	public void init()
	{

		try
		{
			systemLogger.newFile();
		}
		catch( FileNotFoundException | UnsupportedEncodingException e )
		{
			systemLogger.error( "File format is not supported. Or Encoding is wrong. " + e.getMessage() );
		}

		Download downloadler = new Download();
		csvReaderInsert csvReaderInsert = new csvReaderInsert();
		Delete delete = new Delete();

		DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
		databaseHandler.generateNewDatabase();
		databaseHandler.generateTables();
		SystemsReader jsonReader = new SystemsReader();
		NearbySystems getNearbySystems = new NearbySystems();

		final long timeStart = System.currentTimeMillis();

		//************************************************************//
		//						Commodity Prices					  //
		//************************************************************//
/*
		downloadler.download( DOWNLOAD_URL_STATION_COMMODITY , SAVE_PATH_STATION_COMMODITY );
		databaseHandler.dropTable(DB_TABLE_STATION_COMMODITY);
		csvReaderInsert.read();
		delete.delete(SAVE_PATH_STATION_COMMODITY);
*/

		//************************************************************//
		//						      Systems 	   					  //
		//************************************************************//

		//downloadler.download( DOWNLOAD_URL_SYSTEMS, SAVE_PATH_SYSTEMS );
		//databaseHandler.dropTable( DB_TABLE_SYSTEMS );
		//databaseHandler.dropTable( DB_TABLE_NEARBY_SYSTEMS );
		//jsonReader.readJson();
		//delete.delete( SAVE_PATH_SYSTEMS );

		//************************************************************//
		//						Commodity Mapping					  //
		//************************************************************//

		downloadler.download( DOWNLOAD_URL_COMMODITY_MAPPING, SAVE_PATH_COMMODITY_MAPPING );


		final long timeEnd = System.currentTimeMillis();
		long time = ( timeEnd - timeStart );
		System.out.println( time );
		systemLogger.info( "Hole action tooked:  " + time + " seconds." );

		//getNearbySystems.getDta("Sol", 100);
	}
}

