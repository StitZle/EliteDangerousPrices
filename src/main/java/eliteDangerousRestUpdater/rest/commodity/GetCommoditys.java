package eliteDangerousRestUpdater.rest.commodity;

import static eliteDangerousRestUpdater.utils.Constants.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import eliteDangerousRestUpdater.rest.build.Build;


public class GetCommoditys
{
	public void getCommodity( String commodityName, int count, double multiplier, int maxAge,
			long stationID, Connection connection, HashMap hashMap, JSONObject jsonObject )
	{

		CommodityMapping commodityMapping = new CommodityMapping();
		int commodityID = commodityMapping.mapping( commodityName );

		double calcSupply = count * multiplier;
		int minSupply = (int)calcSupply;

		//TODO UnixTime soll nicht von jetzt sondern von 00:00 zählen.
		long unixTime = System.currentTimeMillis() / 1000L;
		long maxCommodityAge = unixTime - TimeUnit.DAYS.toSeconds( maxAge );

		boolean ignore = false;
		long supply = 0;
		long buyPrice = 0;

		// Get Commodity
		String commoditySql = "SELECT supply, buy_price FROM " + DB_TABLE_STATION_COMMODITY
				+ " WHERE commodity_id = ? AND supply >= ? AND collected_at >= ? AND station_id = ?";

		try
		{
			PreparedStatement preparedStatementCommodity = connection.prepareStatement( commoditySql );

			//set Values
			preparedStatementCommodity.setInt( 1, commodityID );
			preparedStatementCommodity.setInt( 2, minSupply );
			preparedStatementCommodity.setLong( 3, maxCommodityAge );
			preparedStatementCommodity.setLong( 4, stationID );

			ResultSet resultSetCommodity = preparedStatementCommodity.executeQuery();

			if( resultSetCommodity.next() != false )
			{
				supply = resultSetCommodity.getLong( "supply" );
				buyPrice = resultSetCommodity.getLong( "buy_price" );

				jsonObject.put( "Supply", supply );
				jsonObject.put( "BuyPrice", buyPrice );

				JSONArray jsonArray = new JSONArray();
				jsonArray.put( jsonObject );

				jsonArray.put( hashMap );
				jsonArray.put( jsonObject );

				System.out.println( jsonArray );
			}
			else
			{
				ignore = true;
			}

			//Build build = new Build();
			//build.buildJson(systemName, systemID, result, needsPermit, maxLanding, commodityName, supply, buyPrice, stationID, stationName, distanceToStar, maxLandingSize, planetary, ignore);

		}
		catch( SQLException e )
		{
			e.printStackTrace();
		}

	}
}
