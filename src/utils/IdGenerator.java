package utils;

public class IdGenerator {
	private static IdGenerator instance=null;
	private int[] ids; 
	private static final int NUMBER_OF_TYPES = 5;
	public static final int FORUM = 0;
	public  static final int SUBFORUM = 1;
	public static final int THREAD= 2;
	public static final int MESSAGE= 3;
	public static final int USER = 4;
	
	private IdGenerator() {
		ids= new int[NUMBER_OF_TYPES];
		for (int i=0; i<NUMBER_OF_TYPES;i++) {
			ids[i] =0;
		}
	}
	
	public static int getId(int type) {
		if (instance==null) {
			synchronized (IdGenerator.class) {
				if (instance==null) {
					instance = new IdGenerator();
				}
			}
		}
		return instance.ids[type]++;
	}
	
}
