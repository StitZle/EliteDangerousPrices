package eliteDangerousPrice.functions;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class Download
{
	SystemLogger systemLogger = SystemLogger.getInstance();

	String className = this.getClass().getSimpleName();

	TimeMeasurment time = new TimeMeasurment();


	public void download( String downloadURL, String savePath )
	{

		time.startTime();

		systemLogger.info( className, "Starting download from: " + downloadURL );

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
			systemLogger.error( className, "Error in URL: " + e.getMessage() );
		}
		catch( IOException e )
		{
			systemLogger.error( className, "Error while downloading: " + e.getMessage() );
		}
		time.measuretTime( className, "Download from " + downloadURL );
	}
}
