package utils;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
	private static AtomicInteger[] ids;
	{
		ids= new AtomicInteger[NUMBER_OF_TYPES];
		for (int i=0; i<NUMBER_OF_TYPES;i++) {
			ids[i] = new AtomicInteger(0);
		}
	}
	private static final int NUMBER_OF_TYPES = 5;
	public static final int FORUM = 0;
	public static final int SUBFORUM = 1;
	public static final int THREAD= 2;
	public static final int MESSAGE= 3;
	public static final int USER = 4;
	
	public static int getId(int type) {
		return ids[type].getAndIncrement();
	}
	
}
