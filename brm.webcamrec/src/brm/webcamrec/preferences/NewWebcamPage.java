package brm.webcamrec.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
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

public class NewWebcamPage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	private DirectoryFieldEditor imageDir;

	private IntegerFieldEditor readDelay;

	private IntegerFieldEditor frameTime;

	private IntegerFieldEditor timeOffset;

	private BooleanFieldEditor timeNow;

	public NewWebcamPage() {
		super(GRID);
		setTitle("Webcam Recorder Preferences");
		setPreferenceStore(WebcamrecPlugin.getDefault().getPreferenceStore());
		setDescription("Default Setting for a new Webcam entry");

	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {
		LogPrint.note(this, "createFieldEditors");
		imageDir = new DirectoryFieldEditor(PreferenceConstants.IMAGE_DIR,
				"Image Directory:", getFieldEditorParent());

		addField(imageDir);

		readDelay = new IntegerFieldEditor(PreferenceConstants.READ_DELAY,
				"ReadDelay in Sec:", getFieldEditorParent());

		addField(readDelay);

		frameTime = new IntegerFieldEditor(PreferenceConstants.FRAME_TIME,
				"FrameTime in ms:", getFieldEditorParent());

		addField(frameTime);

		timeOffset = new IntegerFieldEditor(PreferenceConstants.TIME_OFFSET,
				"Time Offset in Min:", getFieldEditorParent());
		addField(timeOffset);

		timeNow = new BooleanFieldEditor(PreferenceConstants.TIME_NOW,
				"Use TimeNow:", getFieldEditorParent());
		addField(timeNow);
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	}
	


}