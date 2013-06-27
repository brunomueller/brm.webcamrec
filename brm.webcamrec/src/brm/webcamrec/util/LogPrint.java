/*
 * Created on Jan 26, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package brm.webcamrec.util;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author schbrm TODO To change the template for this generated type comment go
 *         to Window - Preferences - Java - Code Style - Code Templates
 */
public class LogPrint {
	
	private static final String TIME_PATTERN = "HH:mm:ss.SSS";
	private static SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_PATTERN);
	public static final int NOTE = 1;

	public static final int ERROR = 2;

	public static void note(Object caller, String message) {

		print(LogPrint.NOTE, caller, message);
	}

	public static void error(Object caller, String message) {
		print(LogPrint.ERROR, caller, message);
	}

	private static void print(int type, Object caller, String message) {
		// SimpleDateFormat dateFmt = new SimpleDateFormat(TIME_PATTERN);
		String msgText = timeFormat.format(new Date() ) + " " + caller.getClass().getName() + " " + message + "\n";
		if (type == LogPrint.NOTE) {
			System.out.print("NOTE: " + msgText);
		}
		if (type == LogPrint.ERROR) {
			System.err.print("ERROR: " + msgText);
		}

	}

}
