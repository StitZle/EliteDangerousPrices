package eliteDangerousPrice.jsonHandler;

import static eliteDangerousPrice.utils.Constants.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eliteDangerousPrice.functions.SystemLogger;
import eliteDangerousPrice.handler.DatabaseHandler;


public class NearbySystems
{
	SystemLogger systemLogger = SystemLogger.getInstance();

	DatabaseHandler databaseHandler = DatabaseHandler.getInstance();


	public void getDta()
	{
		final long timeStart = System.currentTimeMillis();

		Connection connection = databaseHandler.connect();

		try
		{
			PreparedStatement preparedStatement = connection.prepareStatement( SQL_SYSTEMS_SELECT_FIRST );
			ResultSet resultSet = preparedStatement.executeQuery();

			PreparedStatement preparedStatement2 = connection.prepareStatement( SQL_SYSTEMS_NEARBY_SYSTEMS_INSERT );

			int count = 0;
			int batchSize = 25000;

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

						double x = x_pos - x_pos2;
						double y = y_pos - y_pos2;
						double z = z_pos - z_pos2;

						double result = Math.sqrt( Math.pow( x, 2 ) + Math.pow( y, 2 ) + Math.pow( z, 2 ) );

						if( result <= 150 && result != 0 )
						{

							connection.setAutoCommit( false );
							try
							{

								preparedStatement2.setInt( 1, id );
								preparedStatement2.setInt( 2, id2 );
								preparedStatement2.setDouble( 3, result );

								preparedStatement2.addBatch();

								count++;

								if( count % batchSize == 0 )
								{
									//systemLogger.info( "Commit the Batch  in NearbySystems.class: " + count );
									preparedStatement2.executeBatch();
									preparedStatement2.clearBatch();
								}

							}
							catch( SQLException e )
							{
								systemLogger.error( "SQLException in NearbySystems.class: " + e.getMessage() );
							}
						}

					}
				}
				catch( SQLException e )
				{
					System.out.println( "Error 2: " + e.getMessage() );
				}

				//System.out.println( id + ", " + edsm_id + ", " + ", " + x_pos + ", " + y_pos + ", " + z_pos + ", " + needs_permit );
			}
			try
			{
				preparedStatement2.executeBatch();
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
		catch( SQLException e )
		{
			System.out.println( "Error 1: " + e.getMessage() );
		}

		final long timeEnd = System.currentTimeMillis();
		long time = ( timeEnd - timeStart ) / 1000;
		systemLogger.info( "Last " + time + " seconds." );

	}

}
















