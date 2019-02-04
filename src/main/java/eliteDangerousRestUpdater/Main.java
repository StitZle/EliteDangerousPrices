package eliteDangerousRestUpdater;

import eliteDangerousRestUpdater.functions.Delete;
import eliteDangerousRestUpdater.functions.Download;
import eliteDangerousRestUpdater.functions.SystemLogger;
import eliteDangerousRestUpdater.functions.TimeMeasurment;
import eliteDangerousRestUpdater.handler.commodityMappingHandler.CommodityMapping;
import eliteDangerousRestUpdater.handler.commodityPricesHandler.CommodityPrices;
import eliteDangerousRestUpdater.functions.DatabaseHandler;
import eliteDangerousRestUpdater.handler.stationHandler.Stations;
import eliteDangerousRestUpdater.handler.systemsHandler.Systems;
import eliteDangerousRestUpdater.rest.systems.GetSystems;


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

		//
		//
		//
		//		//************************************************************//
		//		//					    GetStations 	   					      //
		//		//************************************************************//
		//
		//		downloadler.download( DOWNLOAD_URL_SYSTEMS, SAVE_PATH_SYSTEMS );
		//		databaseHandler.dropTable( DB_TABLE_SYSTEMS );
		//		jsystems.insertSystems();
		//		delete.delete( SAVE_PATH_SYSTEMS );
		//
		//
		//		//************************************************************//
		//		//						Stations					          //
		//		//************************************************************//
		//
		//		downloadler.download( DOWNLOAD_URL_STATIONS, SAVE_PATH_STATIONS );
		//		databaseHandler.dropTable( DB_TABLE_STATIONS );
		//		insertStations.insertStations();
		//		delete.delete( SAVE_PATH_STATIONS );
		//
		//
		//		//************************************************************//
		//		//						Commodity Prices					  //
		//		//************************************************************//
		//
		//		downloadler.download( DOWNLOAD_URL_STATION_COMMODITY , SAVE_PATH_STATION_COMMODITY );
		//		databaseHandler.dropTable(DB_TABLE_STATION_COMMODITY);
		//		commodityPrices.insertCommodityPrices();
		//		delete.delete(SAVE_PATH_STATION_COMMODITY);
		//
		//
		//		//************************************************************//
		//		//						Commodity Mapping					  //
		//		//************************************************************//
		//
		//		downloadler.download( DOWNLOAD_URL_COMMODITY_MAPPING, SAVE_PATH_COMMODITY_MAPPING );
		//		databaseHandler.dropTable( DB_TABLE_COMMODITY_MAPPING );
		//		commodityMapping.insertCommodityMapping();
		//		delete.delete( SAVE_PATH_COMMODITY_MAPPING );
		//
		//
		//		// End Program //
		//		time.measuretTime( className, "Complete program" );
		//		systemLogger.end();
		//		System.exit( 0 );

		GetSystems getNearbySystems = new GetSystems();
		getNearbySystems.getData( "Vesuvit", 50, false, "Gold", 28, 2, false, "L", 10 );

		//GetStationID getStationID = new GetStationID();
		//getStationID.getStationIDasList( 42,10,1,3 );




	}
}

