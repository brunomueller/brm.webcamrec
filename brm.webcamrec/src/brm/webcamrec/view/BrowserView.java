/*
 * Created on Jan 25, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package brm.webcamrec.view;

/**
 * @author schbrm
 */

import java.io.File;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import brm.webcamrec.model.WebcamEntry;
import brm.webcamrec.util.LogPrint;

/**
 * @author    schbrm
 */
public class BrowserView extends ViewPart implements ISelectionListener {
	/**
	 * @uml.property  name="iD"
	 */
	public static final String ID = "brm.webcamrec.view.BrowserView"; //$NON-NLS-1$

	public static final String FILE_PROTO = "file:///"; //$NON-NLS-1$

	public static final String ABOUT = "about:blank"; //$NON-NLS-1$

	private Browser browser;

	/**
	 * @uml.property  name="displayRecording"
	 */
	private boolean displayRecording;

	/**
	 * @uml.property  name="currentWebcam"
	 * @uml.associationEnd  
	 */
	private WebcamEntry currentWebcam;

	/**
	 * @return  Returns the iD.
	 * @uml.property  name="iD"
	 */
	public static String getID() {
		return ID;
	}

	public void createPartControl(Composite parent) {
		LogPrint.note(this, "createPartControl");
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		gridLayout.marginHeight = 2;
		gridLayout.marginWidth = 5;
		parent.setLayout(gridLayout);

		// listen for selection changed events
		getViewSite().getPage().addSelectionListener(this);

		browser = new Browser(parent, SWT.NONE);

		browser.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.GRAB_VERTICAL | GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL));

		browser.setUrl(BrowserView.ABOUT);

	}

	public void browserRefresh() {
		browser.refresh();
	}

	/**
	 * @see ISelectionListener#selectionChanged(IWorkbenchPart, ISelection)
	 */
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		LogPrint.note(this, "selectionChanged");
		WebcamView viewer;
		// WebcamEntry bookmark = null;
		if (part instanceof WebcamView) {
			viewer = (WebcamView) part;
			if (selection instanceof IStructuredSelection) {

				Object firstElement = ((IStructuredSelection) selection)
						.getFirstElement();

				// check if we got an element
				if (firstElement != null) {
					currentWebcam = (WebcamEntry) firstElement;

					setBrowserURL();

				}

			}
		}
	}

	public void setBrowserURL() {
		// set text in tabber
		
		// we may not have a webcam yet
		if (currentWebcam != null) {
			setPartName(currentWebcam.getWebcamName());

			if (isDisplayRecording()) {
				// check whether file exists and act accordingly
				File imgFile = new File(currentWebcam.getImageFile());
				if (imgFile.exists()) {
					setContentDescription(currentWebcam.getStartTime() + " to "
							+ currentWebcam.getEndTime() + " :"
							+ currentWebcam.getReadDelay() + ": stored as "
							+ currentWebcam.getImageFile());
					browser.setUrl(BrowserView.FILE_PROTO
							+ currentWebcam.getImageFile());
				} else {
					setContentDescription(imgFile.toString()
							+ " does not exist");
					browser.setUrl(BrowserView.ABOUT);
				}
			} else {
				browser.setUrl(currentWebcam.getWebcamURL());
				setContentDescription(currentWebcam.getWebcamURL());

			}
		}

	}

	public void setFocus() {
		LogPrint.note(this, "setFocus");
		// TODO Auto-generated method stub

	}

	/**
	 * @return    Returns the displayRecording.
	 * @uml.property  name="displayRecording"
	 */
	public boolean isDisplayRecording() {
        return displayRecording;
    }

	/**
	 * @param displayRecording    The displayRecording to set.
	 * @uml.property  name="displayRecording"
	 */
	public void setDisplayRecording(boolean displayType) {
        LogPrint.note(this, "setDisplayRecording");
        boolean oldStatus = displayRecording;
        this.displayRecording = displayType;
        setBrowserURL();
    
    }
}