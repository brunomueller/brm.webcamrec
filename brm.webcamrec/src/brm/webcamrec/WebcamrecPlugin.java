package brm.webcamrec;

import java.io.IOException;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import brm.webcamrec.model.WebcamEntryCollection;
import brm.webcamrec.preferences.PreferenceConstants;
import brm.webcamrec.util.LogPrint;
import brm.webcamrec.view.WebcamView;

/**
 * @author    schbrm
 */
public class WebcamrecPlugin extends AbstractUIPlugin {
	
	public static final String ID = "brm.webcamrec";
	
	public static String WEBCAM_FILENAME = "webcamrbookmark.xml";
	
	//The shared instance.
	/**
	 * @uml.property  name="plugin"
	 * @uml.associationEnd  
	 */
	private static WebcamrecPlugin plugin;
	//Resource bundle.
	/**
	 * @uml.property  name="resourceBundle"
	 */
	private ResourceBundle resourceBundle;
	
	/**
	 * @uml.property  name="webcamCollection"
	 * @uml.associationEnd  
	 */
	private WebcamEntryCollection webcamCollection;
	
	/**
	 * @uml.property  name="webcamView"
	 * @uml.associationEnd  
	 */
	private WebcamView webcamView;
	
	/**
	 * @uml.property  name="messageConsole"
	 */
	private MessageConsole messageConsole;
	
	/**
	 * The constructor.
	 */
	public WebcamrecPlugin() {
		super();
		plugin = this;

	

	}
	

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		LogPrint.note(this, "start");
		String workspaceLocation = WebcamrecPlugin.getDefault().getStateLocation().append("myFile.txt").toString();
		LogPrint.note(this, "workspaceLocation=" + workspaceLocation );
		
		setProxySetting();
		readWebcamFile();
		LogPrint.note(this, "WebcamrecPlugin");
		
				
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		LogPrint.note(this, "stop");
		writeWebcamFile();
		plugin = null;
		resourceBundle = null;
		
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 */
	public static WebcamrecPlugin getDefault() {

		return plugin;
	}

	/**
	 * Returns the string from the plugin's resource bundle,
	 * or 'key' if not found.
	 */
	public static String getResourceString(String key) {
		System.out.print("NOTE: getResourceString() key=" + key + "\n");
		ResourceBundle bundle = WebcamrecPlugin.getDefault().getResourceBundle();
		try {
			return (bundle != null) ? bundle.getString(key) : key;
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Returns the plugin's resource bundle
	 * @uml.property  name="resourceBundle"
	 */
	public ResourceBundle getResourceBundle() {
		
		LogPrint.note(this, "getResourceBundle");
		try {
			if (resourceBundle == null)
				resourceBundle = ResourceBundle.getBundle("brm.webcamrec.WebcamrecPluginResources");
			System.out.print("NOTE: Resource" + resourceBundle.toString());
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
		return resourceBundle;
	}
	
	private void readWebcamFile() {
		LogPrint.note(this, "readWebcamFile");
		LogPrint.note(this, "install location"
				+ Platform.getInstallLocation().getDefault());
		
		webcamCollection = new WebcamEntryCollection();

		try {
			String fileName = getWebcamFileName().toExternalForm();
			webcamCollection.readFile(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void writeWebcamFile() {
		LogPrint.note(this, "writeWebcamFile");
		try {
			// need to get the physicalname, otherwise it would not work
			String fileName = getWebcamFileName().getFile();
			webcamCollection.writeFile(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
	}

	/**
	 * @return    Returns the webcamCollection.
	 * @uml.property  name="webcamCollection"
	 */
	public WebcamEntryCollection getWebcamCollection() {
        return webcamCollection;
    }
	
	/**
	 * Returns the URL containing the location on where to store
	 * webcam entries
	 * @return
	 * @throws IOException
	 */
	public URL getWebcamFileName() throws IOException {
		// TODO check depracated
		
		
		LogPrint.note(this, "getWebcamFileNAme");
		Bundle bundle = Platform.getBundle(WebcamrecPlugin.ID);
		Path path = new Path(WebcamrecPlugin.WEBCAM_FILENAME);
		URL fileURL = Platform.find(bundle, path);
//		if ( fileURL == null ) {
//			fileURL = createWebcamFile(path);
//		}
		LogPrint.note(this, "filename=" + fileURL);
		return Platform.asLocalURL(fileURL);
	}
	
//	private URL createWebcamFile(Path filePath) {
//		
//		return new URL(filePath.toString());
//	}

	/**
	 * @return    Returns the webcamView.
	 * @uml.property  name="webcamView"
	 */
	public WebcamView getWebcamView() {
        return webcamView;
    }

	/**
	 * @param webcamView    The webcamView to set.
	 * @uml.property  name="webcamView"
	 */
	public void setWebcamView(WebcamView webcamView) {
        this.webcamView = webcamView;
    }
	
	public void setProxySetting() {
		LogPrint.note(this, "setProxySetting");
		IPreferenceStore prefStore = getPreferenceStore();
		LogPrint.note(this, "useProxy:" + prefStore.getBoolean(PreferenceConstants.USE_PROXY_PREFERENCE));
		LogPrint.note(this, "proxyHost:" + prefStore.getString(PreferenceConstants.HTTP_PROXY_HOST_PREFERENCE));
		LogPrint.note(this, "proxyPort:" + prefStore.getString(PreferenceConstants.HTTP_PROXY_PORT_PREFERENCE));
		
		if (getPreferenceStore().getBoolean(PreferenceConstants.USE_PROXY_PREFERENCE)) {
		    System.setProperty(PreferenceConstants.USE_PROXY, "true");
		    System.setProperty(PreferenceConstants.HTTP_PROXY_HOST, 
		    	getPreferenceStore().getString(PreferenceConstants.HTTP_PROXY_HOST_PREFERENCE));
		    System.setProperty(PreferenceConstants.HTTP_PROXY_PORT, 
		    	getPreferenceStore().getString(PreferenceConstants.HTTP_PROXY_PORT_PREFERENCE));
		}
	}


	/**
	 * @return    Returns the messageConsole.
	 * @uml.property  name="messageConsole"
	 */
	public MessageConsole getMessageConsole() {
		return messageConsole;
	}


	/**
	 * @param messageConsole    The messageConsole to set.
	 * @uml.property  name="messageConsole"
	 */
	public void setMessageConsole(MessageConsole messageConsole) {
		this.messageConsole = messageConsole;
	}


	
}
