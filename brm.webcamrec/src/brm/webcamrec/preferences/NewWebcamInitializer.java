package brm.webcamrec.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import brm.webcamrec.WebcamrecPlugin;
import brm.webcamrec.util.LogPrint;

/**
 * Class used to initialize default preference values.
 */
public class NewWebcamInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		LogPrint.note(this, "initializeDefaultPreferences" );
		IPreferenceStore store = WebcamrecPlugin.getDefault()
				.getPreferenceStore();
		store.setDefault(PreferenceConstants.IMAGE_DIR, "d:\\temp");
		store.setDefault(PreferenceConstants.READ_DELAY, 45);
		store.setDefault(PreferenceConstants.FRAME_TIME, 750);
		store.setDefault(PreferenceConstants.TIME_OFFSET, 10);
	}

}
