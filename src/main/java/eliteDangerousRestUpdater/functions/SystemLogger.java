package eliteDangerousRestUpdater.functions;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class SystemLogger
{

	//Stuff for Singelton

	private static final SystemLogger instance = new SystemLogger();


	private SystemLogger()
	{
	}


	public static SystemLogger getInstance()
	{
		return instance;
	}


	//Time
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern( "dd/MM/yyyy HH:mm:ss" );

	DateTimeFormatter file = DateTimeFormatter.ofPattern( "dd_MM_yyyy" );

	DateTimeFormatter logtime = DateTimeFormatter.ofPattern( "HH:mm:ss" );



	//Strings
	String fileName = "Log_" + file.format( LocalDateTime.now() ) + ".txt";

	String filePath = "./logs/";


	public void newFile()
	{
		try
		{
			PrintWriter writer = new PrintWriter( filePath + fileName, "UTF-8" );
			writer.println( "-----------------------" );
			writer.println( "Log start" );
			writer.println( "Start time: " + dtf.format( LocalDateTime.now() ) );
			writer.println( "------------------------\n" );
			writer.close();
		}
		catch( FileNotFoundException e )
		{
			e.printStackTrace();
		}
		catch( UnsupportedEncodingException e )
		{
			e.printStackTrace();
		}

	}


	public void end()
	{
		try (FileWriter f = new FileWriter( filePath + fileName, true );
				BufferedWriter bw = new BufferedWriter( f );
				PrintWriter writer = new PrintWriter( bw ))
		{
			writer.println( "" );
			writer.println( "-----------------------" );
			writer.println( "Log end" );
			writer.println( "End time: " + dtf.format( LocalDateTime.now() ) );
			writer.println( "------------------------\n" );

		}
		catch( IOException i )
		{
			i.printStackTrace();
		}

	}


	public void error( String className, String logMsg )
	{
		try (FileWriter f = new FileWriter( filePath + fileName, true );
				BufferedWriter bw = new BufferedWriter( f );
				PrintWriter writer = new PrintWriter( bw ))
		{
			writer.println( logtime.format( LocalDateTime.now() ) + " -ERROR-  " + className + String.format(
					"%" + ( 21 - className.length() ) + "s", "" ) + ": " + logMsg );

			writer.println( logtime.format( LocalDateTime.now() ) + " -ERROR-  "
					+ " Exiting program because an error occurred in "
					+ className );

			//System.exit( 1 );
		}
		catch( IOException i )
		{
			i.printStackTrace();
		}
	}


	public void warning( String className, String logMsg )
	{
		try (FileWriter f = new FileWriter( filePath + fileName, true );
				BufferedWriter bw = new BufferedWriter( f );
				PrintWriter writer = new PrintWriter( bw ))
		{
			writer.println( logtime.format( LocalDateTime.now() ) + " -WARNING-  " + className + String.format(
					"%" + ( 19 - className.length() ) + "s", "" ) + ": " + logMsg );

		}
		catch( IOException i )
		{
			i.printStackTrace();
		}
	}


	public void info( String className, String logMsg )
	{
		try (FileWriter f = new FileWriter( filePath + fileName, true );
				BufferedWriter bw = new BufferedWriter( f );
				PrintWriter writer = new PrintWriter( bw ))
		{
			writer.println( logtime.format( LocalDateTime.now() ) + " -INFO-  " + className + String.format(
					"%" + ( 22 - className.length() ) + "s", "" ) + ": " + logMsg );
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
	}
}

