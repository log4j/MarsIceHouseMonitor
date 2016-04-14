package edu.gwu.csci6231.frame;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import edu.gwu.csci6231.database.SimulatorEvent;
import edu.gwu.csci6231.device.DeviceProvider;
import edu.gwu.csci6231.device.SensorProvider;

public class DebugEventDialog extends Dialog {

	
	private DeviceProvider provider;
	private Shell shell;
	private SimulatorEvent event;
	
	private List<Combo> indicators;
	private List<Text> values;
	private List<Text> times;
	private List<Button> removeBtns;
	
	private Button addEventItemBtn,runSimulatorBtn;
	
	private Composite tables;

	public DebugEventDialog(Shell parent, int mode, DeviceProvider provider) {
		super(parent, mode);
		this.provider = provider;

		event = new SimulatorEvent();
		event.addEvent();
		event.addEvent();
		
		indicators = new ArrayList<Combo>();
		values = new ArrayList<Text>();
		times = new ArrayList<Text>();
		removeBtns = new ArrayList<Button>();
		
		
	}

	
	public void open() {
		shell = new Shell(getParent());
		shell.setText("Add Employee");
		draw(); // Contents of Dialog
		shell.pack();
		shell.open();
 
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
 
	}
 
	private void draw() {
 
		if(shell==null)
			return;
		
		shell.setLayout(new GridLayout(1, true));
		
		tables = new Composite(shell, SWT.HORIZONTAL);
		tables.setLayout(new GridLayout(4, true));
		
//		Composite labels = new Composite(shell, SWT.HORIZONTAL);
//		labels.setLayout(new GridLayout(4, true));
		
		Label titleIndicator = new Label(tables, SWT.HORIZONTAL | SWT.SHADOW_OUT);
		titleIndicator.setText("Indicator");
		
		Label titleSetValue = new Label(tables, SWT.HORIZONTAL | SWT.SHADOW_OUT);
		titleSetValue.setText("Set Value");
		
		Label titleWhen = new Label(tables, SWT.HORIZONTAL | SWT.SHADOW_OUT);
		titleWhen.setText("When");
		
		Label titleRemove = new Label(tables, SWT.HORIZONTAL | SWT.SHADOW_OUT);
		titleRemove.setText("  ");
 
		
		
		for(SimulatorEvent.EventInfo eventInfo:event.getEvents()){
			this.addOneRecord(eventInfo);
		}
		
		addEventItemBtn = new Button(shell,SWT.NONE);
		addEventItemBtn.setText("Insert Event");
		addEventItemBtn.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseUp(MouseEvent e) {
				event.addEvent(SimulatorEvent.EventInfo.create());
				addOneRecord(event.getLastEvent());
				tables.redraw();
				shell.pack();
				System.out.println("Insert Event");
			}
		});
		
		runSimulatorBtn = new Button(shell,SWT.NONE);
		runSimulatorBtn.setText("Run!!!!!");
		runSimulatorBtn.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseUp(MouseEvent e) {
				((SensorProvider)provider).runSimulator(getEvent());
			}
		});
	}

	private void addOneRecord(SimulatorEvent.EventInfo eventInfo){
		Combo targets = new Combo(tables, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		for(String key : provider.getDataModelNames()){
			targets.add(key);
		}
		indicators.add(targets);
		
		Text value = new Text(tables, SWT.SINGLE | SWT.BORDER);
		values.add(value);
		
		Text time = new Text(tables, SWT.SINGLE | SWT.BORDER);
		times.add(time);
		
		Button removeBtn = new Button(tables, SWT.NONE);
		removeBtn.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseUp(MouseEvent e) {
				int index = removeBtns.indexOf(e.getSource());
				if(index>=0){
					indicators.remove(index).dispose();
					values.remove(index).dispose();
					times.remove(index).dispose();
					removeBtns.remove(index).dispose();
					shell.pack();
				}
			}
		});
		if(removeBtns.size()==0)
			removeBtn.setEnabled(false);
		removeBtn.setText("Remove");
		removeBtns.add(removeBtn);
		
	}
	
	public SimulatorEvent getEvent(){
		SimulatorEvent event = new SimulatorEvent();
		for(int i=0;i<indicators.size();i++){
			double value = 0;
			try {
				value = Double.parseDouble(values.get(i).getText());
			} catch (NumberFormatException e) {
//				e.printStackTrace();
			}
			int time = 0;
			try {
				time = Integer.parseInt(times.get(i).getText());
			} catch (NumberFormatException e) {
//				e.printStackTrace();
			}
			
			event.addEvent(new SimulatorEvent.EventInfo(
					indicators.get(i).getText(), 
					value,
					time));
		}
		
		return event;
	}
}
