package brm.webcamrec.view;

import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import brm.webcamrec.ISharedImages;
import brm.webcamrec.WebcamrecPlugin;
import brm.webcamrec.actions.NewWebcamEntry;
import brm.webcamrec.actions.WebcamProperties;
import brm.webcamrec.actions.WebcamRemove;
import brm.webcamrec.actions.WebcamStart;
import brm.webcamrec.actions.WebcamStop;
import brm.webcamrec.model.WebcamContentProvider;
import brm.webcamrec.model.WebcamEntry;
import brm.webcamrec.model.WebcamListLabelProvider;
import brm.webcamrec.util.LogPrint;

/**
 * @author    schbrm
 */
public class WebcamView extends ViewPart implements ISelectionListener {
	public static final String ID = "brm.webcamrec.view.WebcamView"; //$NON-NLS-1$

	/**
	 * @uml.property  name="viewer"
	 */
	private TableViewer viewer;

	// private WebcamEntryCollection webcamCollection;

	/**
	 * @uml.property  name="newWebcamAction"
	 * @uml.associationEnd  
	 */
	private NewWebcamEntry newWebcamAction;

	/**
	 * @uml.property  name="startAction"
	 * @uml.associationEnd  
	 */
	private WebcamStart startAction;

	/**
	 * @uml.property  name="removeAction"
	 * @uml.associationEnd  
	 */
	private WebcamRemove removeAction;

	/**
	 * @uml.property  name="stopAction"
	 * @uml.associationEnd  
	 */
	private WebcamStop stopAction;

	/**
	 * @uml.property  name="propertiesAction"
	 * @uml.associationEnd  
	 */
	private WebcamProperties propertiesAction;

	private Action doubleClickAction;

	private Action singleClickAction;

	/**
	 * @uml.property  name="contentProvider"
	 * @uml.associationEnd  
	 */
	private WebcamContentProvider contentProvider;

	public WebcamView() {
		super();
		// webcamCollection =
		// WebcamrecPlugin.getDefault().getWebcamCollection();

		// add ourselve to plugin class so that other can communicate
		WebcamrecPlugin.getDefault().setWebcamView(this);

	}

	public void createPartControl(Composite parent) {
		LogPrint.note(this, "createPartControl:new");

		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL);

		// add columns to display
		TableColumn column;

		column = new TableColumn(viewer.getTable(), SWT.LEFT);
		column.setText("Name");
		column.setWidth(100);

		column = new TableColumn(viewer.getTable(), SWT.LEFT);
		column.setText("Status");
		column.setWidth(100);

		

	/*	
	  // create the sort listener for the sort event to take place
		Listener sortListener = new Listener() {
			public void handleEvent(Event event) {
				if (!(event.widget instanceof TableColumn)) {
					return;
				}
				Table table = viewer.getTable();
				TableColumn tc = (TableColumn) event.widget;
				sortTable(table, table.indexOf(tc));
				System.out.println("The table is sorted by column #"
						+ table.indexOf(tc));
			}
		};
		*/
/*
		// add the sort listener to all the columns
		Table table = viewer.getTable();
		for (int i = 0; i < table.getColumnCount(); i++) {
			((TableColumn) table.getColumn(i)).addListener(SWT.Selection,
					sortListener);
		}
*/
		viewer.getTable().setHeaderVisible(true);
		contentProvider = new WebcamContentProvider(this);
		// viewer.setContentProvider(new WebcamListModel());
		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(new WebcamListLabelProvider());
		viewer.setInput(this);

		// define ourselves as selection provider for the page
		// this allows other views to listen for selectionChanged
		// events
		getSite().setSelectionProvider(viewer);

		getViewSite().getPage().addSelectionListener(this);

		// create and add menu elements
		createActions();
		addContextMenu();
		// addSingleClickAction();
		// addDoubleClickAction();

