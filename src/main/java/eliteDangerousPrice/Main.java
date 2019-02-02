package eliteDangerousPrice;

import static eliteDangerousPrice.utils.Constants.*;

import eliteDangerousPrice.functions.Delete;
import eliteDangerousPrice.functions.Download;
import eliteDangerousPrice.functions.SystemLogger;
import eliteDangerousPrice.functions.TimeMeasurment;
import eliteDangerousPrice.handler.commodityMappingHandler.CommodityMapping;
import eliteDangerousPrice.handler.commodityPricesHandler.CommodityPrices;
import eliteDangerousPrice.functions.DatabaseHandler;
import eliteDangerousPrice.handler.stationHandler.Stations;
import eliteDangerousPrice.handler.systemsHandler.Systems;
import eliteDangerousPrice.rest.NearbySystems;


public class Main
{

	SystemLogger systemLogger = SystemLogger.getInstance();

	String className = this.getClass().getSimpleName();

	DatabaseHandler databaseHandler = DatabaseHandler.getInstance();


	public static void main( String[] args )
	{

		// Init
		Main main = new Main();
		main.init();

	}


	public void init()
	{

		TimeMeasurment time = new TimeMeasurment();

		time.startTime();

		systemLogger.newFile();

		databaseHandler.generateNewDatabase();
		databaseHandler.generateTables();

		Download downloadler = new Download();

		CommodityPrices commodityPrices = new CommodityPrices();

		Delete delete = new Delete();

		Systems jsystems = new Systems();

		CommodityMapping commodityMapping = new CommodityMapping();

		Stations insertStations = new Stations();




		//************************************************************//
		//						Commodity Prices					  //
		//************************************************************//

		downloadler.download( DOWNLOAD_URL_STATION_COMMODITY , SAVE_PATH_STATION_COMMODITY );
		databaseHandler.dropTable(DB_TABLE_STATION_COMMODITY);
		commodityPrices.insertCommodityPrices();
		delete.delete(SAVE_PATH_STATION_COMMODITY);


		//************************************************************//
		//					    Systems 	   					      //
		//************************************************************//

		downloadler.download( DOWNLOAD_URL_SYSTEMS, SAVE_PATH_SYSTEMS );
		databaseHandler.dropTable( DB_TABLE_SYSTEMS );
		jsystems.insertSystems();
		delete.delete( SAVE_PATH_SYSTEMS );


		//************************************************************//
		//						Commodity Mapping					  //
		//************************************************************//

		downloadler.download( DOWNLOAD_URL_COMMODITY_MAPPING, SAVE_PATH_COMMODITY_MAPPING );
		databaseHandler.dropTable( DB_TABLE_COMMODITY_MAPPING );
		commodityMapping.insertCommodityMapping();
		delete.delete( SAVE_PATH_COMMODITY_MAPPING );

		//************************************************************//
		//						Stations					          //
		//************************************************************//

		downloadler.download( DOWNLOAD_URL_STATIONS, SAVE_PATH_STATIONS );
		databaseHandler.dropTable( DB_TABLE_STATIONS );
		insertStations.insertStations();
		delete.delete( SAVE_PATH_STATIONS );

		time.measuretTime( className, "Complete program" );

		NearbySystems getNearbySystems = new NearbySystems();
		//getNearbySystems.getData("Sol", 100);

		System.exit( 0 );
	}
}

