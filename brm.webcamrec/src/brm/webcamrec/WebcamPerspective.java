/*
 * Next, you must define at least one perspective and make it the default.
 * Perspectives are created by implementing IPerspectiveFactory
 * using the class name referred to by the org.eclipse.ui.perspectives
 * extension (see listing 3).
 * The important part of this interface is the createInitialLayout()
 * method where you position and open any views and/or editors you'd
 * like the user to start with.
 * In this example we're not going to create any views so it will be
 * a pretty boring perspective.
 */
package brm.webcamrec;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import brm.webcamrec.util.LogPrint;
import brm.webcamrec.view.BrowserView;
import brm.webcamrec.view.WebcamView;

public class WebcamPerspective implements IPerspectiveFactory {
	public static final String ID = "brm.webcamrec.WebcamPerspective"; //$NON-NLS-1$

	public WebcamPerspective() {
		LogPrint.note(this, "WebcamPerspective");
	}

	public void createInitialLayout(IPageLayout layout) {
		LogPrint.note(this, "createInitialLayout");
		layout.setEditorAreaVisible(false);
		layout.addView(WebcamView.ID, IPageLayout.LEFT, 0.3f,
				IPageLayout.ID_EDITOR_AREA);
		layout.addView(BrowserView.ID, IPageLayout.RIGHT, 0.7f,
				IPageLayout.ID_EDITOR_AREA);

		// add shortcuts to the windows menu items
		layout.addPerspectiveShortcut(ID);
		layout.addShowViewShortcut(WebcamView.ID);
		layout.addShowViewShortcut(BrowserView.ID);

		// make the WebcamView nonCloseable
		layout.getViewLayout(WebcamView.ID).setCloseable(false);
		layout.getViewLayout(BrowserView.ID).setCloseable(false);

	}
}
