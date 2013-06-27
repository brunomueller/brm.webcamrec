/*
 * Created on Jan 26, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package brm.webcamrec.wizard;

import org.eclipse.jface.wizard.Wizard;

import brm.webcamrec.WebcamrecPlugin;
import brm.webcamrec.model.WebcamEntry;
import brm.webcamrec.model.WebcamEntryCollection;
import brm.webcamrec.util.LogPrint;

/**
 * @author    schbrm
 */
public class WebcamEntryWizard extends Wizard {
	/**
	 * @uml.property  name="webcamEntry"
	 * @uml.associationEnd  
	 */
	private WebcamEntry webcamEntry;

	/**
	 * @uml.property  name="newEntry"
	 */
	private boolean newEntry;

	/**
	 * @uml.property  name="page"
	 * @uml.associationEnd  
	 */
	private WebcamEntryWizardPage page;

	/**
	 * 
	 */
	public WebcamEntryWizard(boolean newEntry) {
		super();
		this.newEntry = newEntry;
		LogPrint.note(this, "NewBookmarkWizzard() ");
		if (newEntry == true) {
			setWindowTitle("New Webcam Entry");
		} else {
			setWindowTitle("Edit Webcam Entry");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.IWizard#performFinish()
	 */
	public boolean performFinish() {
		LogPrint.note(this, "performFinish() ");
		// TODO Auto-generated method stub
		// WebcamEntryWizardPage page = (WebcamEntryWizardPage)
		// getStartingPage();

		// get all the fields as local variables
		/*
		 *        String pWebcamName = page.webcamName.getText().trim();
        String pWebcamURL = page.webcamURL.getText().trim();
        String pImageFile = page.imageFile.getText().trim();
        String pStartTime = page.startTime.getText().trim();
        String pEndTime = page.endTime.getText().trim();
        String pFrameTime = page.frameTime.getText().trim();
        String pReadDelay = page.readDelay.getText().trim();

		 */
		String pWebcamName = page.webcamName.getText().trim();
		String pWebcamURL = page.webcamURL.getText().trim();
		String pImageFile = page.imageFile.getText().trim();
		String pStartTime = page.startTime.getText().trim();
		String pEndTime = page.endTime.getText().trim();
		String pFrameTime = page.frameTime.getText().trim();
		String pReadDelay = page.readDelay.getText().trim();

		LogPrint.note(this, "performFinish() " + pWebcamName);
		if (pWebcamName.equalsIgnoreCase("")) {
			page.setErrorMessage("You must provide a Webcam name");
			page.webcamName.setCursor(null);
			// page.setPageComplete(false);
			return false;
		}

		if (pWebcamURL.equalsIgnoreCase("")) {
			page.setErrorMessage("You must provide a Webcam URL");
			// page.setPageComplete(false);
			page.webcamURL.setCursor(null);
			return false;
		}
		if (pImageFile.equalsIgnoreCase("")) {
			page.setErrorMessage("You must provide a Webcam URL");
			// page.setPageComplete(false);
			return false;
		}
		if (pStartTime.equalsIgnoreCase("")) {
			page.setErrorMessage("You must provide a Starttime");
			// page.setPageComplete(false);
			return false;
		}

		if (pEndTime.equalsIgnoreCase("")) {
			page.setErrorMessage("You must provide a Endtime");
			// page.setPageComplete(false);
			return false;
		}

		if (pFrameTime.equalsIgnoreCase("")) {
			page.setErrorMessage("You must provide a Frametime");
			// page.setPageComplete(false);
			return false;
		}

		if (pReadDelay.equalsIgnoreCase("")) {
			page.setErrorMessage("You must provide a Readdelay");
			// page.setPageComplete(false);
			return false;
		}

		// check for new webcam Entry
		// if new entry add new entry to collection
		//
		if (isNewEntry() == true) {
			webcamEntry = new WebcamEntry();
			WebcamEntryCollection entryCollection = WebcamrecPlugin.getDefault().getWebcamCollection();
			entryCollection.add(webcamEntry);
		}
		// move text from UI to webcamEntry
		webcamEntry.setWebcamName(pWebcamName);
		webcamEntry.setWebcamURL(pWebcamURL);
		webcamEntry.setImageFile(pImageFile);
		webcamEntry.setStartTime(pStartTime);
		webcamEntry.setEndTime(pEndTime);
		webcamEntry.setReadDelay(pReadDelay);
		webcamEntry.setFrameTime(pFrameTime);

		page.setErrorMessage(null);
		return true;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.IWizard#addPages()
	 */
	public void addPages() {
		super.addPages();
		LogPrint.note(this, "addPages() ");
		// TODO Auto-generated method stub
		page = new WebcamEntryWizardPage("webcamEntryPage");
		addPage(page);
		page.setTitle("Webcam Entry");
		page.setDescription("Define your WebcamEntry properties");

	}

	/**
	 * @return    Returns the webcamEntry.
	 * @uml.property  name="webcamEntry"
	 */
	public WebcamEntry getWebcamEntry() {
        return webcamEntry;
    }

	/**
	 * @param webcamEntry    The webcamEntry to set.
	 * @uml.property  name="webcamEntry"
	 */
	public void setWebcamEntry(WebcamEntry webcamEntry) {
        this.webcamEntry = webcamEntry;
    }

	/**
	 * @return    Returns the newEntry.
	 * @uml.property  name="newEntry"
	 */
	public boolean isNewEntry() {
        return newEntry;
    }

}
