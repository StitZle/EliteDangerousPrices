package eliteDangerousRestUpdater.rest.commodity;

import static eliteDangerousRestUpdater.utils.Constants.DB_TABLE_COMMODITY_MAPPING;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import eliteDangerousRestUpdater.functions.DatabaseHandler;
import eliteDangerousRestUpdater.functions.SystemLogger;


public class CommodityMapping
{
	DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

	SystemLogger systemLogger = SystemLogger.getInstance();

	String className = this.getClass().getSimpleName();


	public int mapping( String commoity )
	{

		String sql = "SELECT id FROM " + DB_TABLE_COMMODITY_MAPPING + " WHERE name = ?";

		int commoityId = 0;

		try (Connection connection = databaseHandler.connect())
		{
			PreparedStatement preparedStatement = connection.prepareStatement( sql );
			preparedStatement.setString( 1, commoity );
			ResultSet resultSetMapping = preparedStatement.executeQuery();

			commoityId = resultSetMapping.getInt( "id" );
		}
		catch( SQLException e )
		{
			systemLogger.error( className, "SQLException: " + e.getMessage() );
			e.printStackTrace();
		}

		return commoityId;
	}
}
