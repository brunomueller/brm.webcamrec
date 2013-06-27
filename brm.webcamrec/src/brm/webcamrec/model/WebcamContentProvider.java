/*
 * Created on Jan 26, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package brm.webcamrec.model;

/**
 * @author schbrm
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import brm.webcamrec.WebcamrecPlugin;
import brm.webcamrec.util.LogPrint;
import brm.webcamrec.view.WebcamView;

/**
 * @author    schbrm
 */
public class WebcamContentProvider implements IStructuredContentProvider {
	/**
	 * @uml.property  name="input"
	 * @uml.associationEnd  
	 */
	WebcamEntryCollection input;

	/**
	 * @uml.property  name="viewer"
	 * @uml.associationEnd  
	 */
	WebcamView viewer;

	/**
	 * 
	 */
	public WebcamContentProvider(WebcamView view) {
		this.viewer = view;

	}

	/**
	 * Return the webcam entries, view knows about the webcam Collection
	 * 
	 * @see IStructuredContentProvider#getElements(Object)
	 */
	public Object[] getElements(Object element) {
		LogPrint.note(this, "getElements");
		LogPrint.note(this, "Class: " + element.getClass().getName());
		viewer = (WebcamView) element;
		
		return WebcamrecPlugin.getDefault().getWebcamCollection().elements().toArray();

	}

	/**
	 * @see IContentProvider#dispose()
	 */
	public void dispose() {
		LogPrint.note(this, "dispose");
		if (input != null)
			input.setListener(null);
		input = null;
		viewer = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see brm.webcamrec.model.BookmarkFile.Listener#added(brm.webcamrec.model.WebcamEntry)
	 */
	public void added(WebcamEntry w) {
		LogPrint.note(this, "added");
		// TODO Auto-generated method stub
		if (viewer != null)
			viewer.getViewer().add(w);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see brm.webcamrec.model.BookmarkFile.Listener#removed(brm.webcamrec.model.WebcamEntry)
	 */
	public void removed(WebcamEntry w) {
		LogPrint.note(this, "removed");
		// TODO Auto-generated method stub
		if (viewer != null) {
			viewer.getViewer().setSelection(null);
			viewer.getViewer().remove(w);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
	 *      java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		LogPrint.note(this, "inputChanged");
		// TODO Auto-generated method stub

	}
}
