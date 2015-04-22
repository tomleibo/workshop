package bridge;


public class Driver {
	private static Proxy proxy;
	private static Real real;
	
	public static IForumSystemBridge getDriver(){
		if(real != null){
			real = new Real();
			return real;
		}

		proxy = new Proxy();

		return proxy;
	}
}
