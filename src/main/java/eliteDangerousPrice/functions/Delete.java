package eliteDangerousPrice.functions;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;


public class Delete
{
	SystemLogger systemLogger = SystemLogger.getInstance();


	public void delete( String deletePath )
	{
		try
		{
			Files.deleteIfExists( Paths.get( deletePath ) );
		}
		catch( NoSuchFileException e )
		{
			systemLogger.error( "No such file/directory exists: " + deletePath );
		}
		catch( DirectoryNotEmptyException e )
		{
			systemLogger.error( "Directory is not empty: " + deletePath );
		}
		catch( IOException e )
		{
			systemLogger.error( "Invalid permissions: " + deletePath );
		}

		systemLogger.info( "Successfully deleted csv file. Path: " + deletePath );

	}
}
