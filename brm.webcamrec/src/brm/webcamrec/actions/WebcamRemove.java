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

import brm.webcamrec.WebcamrecPlugin;
import brm.webcamrec.model.WebcamEntry;
import brm.webcamrec.util.LogPrint;
import brm.webcamrec.view.WebcamView;

/**
 * @author    schbrm
 */
public class WebcamRemove extends Action {
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
		LogPrint.note(this, "run:You selected WebcamRemove");
		ISelection selection = viewer.getSelection();
		Object obj = ((IStructuredSelection) selection).getFirstElement();
		if (obj != null) {
			WebcamrecPlugin.getDefault().getWebcamCollection().remove((WebcamEntry) obj);
			// refresh the viwer
			webcamView.getViewer().refresh();
		}
	}

}
