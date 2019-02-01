package eliteDangerousPrice.utils;

public class Constants
{

	// General //
	public static final String DB_URL = "jdbc:sqlite:./src/main/resources/database/";

	public static final String DB_NAME = "ED_Database.db";

	// Station Commodity //

	public static final String DB_TABLE_STATION_COMMODITY = "stationCommoditys";

	public static final String SAVE_PATH_STATION_COMMODITY = "C:/Users/nibu/Desktop/Me/Projects/EliteDangerous/EliteDangerousPrices/src/main/resources/downloads/price.csv";

	public static final String SQL_STATION_COMMODITY = "INSERT INTO " + DB_TABLE_STATION_COMMODITY
			+ "(id, station_id, commodity_id, supply, buy_price, sell_price, demand, collected_at) VALUES(?,?,?,?,?,?,?,?)";

	public static final String DOWNLOAD_URL_STATION_COMMODITY = "https://eddb.io/archive/v6/listings.csv";

	// Systems //

	public static final String DB_TABLE_SYSTEMS = "systems";

	public static final String DB_TABLE_NEARBY_SYSTEMS = "nearbySystems";

	public static final String SAVE_PATH_SYSTEMS = "C:/Users/nibu/Desktop/Me/Projects/EliteDangerous/EliteDangerousPrices/src/main/resources/downloads/systems.jsonl";

	public static final String SQL_SYSTEMS_INSERT = "INSERT INTO " + DB_TABLE_SYSTEMS
			+ "(id, edsm_id, system_name, x_pos, y_pos, z_pos, needs_permit, updated_at ) VALUES (?,?,?,?,?,?,?,?)";

	public static final String SQL_SYSTEMS_SELECT_FIRST =
			"SELECT id, edsm_id, system_name, x_pos, y_pos, z_pos, needs_permit, updated_at FROM " + DB_TABLE_SYSTEMS;

	public static final String SQL_SYSTEMS_NEARBY_SYSTEMS_INSERT =
			"INSERT INTO " + DB_TABLE_NEARBY_SYSTEMS + "(fromSystem, toSystem, distance) VALUES (?,?,?)";

	public static final String DOWNLOAD_URL_SYSTEMS = "https://eddb.io/archive/v6/systems_populated.jsonl";

}

//INSERT INTO "+ DB_TABLE_SYSTEMS+"(id, edsm_id, system_name, x_pos, y_pos, z_pos, population, government_id, government, allegiance_id, allegiance, security_id, security, primary_economy_id, primary_economy, power, power_state, power_state_id, needs_permit, updated_at   ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";