package eliteDangerousPrice.jsonHandler;

import static eliteDangerousPrice.utils.Constants.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eliteDangerousPrice.functions.SystemLogger;
import eliteDangerousPrice.handler.DatabaseHandler;


public class NearbySystemsV2
{
	SystemLogger systemLogger = SystemLogger.getInstance();

	DatabaseHandler databaseHandler = DatabaseHandler.getInstance();


	public void getDta()
	{
		final long timeStart = System.currentTimeMillis();

		try (Connection connection = databaseHandler.connect())
		{

			PreparedStatement preparedStatement = connection.prepareStatement( SQL_SYSTEMS_SELECT_FIRST );
			ResultSet resultSet = preparedStatement.executeQuery();

			while( resultSet.next() )
			{
				int id = resultSet.getInt( "id" );
				int edsm_id = resultSet.getInt( "edsm_id" );
				float x_pos = resultSet.getFloat( "x_pos" );
				float y_pos = resultSet.getFloat( "y_pos" );
				float z_pos = resultSet.getFloat( "z_pos" );
				boolean needs_permit = resultSet.getBoolean( "needs_permit" );

				try
				{
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

						calc( id, x_pos, y_pos, z_pos, id2, x_pos2, y_pos2, z_pos2 );
					}

				}
				catch( SQLException e )
				{
					System.out.println( "Error 2: " + e.getMessage() );
				}

				System.out.println(
						id + ", " + edsm_id + ", " + ", " + x_pos + ", " + y_pos + ", " + z_pos + ", " + needs_permit );
			}

		}
		catch( SQLException e )
		{
			System.out.println( "Error 1: " + e.getMessage() );
		}

		final long timeEnd = System.currentTimeMillis();
		long time = ( timeEnd - timeStart ) / 1000;
		systemLogger.info( "Last " + time + " seconds." );

	}


	public void calc( int id, float x_pos, float y_pos, float z_pos, int id2, float x_pos2, float y_pos2, float z_pos2 )
	{

		double x = x_pos - x_pos2;
		double y = y_pos - y_pos2;
		double z = z_pos - z_pos2;

		double result = Math.sqrt( Math.pow( x, 2 ) + Math.pow( y, 2 ) + Math.pow( z, 2 ) );

		if( result <= 150 )
		{
			//systemLogger.info("Ergebnis von System: " + id + " und System : " + id2 + " : " + result);
			//inset( id, id2, result );

		}
		//systemLogger.info("Ergebnis von System: " + id + " und System : " + id2 + " : " + ergebnis);
	}


	public void inset( int id, int id2, double result )
	{

		int count = 0;
		int batchSize = 10000;

		Connection connection = databaseHandler.connect();
		PreparedStatement preparedStatement = null;

		try
		{
			preparedStatement = connection.prepareStatement( SQL_SYSTEMS_NEARBY_SYSTEMS_INSERT );
			connection.setAutoCommit( false );
		}
		catch( SQLException e )
		{
			systemLogger.error( "Error in NearbySystems.class: " + e.getMessage() );
		}

		try
		{

			preparedStatement.setInt( 1, id );
			preparedStatement.setInt( 2, id2 );
			preparedStatement.setDouble( 3, result );

			preparedStatement.addBatch();

			count++;

			System.out.println( count );

			if( count % batchSize == 0 )
			{
				systemLogger.info( "Commit the Batch in NearbySystems.class: " + count );
				preparedStatement.executeBatch();
				preparedStatement.clearBatch();
			}

		}
		catch( SQLException e )
		{
			systemLogger.error( "SQLException in NearbySystems.class: " + e.getMessage() );
		}

		try
		{
			preparedStatement.executeBatch();
			systemLogger.info( "Inserted last Batch in NearbySystems.class!" );
			systemLogger.info( "Inserted rows: " + count );

			connection.commit();
			connection.close();

		}
		catch( SQLException e )
		{
			systemLogger.error( "SQLException in JSONReader: " + e.getMessage() );
		}
	}
}
















