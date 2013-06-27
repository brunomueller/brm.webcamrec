/*
 * Created on Jan 26, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package brm.webcamrec.wizard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import brm.webcamrec.WebcamrecPlugin;
import brm.webcamrec.model.WebcamEntry;
import brm.webcamrec.preferences.PreferenceConstants;
import brm.webcamrec.util.LogPrint;

/**
 * @author    schbrm
 */
public class WebcamEntryWizardPage extends WizardPage implements Listener {
	/**
	 * @uml.property  name="webcamName"
	 */
	Text webcamName;

	/**
	 * @uml.property  name="webcamURL"
	 */
	Text webcamURL;

	/**
	 * @uml.property  name="imageFile"
	 */
	Text imageFile;

	/**
	 * @uml.property  name="readDelay"
	 */
	Text readDelay;

	/**
	 * @uml.property  name="frameTime"
	 */
	Text frameTime;

	/**
	 * @uml.property  name="startTime"
	 */
	Text startTime;

	/**
	 * @uml.property  name="endTime"
	 */
	Text endTime;

	Button timeNow;

	/**
	 * @param pageName
	 */
	protected WebcamEntryWizardPage(String pageName) {
		super(pageName);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		GridLayout pageLayout = new GridLayout();
		pageLayout.numColumns = 2;
		parent.setLayout(pageLayout);
		parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Label bmNameLabel = new Label(parent, SWT.NONE);
		bmNameLabel.setText("WebcamEntry Name:");
		webcamName = new Text(parent, SWT.BORDER);
		webcamName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// add an event listner for changes in text field
		webcamName.addListener(SWT.Modify, this);

		Label bmURLLabel = new Label(parent, SWT.NONE);
		bmURLLabel.setText("WebcamEntry URL:");
		webcamURL = new Text(parent, SWT.BORDER);
		webcamURL.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label bmFileLabel = new Label(parent, SWT.NONE);
		bmFileLabel.setText("FileName:");
		imageFile = new Text(parent, SWT.BORDER);
		imageFile.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label bmReadDelayLabel = new Label(parent, SWT.NONE);
		bmReadDelayLabel.setText("Readdelay in Sec:");
		readDelay = new Text(parent, SWT.BORDER);
		readDelay.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		readDelay.addListener(SWT.Modify, this);

		Label bmFrameTimeLabel = new Label(parent, SWT.NONE);
		bmFrameTimeLabel.setText("FrameTime in ms:");
		frameTime = new Text(parent, SWT.BORDER);
		frameTime.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		frameTime.addListener(SWT.Modify, this);

		Label bmStartTimeLabel = new Label(parent, SWT.NONE);
		bmStartTimeLabel.setText("Starttime:");
		startTime = new Text(parent, SWT.BORDER);
		startTime.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		startTime.addListener(SWT.Modify, this);

		Label bmStopTimeLabel = new Label(parent, SWT.NONE);
		bmStopTimeLabel.setText("Stoptime:");
		endTime = new Text(parent, SWT.BORDER);
		endTime.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		endTime.addListener(SWT.Modify, this);

		timeNow = new Button(parent, SWT.PUSH);
		timeNow.setText("Time Now");
		timeNow.addListener(SWT.Selection, this);

		setControl(parent);

		// set fields to their default values from preferences
		initFields();

	}

	/**
	 * This method fills the fields in the page with the information from an
	 * webcam Entry or uses the defaults from the prefrence store
	 */
	private void initFields() {
		WebcamEntryWizard wizard = (WebcamEntryWizard) getWizard();
		WebcamEntry tempWebcamEntry = wizard.getWebcamEntry();

		// we already have a webcam entry
		if (tempWebcamEntry != null) {
			setWebcamName(tempWebcamEntry.getWebcamName());
			setWebcamURL(tempWebcamEntry.getWebcamURL());
			setImageFile(tempWebcamEntry.getImageFile());
			setReadDelay(tempWebcamEntry.getReadDelay());
			setFrameTime(tempWebcamEntry.getFrameTime());
			setStartTime(tempWebcamEntry.getStartTime());
			setEndTime(tempWebcamEntry.getEndTime());
		} else {
			IPreferenceStore prefStore = getPreferenceStore();
			// setWebcamName("-webcamName-);
			// setWebcamURL(tempWebcamEntry.getWebcamURL());
			setImageFile(prefStore.getString(PreferenceConstants.IMAGE_DIR));
			setReadDelay(prefStore.getString(PreferenceConstants.READ_DELAY));
			setFrameTime(prefStore.getString(PreferenceConstants.FRAME_TIME));
			// setStartTime(tempWebcamEntry.getStartTime());
			// setEndTime(tempWebcamEntry.getEndTime());

		}

	}

