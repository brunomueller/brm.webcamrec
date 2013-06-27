package brm.webcamrec.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import brm.webcamrec.WebcamrecPlugin;
import brm.webcamrec.util.LogPrint;

/**
 * Class used to initialize default preference values.
 */
public class ProxyInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		LogPrint.note(this, "initializeDefaultPreferences" );
		IPreferenceStore store = WebcamrecPlugin.getDefault()
				.getPreferenceStore();
		
		store.setDefault(PreferenceConstants.USE_PROXY_PREFERENCE, false);
		store.setDefault(PreferenceConstants.HTTP_PROXY_HOST_PREFERENCE, "a.b.c.d");
		store.setDefault(PreferenceConstants.HTTP_PROXY_PORT_PREFERENCE, 0);
	}

}
