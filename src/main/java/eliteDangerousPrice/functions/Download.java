package eliteDangerousPrice.functions;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class Download
{
	SystemLogger systemLogger = SystemLogger.getInstance();


	public void download( String downloadURL, String savePath )
	{

		systemLogger.info( "Starting download from: " + downloadURL );
		final long timeStart = System.currentTimeMillis();

		try
		{
			URL url = new URL( downloadURL );
			BufferedInputStream bufferedInputStream = new BufferedInputStream( url.openStream() );
			FileOutputStream fileOutputStream = new FileOutputStream( savePath );
			byte[] buffer = new byte[1024];
			int count = 0;
			while( ( count = bufferedInputStream.read( buffer, 0, 1024 ) ) != -1 )
			{
				fileOutputStream.write( buffer, 0, count );
			}
			fileOutputStream.close();
			bufferedInputStream.close();

		}
		catch( MalformedURLException e )
		{
			systemLogger.error( "Error in URL: " + e.getMessage() );
		}
		catch( IOException e )
		{
			systemLogger.error( "Error in Downloader: " + e.getMessage() );
		}

		final long timeEnd = System.currentTimeMillis();
		long time = ( timeEnd - timeStart ) / 1000;
		systemLogger.info( "Download finished. Download last: " + time + " seconds." );

	}
}