	private IPreferenceStore getPreferenceStore() {
		return WebcamrecPlugin.getDefault().getPreferenceStore();

	}

	/*
	 * Handle all the events from the widgets, is used here for verification of
	 * contents (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
	 */
	public void handleEvent(Event e) {
		LogPrint.note(this, "Event for" + e);
		
		if (e.widget == webcamName) {
			// LogPrint.note(this, "changed webcamName");
			// String tempFile = imageFile.getText();
			// tempFile = tempFile + webcamName.getText() + ".gif";
			// imageFile.setText(tempFile);
		}
		if (e.widget == timeNow) {
			// LogPrint.note(this, "timeNow pressed");
			updateTime();
		}

		if (e.widget == startTime) {
			LogPrint.note(this, "changed startTime");
			checkTime(startTime);
		}

		if (e.widget == endTime) {
			// LogPrint.note(this, "changed stopTime");
			checkTime(endTime);
		}
		
		if (e.widget == readDelay) {
			LogPrint.note(this, "changed stopTime");
			checkInteger(readDelay);
		}

		if (e.widget == frameTime) {
			LogPrint.note(this, "changed stopTime");
			checkInteger(frameTime);
		}

	}

	private void checkInteger(Text integerField) {
		String numberString = integerField.getText();
		try {
			int number = Integer.valueOf(numberString).intValue();
			setErrorMessage(null);

		} catch (NumberFormatException e1) {
			this.setErrorMessage("Must be a number");
		}
	}

	private void checkTime(Text timeField) {
		SimpleDateFormat dateFmt = new SimpleDateFormat(WebcamEntry.TIME_FMT);
		// switch on strict date parsing
		dateFmt.setLenient(false);

		Date testDate = null;
		try {
			testDate = dateFmt.parse(timeField.getText());
			// clear the error message
			this.setErrorMessage(null);
		} catch (ParseException e) {
			this.setErrorMessage("Time must be in the format "
					+ dateFmt.toPattern());

		}

	}

	private void updateTime() {
		int startTimeOffset = 1;
		int endTimeOffset = getPreferenceStore().getInt(
				PreferenceConstants.TIME_OFFSET);

		Date tempStartTime = new Date(new Date().getTime() + (60 * 1000)
				* startTimeOffset);
		Date tempEndTime = new Date(tempStartTime.getTime() + (60 * 1000)
				* endTimeOffset);

		String timeNow1 = new SimpleDateFormat(WebcamEntry.TIME_FMT)
				.format(tempStartTime);
		startTime.setText(timeNow1);
		String timeNow2 = new SimpleDateFormat(WebcamEntry.TIME_FMT)
				.format(tempEndTime);
		endTime.setText(timeNow2);
	}

	/**
	 * @return    Returns the frameTime.
	 * @uml.property  name="frameTime"
	 */
	public String getFrameTime() {
        return frameTime.getText();
    }

	public void setFrameTime(String frameTime) {
		this.frameTime.setText(frameTime);
	}

	/**
	 * @return    Returns the imageFile.
	 * @uml.property  name="imageFile"
	 */
	public String getImageFile() {
        return imageFile.getText();
    }

	public void setImageFile(String imageFile) {
		this.imageFile.setText(imageFile);
	}

	/**
	 * @return    Returns the readDelay.
	 * @uml.property  name="readDelay"
	 */
	public String getReadDelay() {
        return readDelay.getText();
    }

	public void setReadDelay(String readDelay) {
		this.readDelay.setText(readDelay);
	}

	/**
	 * @return    Returns the startTime.
	 * @uml.property  name="startTime"
	 */
	public String getStartTime() {
        return startTime.getText();
    }

	public void setStartTime(String startTime) {
		this.startTime.setText(startTime);
	}

	/**
	 * @return    Returns the endTime.
	 * @uml.property  name="endTime"
	 */
	public String getEndTime() {
        return endTime.getText();
    }

	public void setEndTime(String endTime) {
		this.endTime.setText(endTime);
	}

	/**
	 * @return    Returns the webcamName.
	 * @uml.property  name="webcamName"
	 */
	public String getWebcamName() {
        return webcamName.getText();
    }

	public void setWebcamName(String webcamName) {
		this.webcamName.setText(webcamName);
	}

	/**
	 * @return    Returns the webcamURL.
	 * @uml.property  name="webcamURL"
	 */
	public String getWebcamURL() {
        return webcamURL.getText();
    }

	public void setWebcamURL(String webcamURL) {
		this.webcamURL.setText(webcamURL);
	}

}
