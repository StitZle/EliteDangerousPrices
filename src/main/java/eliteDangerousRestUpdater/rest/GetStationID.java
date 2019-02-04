package eliteDangerousRestUpdater.rest;

import static eliteDangerousRestUpdater.utils.Constants.DB_TABLE_STATION_COMMODITY;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.json.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eliteDangerousRestUpdater.functions.DatabaseHandler;
import eliteDangerousRestUpdater.functions.SystemLogger;
import eliteDangerousRestUpdater.functions.TimeMeasurment;


public class GetStationID
{
/*
	//Stuff for Singelton

	private static final GetStationID instance = new GetStationID();


	private GetStationID()
	{
	}


	public static GetStationID getInstance()
	{
		return instance;
	}

*/

	DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

	SystemLogger systemLogger = SystemLogger.getInstance();

	String className = this.getClass().getSimpleName();

	TimeMeasurment time = new TimeMeasurment();


	public void getStationIDasList( int commoityId, int count, double multiplayer, long maxAge )
	{

		HashMap<Long, ArrayList<JSONObject>> hashMap = new HashMap<Long, ArrayList<JSONObject>>();

		//Select every Station that sell specific Commodity and has right demand.
		double demand = count * multiplayer;
		int minDemand = (int)demand;

		//TODO UnixTime soll nicht von jetzt sondern von 00:00 zählen.
		long unixTime = System.currentTimeMillis() / 1000L;
		long maxCommodityAge = unixTime - TimeUnit.DAYS.toSeconds( maxAge );

		JSONArray jsonArray = new JSONArray();

		String sql =
				"SELECT station_id, commodity_id, supply, buy_price, collected_at FROM " + DB_TABLE_STATION_COMMODITY
						+ " WHERE commodity_id = ? AND supply >= ? AND collected_at >= ?";

		try (Connection connection = databaseHandler.connect();
				PreparedStatement preparedStatement = connection.prepareStatement( sql ))
		{

			//set Values
			preparedStatement.setInt( 1, commoityId );
			preparedStatement.setInt( 2, minDemand );
			preparedStatement.setLong( 3, maxCommodityAge );

			ResultSet resultSet = preparedStatement.executeQuery();

			while( resultSet.next() )
			{
				long stationID = resultSet.getLong( "station_id" );
				int commodityID = resultSet.getInt( "commodity_id" );
				long supply = resultSet.getLong( "supply" );
				int buyPrice = resultSet.getInt( "buy_price" );
				long collectedAt = resultSet.getLong( "collected_at" );

				JSONObject jsonObject = new JSONObject();

				jsonObject.put( "StationID", stationID );
				jsonObject.put( "CommodityID", commodityID );
				jsonObject.put( "Supply", supply );
				jsonObject.put( "BuyPrice", buyPrice );
				jsonObject.put( "CollectedAt", collectedAt );

				jsonArray.put( jsonObject );




				/*if (hashMap.get( systemID ) == null){
					hashMap.put( systemID, new ArrayList<JSONObject>(  ) );
				}
				hashMap.get( systemID ).add( jsonObject );
				*/
			}
		}
		catch( SQLException e )
		{
			e.printStackTrace();
		}

		//jsonArray.put(hashMap);
		systemLogger.info( className, jsonArray.toString() );

	}
}
