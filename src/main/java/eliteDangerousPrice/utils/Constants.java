package eliteDangerousPrice.utils;

public class Constants
{

	// General //
	public static final String DB_URL = "jdbc:sqlite:./src/main/resources/database/";

	public static final String DB_NAME = "ED_Database.db";

	// Station Commodity //

	public static final String DB_TABLE_STATION_COMMODITY = "stationCommoditys";

	public static final String SQL_STATION_COMMODITY = "INSERT INTO " + DB_TABLE_STATION_COMMODITY
			+ "(id, station_id, commodity_id, supply, buy_price, sell_price, demand, collected_at) VALUES(?,?,?,?,?,?,?,?)";

	public static final String DOWNLOAD_URL_STATION_COMMODITY = "https://eddb.io/archive/v6/listings.csv";

	public static final String SAVE_PATH_STATION_COMMODITY = "C:/Users/nibu/Desktop/Me/Projects/EliteDangerous/EliteDangerousPrices/src/main/resources/downloads/price.csv";


	// Systems //

	public static final String DB_TABLE_SYSTEMS = "systems";

	public static final String SAVE_PATH_SYSTEMS = "C:/Users/nibu/Desktop/Me/Projects/EliteDangerous/EliteDangerousPrices/src/main/resources/downloads/systems.jsonl";

	public static final String SQL_SYSTEMS_INSERT = "INSERT INTO " + DB_TABLE_SYSTEMS
			+ "(id, edsm_id, system_name, x_pos, y_pos, z_pos, needs_permit, updated_at ) VALUES (?,?,?,?,?,?,?,?)";

	public static final String DOWNLOAD_URL_SYSTEMS = "https://eddb.io/archive/v6/systems_populated.jsonl";

	// Commodity Mapping //

	public static final String DOWNLOAD_URL_COMMODITY_MAPPING = "https://eddb.io/archive/v6/commodities.json";

	public static final String SAVE_PATH_COMMODITY_MAPPING = "C:/Users/nibu/Desktop/Me/Projects/EliteDangerous/EliteDangerousPrices/src/main/resources/downloads/commodities.jsonl";

	public static final String DB_TABLE_COMMODITY_MAPPING = "commodityMapping";

	public static final String SQL_COMMODITY_MAPPING =
			"INSERT INTO " + DB_TABLE_COMMODITY_MAPPING + "(id, name ) VALUES (?,?)";

	// Stations //

	public static final String DB_TABLE_STATIONS = "stations";

	public static final String SQL_STATIONS = "INSERT INTO " + DB_TABLE_STATIONS
			+ "(id, name, updated_at, max_landing_pad_size, distance_to_star, has_commodities, market_updated_at, is_planetary ) VALUES (?,?,?,?,?,?,?,?)";

	public static final String DOWNLOAD_URL_STATIONS = "https://eddb.io/archive/v6/stations.jsonl";

	public static final String SAVE_PATH_STATIONS = "./src/main/resources/downloads/stations.jsonl";

}

//INSERT INTO "+ DB_TABLE_SYSTEMS+"(id, edsm_id, system_name, x_pos, y_pos, z_pos, population, government_id, government, allegiance_id, allegiance, security_id, security, primary_economy_id, primary_economy, power, power_state, power_state_id, needs_permit, updated_at   ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";