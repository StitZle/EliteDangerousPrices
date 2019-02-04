package eliteDangerousRestUpdater.rest.build;

import java.sql.Connection;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;


public class Build
{
	public void buildJson( String systemName, long systemID, double result, boolean needsPermit, String maxLanding,
			String commodityName, long supply, long buyPrice, long stationID, String stationName, double distanceToStar,
			String maxLandingSize, boolean planetary,
			boolean ignore )
	{

		if( ignore == true )
		{
			//Build
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArray = new JSONArray();

			jsonObject.put( "StationName", stationName );
			jsonObject.put( "StationID", stationID );
			jsonObject.put( "DistanceToStar", distanceToStar );
			jsonObject.put( "Planetary", planetary );
			jsonObject.put( "CommodityName", commodityName );
			jsonObject.put( "Supply", supply );
			jsonObject.put( "BuyPrice", buyPrice );
			jsonObject.put( "MaxLandingSize", maxLanding );
			jsonArray.put( jsonObject );

			HashMap<String, Object> hashMap = new HashMap<>();

			hashMap.put( "SystemName", systemName );
			hashMap.put( "SystemID", systemID );
			hashMap.put( "Distance", result );
			hashMap.put( "NeedPermit", needsPermit );
			//hashMap.put( "Stations" )

		}

	}
}
