package eliteDangerousPrice.functions;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

	LocalDateTime now = LocalDateTime.now();

	//Strings
	String fileName = "Log_" + file.format( now ) + ".txt";

	String filePath = "./logs/";


	public void newFile() throws FileNotFoundException, java.io.UnsupportedEncodingException
	{

		PrintWriter writer = new PrintWriter( filePath + fileName, "UTF-8" );
		writer.println( "-----------------------" );
		writer.println( "Log start" );
		writer.println( "Start time: " + dtf.format( now ) );
		writer.println( "------------------------\n" );
		writer.close();

	}


	public void error( String logMsg )
	{
		try (FileWriter f = new FileWriter( filePath + fileName, true );
				BufferedWriter bw = new BufferedWriter( f );
				PrintWriter writer = new PrintWriter( bw ))
		{
			writer.println( logtime.format( now ) + " -ERROR-  " + " : " + logMsg );
		}
		catch( IOException i )
		{
			i.printStackTrace();
		}
	}


	public void warning( String logMsg )
	{
		try (FileWriter f = new FileWriter( filePath + fileName, true );
				BufferedWriter bw = new BufferedWriter( f );
				PrintWriter writer = new PrintWriter( bw ))
		{
			writer.println( logtime.format( now ) + " -WARNING-" + " : " + logMsg );
		}
		catch( IOException i )
		{
			i.printStackTrace();
		}
	}


	public void info( String logMsg )
	{
		try (FileWriter f = new FileWriter( filePath + fileName, true );
				BufferedWriter bw = new BufferedWriter( f );
				PrintWriter writer = new PrintWriter( bw ))
		{
			writer.println( logtime.format( now ) + " -INFO-   " + " : " + logMsg );
		}
		catch( IOException i )
		{
			i.printStackTrace();
		}
	}
}

