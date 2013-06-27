/*
 * Created on Mar 8, 2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package brm.webcamrec.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardDialog;

import brm.webcamrec.model.WebcamEntry;
import brm.webcamrec.util.LogPrint;
import brm.webcamrec.view.WebcamView;
import brm.webcamrec.wizard.WebcamEntryWizard;

/**
 * @author    schbrm
 */
public class WebcamProperties extends Action {
	private TableViewer viewer;
	/**
	 * @uml.property  name="webcamView"
	 * @uml.associationEnd  
	 */
	private WebcamView webcamView;

	public void init(WebcamView webcamView) {
		this.webcamView = webcamView;
		this.viewer = webcamView.getViewer();

	}

	public void run() {
		LogPrint.note(this, "run:You selected WebcamProperties");
		ISelection selection = viewer.getSelection();
		Object obj = ((IStructuredSelection) selection).getFirstElement();
		if (obj != null) {
			LogPrint.note(this, "You selected \n" + obj.toString());
			openWizard(false);
		}
	}

	private void openWizard(boolean newEntry) {
		LogPrint.note(this, "openWizard");
	
		WebcamEntryWizard wizard = new WebcamEntryWizard(false);
		WizardDialog dialog = new WizardDialog(webcamView.getViewSite()
				.getShell(), wizard);
	
		ISelection selection = webcamView.getViewer().getSelection();
		WebcamEntry webcam = (WebcamEntry) ((IStructuredSelection) selection)
				.getFirstElement();
	
		if (newEntry == false & webcam != null) {
			wizard.setWebcamEntry(webcam);
		}
	
		dialog.open();
		
		// refresh the viwer
		webcamView.getViewer().refresh();
		
		
		LogPrint.note(this, "after::openWizard");
	
	}

}
