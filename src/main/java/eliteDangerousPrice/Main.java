package eliteDangerousPrice;

import static eliteDangerousPrice.utils.Constants.*;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eliteDangerousPrice.functions.Delete;
import eliteDangerousPrice.functions.Download;
import eliteDangerousPrice.functions.SystemLogger;
import eliteDangerousPrice.csvHandler.csvReaderInsert;
import eliteDangerousPrice.handler.DatabaseHandler;
import eliteDangerousPrice.jsonHandler.NearbySystems;
import eliteDangerousPrice.jsonHandler.JSONReader;


public class Main
{

	SystemLogger systemLogger = SystemLogger.getInstance();


	public static void main( String[] args )
	{

		// Init
		Main main = new Main();
		main.init();

	}


	public void init()
	{

		try
		{
			systemLogger.newFile();
		}
		catch( FileNotFoundException | UnsupportedEncodingException e )
		{
			systemLogger.error( "File format is not supported. Or Encoding is wrong. " + e.getMessage() );
		}

		Download downloadler = new Download();
		csvReaderInsert csvReaderInsert = new csvReaderInsert();
		Delete delete = new Delete();

		DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
		databaseHandler.generateNewDatabase();
		databaseHandler.generateTables();
		JSONReader jsonReader = new JSONReader();
		NearbySystems getNearbySystems = new NearbySystems();

		final long timeStart = System.currentTimeMillis();

		//************************************************************//
		//						Commodity Prices					  //
		//************************************************************//
/*
		downloadler.download( DOWNLOAD_URL_STATION_COMMODITY , SAVE_PATH_STATION_COMMODITY );
		databaseHandler.dropTable(DB_TABLE_STATION_COMMODITY);
		csvReaderInsert.read();
		delete.delete(SAVE_PATH_STATION_COMMODITY);
*/

		//************************************************************//
		//						      Systems 	   					  //
		//************************************************************//

		//downloadler.download( DOWNLOAD_URL_SYSTEMS, SAVE_PATH_SYSTEMS );
		//databaseHandler.dropTable( DB_TABLE_SYSTEMS );
		//databaseHandler.dropTable( DB_TABLE_NEARBY_SYSTEMS );
		//jsonReader.readJson();
		//delete.delete( SAVE_PATH_SYSTEMS );
		//getNearbySystems.getDta();

		try
		{
			Connection connection = databaseHandler.connect();
			PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT fromSystem, toSystem, distance FROM " + DB_TABLE_NEARBY_SYSTEMS
							+ " WHERE fromSystem= 6745" );
			ResultSet resultSet = preparedStatement.executeQuery();

			while( resultSet.next() )
			{
				int id = resultSet.getInt( "fromSystem" );
				int id2 = resultSet.getInt( "toSystem" );
				double dis = resultSet.getDouble( "distance" );
				//System.out.println(id +", " + id2 + ", " + dis);
			}
		}
		catch( SQLException e )
		{
			e.printStackTrace();
		}

		final long timeEnd = System.currentTimeMillis();
		long time = ( timeEnd - timeStart ) / 1000;
		systemLogger.info( "Hole action tooked:  " + time + " seconds." );
		System.out.println( time );

	}
}

