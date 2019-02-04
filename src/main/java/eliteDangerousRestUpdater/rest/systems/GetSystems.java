package eliteDangerousRestUpdater.rest.systems;

import static eliteDangerousRestUpdater.utils.Constants.DB_TABLE_SYSTEMS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eliteDangerousRestUpdater.functions.DatabaseHandler;
import eliteDangerousRestUpdater.functions.SystemLogger;
import eliteDangerousRestUpdater.functions.TimeMeasurment;
import eliteDangerousRestUpdater.rest.stations.GetStations;


public class GetSystems
{
	SystemLogger systemLogger = SystemLogger.getInstance();

	String className = this.getClass().getSimpleName();

	DatabaseHandler databaseHandler = DatabaseHandler.getInstance();


	public void getData( String currentSystem, int radius, boolean inkSystemsWithPermissions, String commodityName,
			int count, double multiplier, boolean inkPlanetray, String maxLanding, int maxAge )
	{

		JSONArray jsonArray = new JSONArray();

		Connection connection = databaseHandler.connect();

		try
		{
			PreparedStatement preparedStatementCurrentSystem = connection.prepareStatement(
					"SELECT id, system_name, x_pos, y_pos, z_pos, needs_permit, updated_at FROM "
							+ DB_TABLE_SYSTEMS + " WHERE system_name = ?" );

			preparedStatementCurrentSystem.setString( 1, currentSystem );
			ResultSet resultSetCurrentSystem = preparedStatementCurrentSystem.executeQuery();

			while( resultSetCurrentSystem.next() )
			{
				float x_pos = resultSetCurrentSystem.getFloat( "x_pos" );
				float y_pos = resultSetCurrentSystem.getFloat( "y_pos" );
				float z_pos = resultSetCurrentSystem.getFloat( "z_pos" );

				try
				{
					String sqlAroundSystems = null;

					if( inkSystemsWithPermissions == true )
					{
						sqlAroundSystems = "SELECT id, system_name, x_pos, y_pos, z_pos, needs_permit, updated_at FROM "
								+ DB_TABLE_SYSTEMS;
					}
					else
					{
						sqlAroundSystems = "SELECT id, system_name, x_pos, y_pos, z_pos, needs_permit, updated_at FROM "
								+ DB_TABLE_SYSTEMS + " WHERE needs_permit = false";
					}

					PreparedStatement preparedStatementArroundSystem = connection.prepareStatement( sqlAroundSystems );

					ResultSet resultSetArroundSystem = preparedStatementArroundSystem.executeQuery();

					while( resultSetArroundSystem.next() )
					{
						int systemID = resultSetArroundSystem.getInt( "id" );
						String systemName = resultSetArroundSystem.getString( "system_name" );
						float x_pos2 = resultSetArroundSystem.getFloat( "x_pos" );
						float y_pos2 = resultSetArroundSystem.getFloat( "y_pos" );
						float z_pos2 = resultSetArroundSystem.getFloat( "z_pos" );
						boolean needsPermit = resultSetArroundSystem.getBoolean( "needs_permit" );

						double x = x_pos - x_pos2;
						double y = y_pos - y_pos2;
						double z = z_pos - z_pos2;

						double result = Math.sqrt( Math.pow( x, 2 ) + Math.pow( y, 2 ) + Math.pow( z, 2 ) );

						if( result <= radius )
						{
							HashMap<String, Object> hashMap = new HashMap<>();

							hashMap.put( "SystemName", systemName );
							hashMap.put( "SystemID", systemID );
							hashMap.put( "Distance", result );
							hashMap.put( "NeedsPermit", needsPermit );

							//get Stations
							GetStations getStations = new GetStations();
							getStations.getStations( systemID, inkPlanetray, maxLanding, commodityName, count,
									multiplier, maxAge, connection, hashMap );
						}
					}
				}
				catch( SQLException e )
				{
					e.printStackTrace();
				}
			}
		}
		catch( SQLException e )
		{
			System.out.println( "Error 1: " + e.getMessage() );
		}

		systemLogger.info( className, jsonArray.toString() );

	}
}
/*
	public void sortArray( JSONArray jsonArray )
	{

		List<JSONObject> sortedArray = new ArrayList<JSONObject>();
		for( int i = 0; i < jsonArray.length(); i++ )
			sortedArray.add( jsonArray.getJSONObject( i ) );

		Collections.sort( sortedArray, ( jsonObjectA, jsonObjectB ) -> {
			int compare = 0;
			try
			{
				Double keyA = jsonObjectA.getDouble( "distance" );
				Double keyB = jsonObjectB.getDouble( "distance" );
				compare = Double.compare( keyA, keyB );
			}
			catch( JSONException e )
			{
				e.printStackTrace();
			}
			return compare;
		} );
		systemLogger.info( className, sortedArray.toString() );
	}

}

*/

//					JsonObjectBuilder jsonObject = Json.createObjectBuilder()
//							.add("allSystems", Json.createArrayBuilder());
//
//					jsonObject.add( "system", id2 );
//					jsonObject.add( "distance", result );
//					jsonObject.build();
//					System.out.println(jsonObject.toString());
