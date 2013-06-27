/*
 * Created on Jan 26, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package brm.webcamrec.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import brm.webcamrec.util.LogPrint;
import brm.webcamrec.view.WebcamView;
import brm.webcamrec.wizard.WebcamEntryWizard;

/**
 * @author    schbrm
 */
public class NewWebcamEntry extends Action implements IViewActionDelegate {

	/**
	 * @uml.property  name="webcamView"
	 * @uml.associationEnd  
	 */
	private WebcamView webcamView;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IViewActionDelegate#init(org.eclipse.ui.IViewPart)
	 */
	public void init(IViewPart view) {
		// TODO Auto-generated method stub
		LogPrint.note(this, "init");
		this.webcamView = (WebcamView) view;

	}

	public void run() {
		// this method runs when called from the popup menu
		LogPrint.note(this, "run");
		openWizard();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		// this method runs when called from the local pulldown menu
		LogPrint.note(this, "run IAction");
		openWizard();

	}

	private void openWizard() {
		LogPrint.note(this, "openWizard");

		WebcamEntryWizard wizard = new WebcamEntryWizard(true);
		WizardDialog dialog = new WizardDialog(webcamView.getViewSite()
				.getShell(), wizard);

		// do not need this, since it is always new		
		// ISelection selection = webcamView.getViewer().getSelection();
		// WebcamEntry webcam = (WebcamEntry) ((IStructuredSelection) selection)
		// .getFirstElement();
		//
		// if (newEntry == false & webcam != null) {
		// wizard.setWebcamEntry(webcam);
		//		}

		dialog.open();
		
		webcamView.getViewer().refresh();

		LogPrint.note(this, "after::openWizard");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		LogPrint.note(this, "selectionChanged");

	}

}
