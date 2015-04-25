package utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class ForumLogger {
	
	private static final Logger actionLog = Logger.getLogger("action");
	private static final Logger errorLog = Logger.getLogger("error");
	private static FileHandler actionfl = null;
	private static FileHandler errorfl = null;

	public static void actionLog(String actionMsg) {
		
		if (actionfl == null){
			try {
				actionfl  = new FileHandler("action_log.log");
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			actionLog.addHandler(actionfl);
		}
//		actionLog.info(Thread.currentThread().getStackTrace()[2].getClassName() + " " + actionMsg);
	}
	
	public static void errorLog(String errorMsg){
		
		if (errorfl == null){
			try {
				errorfl  = new FileHandler("error_log.log");
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			errorLog.addHandler(errorfl);
		}
//		errorLog.severe(Thread.currentThread().getStackTrace()[2].getClassName() + " " + errorMsg);
	}
	
	
	/**
	 * how to use the logger:
	 * use the line ForumLogger.actionLog("your action message here");
	 * for writing to the action log... the log will automaticlly add the class and method, so no need to write them
	 * 
	 * same for the error log - ForumLogger.errorLog("your error message here");
	 * put error logs inside each catch, before exceptions you throw
	 * 
	 * all output will be printed on the screen and too two different log files to the main folder of the project
	 */
	

}
