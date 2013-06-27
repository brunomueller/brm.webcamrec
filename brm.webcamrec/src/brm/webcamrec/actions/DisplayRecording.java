/*
 * Created on Jan 26, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package brm.webcamrec.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import brm.webcamrec.util.LogPrint;
import brm.webcamrec.view.BrowserView;

/**
 * @author schbrm TODO To change the template for this generated type comment go
 *         to Window - Preferences - Java - Code Style - Code Templates
 */
public class DisplayRecording implements IViewActionDelegate {
	private IViewPart view;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IViewActionDelegate#init(org.eclipse.ui.IViewPart)
	 */
	public void init(IViewPart view) {
		// TODO Auto-generated method stub
		LogPrint.note(this, "init");
		this.view = view;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		// TODO Auto-generated method stub

		LogPrint.note(this, "run " + action.isChecked());
		informBrowser(action);

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
		informBrowser(action);
	}

	private void informBrowser(IAction action) {
		LogPrint.note(this, "informBrowser");
		BrowserView browserView = (BrowserView) view.getSite().getPage()
				.findView(BrowserView.ID);
		browserView.setDisplayRecording(action.isChecked());
	}

}
