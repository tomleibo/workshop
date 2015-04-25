package acceptanceTests.bridge;


public class Driver {
	private static Proxy proxy;

	public static IForumSystemBridge getDriver(){

		proxy = new Proxy();
		proxy.initRealImplement();

		return proxy;
	}
}
