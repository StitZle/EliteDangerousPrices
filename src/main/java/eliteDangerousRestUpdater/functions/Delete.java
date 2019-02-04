package eliteDangerousRestUpdater.functions;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;


public class Delete
{
	SystemLogger systemLogger = SystemLogger.getInstance();

	String className = this.getClass().getSimpleName();


	public void delete( String deletePath )
	{
		try
		{
			Files.deleteIfExists( Paths.get( deletePath ) );
		}
		catch( NoSuchFileException e )
		{
			systemLogger.warning( className, "No such file/directory exists: " + deletePath );
		}
		catch( DirectoryNotEmptyException e )
		{
			systemLogger.warning( className, "Directory is not empty: " + deletePath );
		}
		catch( IOException e )
		{
			systemLogger.warning( className, "Invalid permissions: " + deletePath );
		}

		systemLogger.info( className, "Successfully deleted file. Path: " + deletePath );

	}
}
