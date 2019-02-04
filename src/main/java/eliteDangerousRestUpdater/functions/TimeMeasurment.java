package eliteDangerousRestUpdater.functions;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;


public class TimeMeasurment
{
	SystemLogger systemLogger = SystemLogger.getInstance();

	long timeStart = 0;


	public void startTime()
	{
		timeStart = System.currentTimeMillis();
	}


	public void measuretTime( String className, String workName )
	{

		final long timeEnd = System.currentTimeMillis();

		final long time = timeEnd - timeStart;

		DecimalFormat df = new DecimalFormat( "#.00" );

		if( TimeUnit.MILLISECONDS.toMillis( time ) >= 0 )
		{
			systemLogger.info( className, workName + " tooked " + time + " milli Seconds!" );
		}
		if( TimeUnit.MILLISECONDS.toSeconds( time ) >= 1 )
		{
			systemLogger.info( className,
					workName + " tooked " + TimeUnit.MILLISECONDS.toSeconds( time ) + " Seconds!" );
		}
		if( TimeUnit.MILLISECONDS.toMinutes( time ) >= 1 )
		{
			systemLogger.info( className,
					workName + " tooked " + TimeUnit.MILLISECONDS.toMinutes( time ) + " Minutes!" );
		}

	}
}
