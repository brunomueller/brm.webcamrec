package brm.webcamrec.preferences;

/**
 * Constant definitions for plug-in preferences
 */
public class PreferenceConstants {

//	Preferences for new Webcam Entry
	public static final String IMAGE_DIR = "webcamentry.imageDirectory";
	
	public static final String READ_DELAY = "webcamentry.readDelay";
	
	public static final String FRAME_TIME = "webcamentry.frameTime";
	
	public static final String TIME_OFFSET = "webcamentry.timeOffset";
	
	public static final String TIME_NOW = "webcamentry.timeNow";
	
	
	//Preferences concerning proxy
    public static final String USE_PROXY_PREFERENCE = "proxysettings.use";
    public static final String HTTP_PROXY_HOST_PREFERENCE = "proxysettings.host";
    public static final String HTTP_PROXY_PORT_PREFERENCE = "proxysettings.port";
    public static final String HTTP_NON_PROXY_HOSTS_PREFERENCE = "proxysettings.nonhosts";
	
	// Java VM system properties names for proxy
	public static final String HTTP_PROXY_HOST = "http.proxyHost";
	public static final String HTTP_PROXY_PORT = "http.proxyPort";
	public static final String USE_PROXY = "proxySet";

	
}
