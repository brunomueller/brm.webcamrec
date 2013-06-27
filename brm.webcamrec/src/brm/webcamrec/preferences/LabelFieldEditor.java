package brm.webcamrec.preferences;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * @author David
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
class LabelFieldEditor extends FieldEditor {

	private Label fTitleLabel;
	private Label fValueLabel;
	private Composite fBasicComposite;
	private String fValue;
	private String fTitle;

	public LabelFieldEditor(Composite parent, String title, String value) {
		fValue = value;
		fTitle = title;
		this.createControl(parent);
	}

	protected void adjustForNumColumns(int numColumns) {
		((GridData) fBasicComposite.getLayoutData()).horizontalSpan =
			numColumns;
	}

	protected void doFillIntoGrid(Composite parent, int numColumns) {
		fBasicComposite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 2;
		fBasicComposite.setLayout(layout);
		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		fBasicComposite.setLayoutData(data);

		fTitleLabel = new Label(fBasicComposite, SWT.NONE);
		fTitleLabel.setText(fTitle);
		GridData gd = new GridData();
		gd.verticalAlignment = SWT.TOP;
		fTitleLabel.setLayoutData(gd);

		fValueLabel = new Label(fBasicComposite, SWT.WRAP);
		fValueLabel.setText(fValue);
		gd = new GridData();
		fValueLabel.setLayoutData(gd);
	}

	public int getNumberOfControls() {
		return 1;
	}

	/**
	 * The label field editor is only used to present a text label
	 * on a preference page.
	 */
	protected void doLoad() {
	}
	protected void doLoadDefault() {
	}
	protected void doStore() {
	}
	
	/**
	 * Stores this field editor's value back into the preference store.
	 */
	public void store() {
	}
}