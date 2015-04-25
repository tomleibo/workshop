package acceptanceTests.bridge;


public class Driver {
	private static Proxy proxy;
	private static Real real;
	
	public static IForumSystemBridge getDriver(){

		proxy = new Proxy();
		proxy.initRealImplement();

		return proxy;
	}
}
