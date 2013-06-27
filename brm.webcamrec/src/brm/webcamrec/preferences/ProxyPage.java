package brm.webcamrec.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import brm.webcamrec.WebcamrecPlugin;
import brm.webcamrec.util.LogPrint;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>,
 * we can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class ProxyPage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	private BooleanFieldEditor useProxyEditor = null;

	private StringFieldEditor proxyHostEditor = null;

	private IntegerFieldEditor proxyPortEditor = null;

	public ProxyPage() {
		super(GRID);
		LogPrint.note(this, ":constructor:");
		setTitle("Webcam Recorder Preferences");
		setPreferenceStore(WebcamrecPlugin.getDefault().getPreferenceStore());
		setDescription("Proxy Settings");

	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {
		LogPrint.note(this, "createFieldEditors");

		useProxyEditor = new BooleanFieldEditor(
				PreferenceConstants.USE_PROXY_PREFERENCE,
				"Use_ProxyPreference", getFieldEditorParent());
		addField(useProxyEditor);

		proxyHostEditor = new StringFieldEditor(
				PreferenceConstants.HTTP_PROXY_HOST_PREFERENCE,
				"HTTP_ProxyHost", getFieldEditorParent());
		addField(proxyHostEditor);

		proxyPortEditor = new IntegerFieldEditor(
				PreferenceConstants.HTTP_PROXY_PORT_PREFERENCE, "ProxyPort",
				getFieldEditorParent());
		addField(proxyPortEditor);
		updateFieldStatus(getPreferenceStore().getBoolean(PreferenceConstants.USE_PROXY_PREFERENCE));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
        super.propertyChange(event);
		
		// check whether source of event is the boolean
		// if yes update the fields accordingly
		if ( event.getSource() == useProxyEditor ) {
			updateUI();
		}
        
    }
    
    private void updateUI() {
        updateFieldStatus(useProxyEditor.getBooleanValue());
    }

    private void updateFieldStatus(boolean proxy) {
		LogPrint.note(this, "updateFieldStatus" + proxy);
    	proxyHostEditor.getTextControl(getFieldEditorParent()).setEditable(proxy);
    	proxyHostEditor.getTextControl(getFieldEditorParent()).setEnabled(proxy);
    	proxyPortEditor.getTextControl(getFieldEditorParent()).setEditable(proxy);
    	proxyPortEditor.getTextControl(getFieldEditorParent()).setEnabled(proxy);
    }


	protected void performDefaults() {
		// TODO Auto-generated method stub
		super.performDefaults();
		LogPrint.note(this, "performDefaults");
	}

	/**
	 * This method runs when the Ok button is pressed
	 * It is this method that can be used to apply the preferences
	 */
	public boolean performOk() {
		// TODO Auto-generated method stub
		LogPrint.note(this, "performOk");
		return super.performOk();
	}


	/**
	 * This method runs when the Apply button is pressed
	 */
	protected void performApply() {
		// TODO Auto-generated method stub
		LogPrint.note(this, "performApply");
		super.performApply();
	}

	public boolean performCancel() {
		// TODO Auto-generated method stub
		LogPrint.note(this, "performCancel");
		return super.performCancel();
	}
}