		// contributeToActionBars();

	}

	public void setFocus() {
		LogPrint.note(this, "setFocus");
		viewer.getControl().setFocus();
	}

	/**
     * @return   Returns the viewer.
     * @uml.property   name="viewer"
     */
	public TableViewer getViewer() {
		LogPrint.note(this, "getViewer");
		return viewer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IViewPart#getViewSite()
	 */
	public IViewSite getViewSite() {
		// TODO Auto-generated method stub
		LogPrint.note(this, "getViewSite");
		return super.getViewSite();
	}

	private void createActions() {
		LogPrint.note(this, "createActions");
		newWebcamAction = new NewWebcamEntry();

		newWebcamAction.init(this);
		/*
		 * newWebcamAction = new Action() { public void run() { ISelection
		 * selection = viewer.getSelection(); Object obj =
		 * ((IStructuredSelection) selection) .getFirstElement();
		 * LogPrint.note(this, "You selected Action 1" + obj.toString()); } };
		 */
		newWebcamAction.setText("New Webcam Entry");
		newWebcamAction.setToolTipText("Create a new Webcam Entry");
		newWebcamAction
				.setImageDescriptor(getImageDescriptor(ISharedImages.NEW_BOOKMARK));
		// getImageDescriptor")

		// newWebcamAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
		// getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		stopAction = new WebcamStop();
		stopAction.init(viewer);
		stopAction.setText("Stop Recording");
		stopAction.setToolTipText("Stop Recording");
		stopAction
				.setImageDescriptor(getImageDescriptor(ISharedImages.LAUNCH_STOP));

		startAction = new WebcamStart();
		startAction.init(viewer);
		startAction.setText("Start Recording2");
		startAction.setToolTipText("Start Recording2");
		startAction
				.setImageDescriptor(getImageDescriptor(ISharedImages.LAUNCH_RUN));

		// propertiesAction = new Action() {
		// public void run() {
		//
		// ISelection selection = viewer.getSelection();
		// Object obj = ((IStructuredSelection) selection)
		// .getFirstElement();
		// LogPrint.note(this, "You selected propertiesAction"
		// + obj.toString());
		//
		// }
		// };

		removeAction = new WebcamRemove();
		removeAction.init(this);
		removeAction.setText("Remove");
		removeAction.setToolTipText("Remove");

		propertiesAction = new WebcamProperties();
		propertiesAction.init(this);

		propertiesAction.setText("Properties");
		propertiesAction.setToolTipText("Properties");

		// doubleClickAction = new Action() {
		// public void run() {
		// ISelection selection = viewer.getSelection();
		// Object obj = ((IStructuredSelection) selection)
		// .getFirstElement();
		// LogPrint.note(this, "You double-clicked " + obj.toString());
		// }
		// };

		// singleClickAction = new Action() {
		// public void run() {
		// ISelection selection = viewer.getSelection();
		// Object obj = ((IStructuredSelection) selection)
		// .getFirstElement();
		// LogPrint.note(this, "You single-clicked " + obj.toString());
		//
		// }
		// };

	}

	private void addContextMenu() {
		LogPrint.note(this, "addContextMenu");
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				WebcamView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void fillContextMenu(IMenuManager manager) {
		LogPrint.note(this, "fillContextMenu");
		manager.add(newWebcamAction);
		manager.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));

		manager.add(startAction);
		manager.add(stopAction);
		manager.add(removeAction);
		manager.add(propertiesAction);
		// manager.add(new Separator());
		// Other plug-ins can contribute there actions here
		// manager.add(new Separator("Additions"));
	}

	private void addDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	private void addSingleClickAction() {
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				LogPrint.note(this, event.toString());
				singleClickAction.run();
			}
		});
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		// fillLocalPullDown(bars.getMenuManager());
	}

	/*
	 * private void fillLocalPullDown(IMenuManager manager) {
	 * manager.add(newWebcamAction); manager.add(stopAction); }
	 */

	/**
	 * Returns the image descriptor with the given relative path.
	 */
	private ImageDescriptor getImageDescriptor(String relativePath) {
		// String iconPath = "icons/";

		// WebcamrecPlugin plugin = WebcamrecPlugin.getDefault();
		return WebcamrecPlugin.imageDescriptorFromPlugin(WebcamrecPlugin.ID,
				relativePath);

	}

	/*
	 * Listen for changes, so that we can adapt the popmenu based on the
	 * selection and the status of the webcamEntry (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.IWorkbenchPart,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		LogPrint.note(this, "selection changed ");
		// TODO Auto-generated method stub

		// only if proper sender
		if (part instanceof WebcamView) {

			// get the selection
			if (selection instanceof IStructuredSelection) {
				Object firstElement = ((IStructuredSelection) selection)
						.getFirstElement();
				WebcamEntry webcamEntry = (WebcamEntry) firstElement;

				// check if we got an element
				if (webcamEntry != null) {

					// webcam is running
					if (webcamEntry.isRunning() == true) {
						startAction.setEnabled(false);
						removeAction.setEnabled(false);
						propertiesAction.setEnabled(false);
						stopAction.setEnabled(true);
					} else {
						startAction.setEnabled(true);
						removeAction.setEnabled(true);
						propertiesAction.setEnabled(true);
						stopAction.setEnabled(false);
					}
				}

			}
		}
	}


}