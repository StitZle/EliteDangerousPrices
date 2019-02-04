package eliteDangerousRestUpdater.rest.stations;

import static eliteDangerousRestUpdater.utils.Constants.DB_TABLE_STATIONS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.json.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import eliteDangerousRestUpdater.functions.DatabaseHandler;
import eliteDangerousRestUpdater.rest.commodity.GetCommoditys;


public class GetStations
{
	DatabaseHandler databaseHandler = DatabaseHandler.getInstance();


	public void getStations( long systemID, boolean inkPlanetray, String maxLanding, String commodityName, int count,
			double multiplier, int maxAge,
			Connection connection, HashMap hashMap )
	{

		//Build sql Querry

		String sqlStations = null;

		if( inkPlanetray == true )
		{
			sqlStations = "SELECT id, name, distance_to_star, is_planetary FROM "
					+ DB_TABLE_STATIONS
					+ " WHERE system_id = ? AND max_landing_pad_size = ? AND has_commodities = true";
		}
		else
		{
			sqlStations = "SELECT id, name, distance_to_star, is_planetary FROM "
					+ DB_TABLE_STATIONS
					+ " WHERE is_planetary = false AND system_id = ? AND max_landing_pad_size = ? AND has_commodities = true";
		}

		try
		{
			PreparedStatement preparedStatementStations = connection.prepareStatement( sqlStations );
			preparedStatementStations.setLong( 1, systemID );
			preparedStatementStations.setString( 2, maxLanding );
			ResultSet resultSetStations = preparedStatementStations.executeQuery();

			while( resultSetStations.next() )
			{
				long stationID = resultSetStations.getLong( "id" );
				String stationName = resultSetStations.getString( "name" );
				double distanceToStar = resultSetStations.getDouble( "distance_to_star" );
				boolean planetary = resultSetStations.getBoolean( "is_planetary" );

				JSONObject jsonObject = new JSONObject();

				jsonObject.put( "StationID", stationID );
				jsonObject.put( "StationName", stationName );
				jsonObject.put( "DistanceToStar", distanceToStar );
				jsonObject.put( "MaxLanding", maxLanding );
				jsonObject.put( "Planetary", planetary );

				//Get Commoditys
				GetCommoditys getCommoditys = new GetCommoditys();
				getCommoditys.getCommodity( commodityName, count, multiplier, maxAge, stationID, connection, hashMap,
						jsonObject );

			}
		}
		catch( SQLException e )
		{
			e.printStackTrace();
		}

	}
}



/*
class Scratch
{
	public static void main( String[] args )
	{
		HashMap<String, List<String>> map = new HashMap<>( );

		List<String> stations = new ArrayList<>(  );

		for (String s : stations) {
			if (!map.get( s.systemId )) {
				map.put( s.systemId , new ArrayList<>( ));
			}
			map.get( s.systemId ).add( s );
		}
	}
}









		HashMap<Long, List<JsonObject>> hashMap = new HashMap<>(  );


		for (int i = 0; i < jsonArray.length(); i++)
		{

			long stationID = jsonArray.getJSONObject( i ).getLong( "StationID" );

			//Get Stations
			String sql =
					"SELECT id, system_id, name, max_landing_pad_size, distance_to_star, market_updated_at, is_planetary FROM "
							+ DB_TABLE_STATIONS + " WHERE id = ?";

			try (Connection connection = databaseHandler.connect();
					PreparedStatement preparedStatement = connection.prepareStatement( sql ))
			{

				preparedStatement.setLong( 1, stationID );

			}
			catch( SQLException e )
			{
				e.printStackTrace();
			}

		}






*/
	/*if (hashMap.get( systemID ) == null){
					hashMap.put( systemID, new ArrayList<JSONObject>(  ) );
				}
				hashMap.get( systemID ).add( jsonObject );
				*/
