package edu.gwu.csci6231.frame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class DebugEventDialog extends Dialog {

	private String message = "";
	private String input = "";

	public DebugEventDialog(Shell parent, int mode) {
		super(parent, mode);
		// TODO Auto-generated constructor stub
		this.setText("DDD");
//	    setMessage("Please enter a value:");
	}

	private Group insGroup;
	 
	public void open() {
		Shell shell = new Shell(getParent());
		shell.setText("Add Employee");
 
		draw(shell); // Contents of Dialog
		shell.pack();
		shell.open();
 
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
 
	}
 
	private void draw(Shell shell) {
 
		shell.setLayout(new GridLayout(2, true));
 
		// ID
 
		Label lid = new Label(shell, SWT.HORIZONTAL | SWT.SHADOW_OUT);
		lid.setText("Employee ID");
		Text tid = new Text(shell, SWT.BORDER | SWT.LEFT);
 
		// Age
 
		Label lage = new Label(shell, SWT.HORIZONTAL | SWT.SHADOW_OUT);
		lage.setText("Age");
		Text tage = new Text(shell, SWT.BORDER | SWT.LEFT);
 
		// Salary
 
		Label lsalary = new Label(shell, SWT.HORIZONTAL | SWT.SHADOW_OUT);
		lsalary.setText("Salary");
		Text tsalary = new Text(shell, SWT.BORDER | SWT.LEFT);
 
		final Button checkButton = new Button(shell, SWT.CHECK);
		checkButton.setText("Is Insured ?");
		checkButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
 
				if (checkButton.getSelection()) {
					insGroup.setVisible(true);
 
				} else {
					insGroup.setVisible(false);
				}
			}
 
		});
 
		// Insurance Group
 
		insGroup = new Group(shell, SWT.SHADOW_IN);
		insGroup.setText("Insurance Details");
		insGroup.setLayout(new GridLayout(1, true));
		Label lb = new Label(insGroup, SWT.HORIZONTAL | SWT.SHADOW_OUT);
		lb.setText("Description");
		Text details = new Text(insGroup, SWT.BORDER | SWT.MULTI);
		insGroup.setVisible(false);
 
	}
}
