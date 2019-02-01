package eliteDangerousPrice.rest;

import static eliteDangerousPrice.utils.Constants.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eliteDangerousPrice.functions.SystemLogger;
import eliteDangerousPrice.functions.DatabaseHandler;


public class NearbySystems
{
	SystemLogger systemLogger = SystemLogger.getInstance();

	DatabaseHandler databaseHandler = DatabaseHandler.getInstance();


	public void getDta( String currentSystem, int radius )
	{
		final long timeStart = System.currentTimeMillis();

		Connection connection = databaseHandler.connect();

		try
		{
			PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT id, edsm_id, system_name, x_pos, y_pos, z_pos, needs_permit, updated_at FROM "
							+ DB_TABLE_SYSTEMS + " WHERE system_name = ?" );
			preparedStatement.setString( 1, currentSystem );
			ResultSet resultSet = preparedStatement.executeQuery();

			int count = 0;
			int batchSize = 25000;

			while( resultSet.next() )
			{
				int id = resultSet.getInt( "id" );
				int edsm_id = resultSet.getInt( "edsm_id" );
				float x_pos = resultSet.getFloat( "x_pos" );
				float y_pos = resultSet.getFloat( "y_pos" );
				float z_pos = resultSet.getFloat( "z_pos" );
				String system_name = resultSet.getString( "system_name" );
				boolean needs_permit = resultSet.getBoolean( "needs_permit" );
				long updated_at = resultSet.getLong( "updated_at" );

				try
				{

					JSONObject jsonObject1 = new JSONObject();
					JSONArray jsonArray = new JSONArray();
/*
					jsonObject1.put( "currentSystem_ID", id );
					jsonObject1.put( "currentSystem_EDSM_ID" , edsm_id );
					jsonObject1.put( "currentSystem_Name", system_name );
					jsonObject1.put( "currentSystem_X-Pos",x_pos );
					jsonObject1.put( "currentSystem_Y-Pos", y_pos );
					jsonObject1.put( "currentSystem_Z-Pos", z_pos);
					jsonObject1.put( "currentSystem_NeedsPermit", needs_permit) ;
					jsonObject1.put( "currentSystem_Updated_at", updated_at  );
					jsonObject1.put( "distance", 0.00 );
					jsonArray.put( jsonObject1 );
*/
					PreparedStatement preparedStatement1 = connection.prepareStatement(
							"SELECT id, edsm_id, system_name, x_pos, y_pos, z_pos, needs_permit, updated_at FROM "
									+ DB_TABLE_SYSTEMS );
					ResultSet resultSet1 = preparedStatement1.executeQuery();
					while( resultSet1.next() )
					{
						int id2 = resultSet1.getInt( "id" );
						float x_pos2 = resultSet1.getFloat( "x_pos" );
						float y_pos2 = resultSet1.getFloat( "y_pos" );
						float z_pos2 = resultSet1.getFloat( "z_pos" );
						boolean needsPermit = resultSet.getBoolean( "needs_permit" );

						double x = x_pos - x_pos2;
						double y = y_pos - y_pos2;
						double z = z_pos - z_pos2;

						double result = Math.sqrt( Math.pow( x, 2 ) + Math.pow( y, 2 ) + Math.pow( z, 2 ) );

						if( result <= radius && result != 0 )
						{
							JSONObject jsonObject = new JSONObject();
							jsonObject.put( "needsPermit", needsPermit );
							jsonObject.put( "distance", result );
							jsonObject.put( "toSystem", id2 );
							jsonArray.put( jsonObject );
						}

					}
					systemLogger.info( jsonArray.toString() );
					sortArray( jsonArray );

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
			e.printStackTrace();
		}

		final long timeEnd = System.currentTimeMillis();
		long time = ( timeEnd - timeStart ) / 1000;
		systemLogger.info( "Last " + time + " seconds." );

	}


	public void sortArray( JSONArray jsonArray )
	{

		List<JSONObject> sortedArray = new ArrayList<JSONObject>();
		for( int i = 0; i < jsonArray.length(); i++ )
			sortedArray.add( jsonArray.getJSONObject( i ) );

		Collections.sort( sortedArray, new Comparator<JSONObject>()
		{
			@Override
			public int compare( JSONObject jsonObjectA, JSONObject jsonObjectB )
			{
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
			}
		} );
		systemLogger.info( sortedArray.toString() );
	}

}

//					JsonObjectBuilder jsonObject = Json.createObjectBuilder()
//							.add("allSystems", Json.createArrayBuilder());
//
//					jsonObject.add( "system", id2 );
//					jsonObject.add( "distance", result );
//					jsonObject.build();
//					System.out.println(jsonObject.toString());














