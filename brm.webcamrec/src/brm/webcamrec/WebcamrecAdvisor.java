/*
 * The Workbench Advisor class, which you referred to in the main program,
 * helps customize the workbench to add and subtract toolbars, perspectives,
 * and so forth.
 * This will covered in more detail in part 2 of the tutorial.
 * For now, the absolute minimum you have to do here is define which
 * perspective is the default one. See listing 4 for the code
 */
package brm.webcamrec;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;

import brm.webcamrec.util.LogPrint;

public class WebcamrecAdvisor extends WorkbenchAdvisor {

	public static final String WEBCAM_RECORDER_CONSOLE = "Webcam Recorder Console";

	private static final Point DEFAULT_SIZE = new Point(1000, 700);

	public String getInitialWindowPerspectiveId() {
		LogPrint.note(this, "getInitialWindowPerspectiveId");
		return "brm.webcamrec.WebcamPerspective"; //$NON-NLS-1$
	}

	public void preWindowOpen(IWorkbenchWindowConfigurer configurer) {

		LogPrint.note(this, "preWindowOpen");
		// define initial size of application
		configurer.setInitialSize(getDisplaySize());
		// configurer.setInitialSize(DEFAULT_SIZE);
		configurer.setShowCoolBar(false);
		configurer.setShowStatusLine(true);
		configurer.setTitle(Messages.getString("window.title")); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.application.WorkbenchAdvisor#initialize(org.eclipse.ui.application.IWorkbenchConfigurer)
	 */
	public void initialize(IWorkbenchConfigurer configurer) {
		// TODO Auto-generated method stub
		super.initialize(configurer);
		LogPrint.note(this, "initialize");

		// save window Layout
		// configurer.setSaveAndRestore(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.application.WorkbenchAdvisor#fillActionBars(org.eclipse.ui.IWorkbenchWindow,
	 *      org.eclipse.ui.application.IActionBarConfigurer, int)
	 */
	public void fillActionBars(IWorkbenchWindow window,
			IActionBarConfigurer configurer, int flags) {
		// TODO Auto-generated method stub
		// super.fillActionBars(window, configurer, flags);
		LogPrint.note(this, "fillActionBars");
		if ((flags  /* & FILL_MENU_BAR */ ) != 0) {
			fillMenuBar(window, configurer);
		}
	}

	private void fillMenuBar(IWorkbenchWindow window,
			IActionBarConfigurer configurer) {

		IMenuManager menuBar = configurer.getMenuManager();
		menuBar.add(createFileMenu(window));
		menuBar.add(createEditMenu(window));
		menuBar.add(createWindowMenu(window));
		menuBar.add(createHelpMenu(window));
	}

	private MenuManager createFileMenu(IWorkbenchWindow window) {
		MenuManager menu = new MenuManager(Messages.getString("menu.file"), //$NON-NLS-1$
				IWorkbenchActionConstants.M_FILE);
		menu.add(new GroupMarker(IWorkbenchActionConstants.FILE_START));
		menu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		menu.add(ActionFactory.QUIT.create(window));
		menu.add(new GroupMarker(IWorkbenchActionConstants.FILE_END));
		return menu;
	}

	private MenuManager createEditMenu(IWorkbenchWindow window) {
		MenuManager menu = new MenuManager(Messages.getString("menu.edit"), //$NON-NLS-1$
				IWorkbenchActionConstants.M_EDIT);
		menu.add(new GroupMarker(IWorkbenchActionConstants.EDIT_START));

		menu.add(ActionFactory.UNDO.create(window));
		menu.add(ActionFactory.REDO.create(window));
		menu.add(new GroupMarker(IWorkbenchActionConstants.UNDO_EXT));
		menu.add(new Separator());

		menu.add(ActionFactory.CUT.create(window));
		menu.add(ActionFactory.COPY.create(window));
		menu.add(ActionFactory.PASTE.create(window));
		menu.add(new GroupMarker(IWorkbenchActionConstants.CUT_EXT));
		menu.add(new Separator());

		menu.add(ActionFactory.DELETE.create(window));
		menu.add(ActionFactory.SELECT_ALL.create(window));
		menu.add(new Separator());

		menu.add(new GroupMarker(IWorkbenchActionConstants.ADD_EXT));

		menu.add(new GroupMarker(IWorkbenchActionConstants.EDIT_END));
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		return menu;
	}

	private MenuManager createWindowMenu(IWorkbenchWindow window) {
		MenuManager menu = new MenuManager(Messages.getString("menu.window"), //$NON-NLS-1$
				IWorkbenchActionConstants.M_WINDOW);

		menu.add(ActionFactory.OPEN_NEW_WINDOW.create(window));

		menu.add(new Separator());
		MenuManager perspectiveMenu = new MenuManager(Messages
				.getString("menu.open_perspective"), "openPerspective"); //$NON-NLS-1$ //$NON-NLS-2$
		IContributionItem perspectiveList = ContributionItemFactory.PERSPECTIVES_SHORTLIST
				.create(window);
		perspectiveMenu.add(perspectiveList);
		menu.add(perspectiveMenu);

		MenuManager viewMenu = new MenuManager(Messages
				.getString("menu.show_view")); //$NON-NLS-1$
		IContributionItem viewList = ContributionItemFactory.VIEWS_SHORTLIST
				.create(window);
		viewMenu.add(viewList);
		menu.add(viewMenu);

		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

		menu.add(ActionFactory.PREFERENCES.create(window));

		menu.add(ContributionItemFactory.OPEN_WINDOWS.create(window));

		return menu;
	}

	private MenuManager createHelpMenu(IWorkbenchWindow window) {
		MenuManager menu = new MenuManager(
				Messages.getString("menu.help"), IWorkbenchActionConstants.M_HELP); //$NON-NLS-1$
		// Welcome or intro page would go here
		menu.add(ActionFactory.HELP_CONTENTS.create(window));
		// Tips and tricks page would go here
		menu.add(new GroupMarker(IWorkbenchActionConstants.HELP_START));
		menu.add(new GroupMarker(IWorkbenchActionConstants.HELP_END));
		menu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		// About should always be at the bottom
		// To use the real RCP About dialog uncomment these lines
		menu.add(new Separator());
		menu.add(ActionFactory.ABOUT.create(window));

		return menu;
	}

	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		// TODO Auto-generated method stub
		LogPrint.note(this, "createWorkbenchWindowAdvisor");
		return super.createWorkbenchWindowAdvisor(configurer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.application.WorkbenchAdvisor#postStartup()
	 */
	public void postStartup() {
		// TODO Auto-generated method stub
		super.postStartup();

		// create a new console
		MessageConsole msgConsole = new MessageConsole(WEBCAM_RECORDER_CONSOLE,
				null);

		// register it with the console manager:
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(
				new IConsole[] { msgConsole });

		// store this message console with the plugin
		WebcamrecPlugin.getDefault().setMessageConsole(msgConsole);

		// There's also a convenience method for showing a console, opening the
		// console
		// view in the active window if necessary:
		// ConsolePlugin.getDefault().getConsoleManager().showConsoleView(myConsole);

	}

	/**
	 * Calculate the max size of the window based on the screen size
	 * @return
	 */
	private Point getDisplaySize() {
		try {
			Display display = Display.getCurrent();
			Monitor monitor = display.getPrimaryMonitor();
			Rectangle rect = monitor.getBounds();
			rect.x = 10;
			rect.y = 10;
			LogPrint.note(this, "getDisplaySize " + rect);
			return new Point((int) (rect.width * 0.8), (int) (rect.height * 0.6));
		} catch (Throwable ignore) {
			LogPrint.note(this, "getDisplaySize DEFAULT" + DEFAULT_SIZE);
			return DEFAULT_SIZE;
		}
	}
}
