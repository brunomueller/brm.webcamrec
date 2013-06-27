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

import brm.webcamrec.model.WebcamEntry;
import brm.webcamrec.util.LogPrint;

public class WebcamStart extends Action {
	private TableViewer viewer;

	public void init(TableViewer viewer) {
		this.viewer = viewer;

	}

	public void run() {
		LogPrint.note(this, "run:You selected WebcamStart");
		ISelection selection = viewer.getSelection();
		Object obj = ((IStructuredSelection) selection).getFirstElement();
		WebcamEntry webcamEntry = (WebcamEntry) obj;
		if (webcamEntry != null) {
			// LogPrint.note(this, "You selected \n" + webcamEntry.toString());
			webcamEntry.start();
		}

	}

}
