package edu.gwu.csci6231.frame;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
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

	public static int INDICATOR_WIDTH = 150;
	public static int LINE_HEIGHT = 30;
	public static int NUMBER_WIDTH = 70;
	public static int BUTTON_WIDTH = 70;
	
	private DeviceProvider provider;
	private Shell shell;
	private SimulatorEvent event;
	
	private List<Combo> indicators;
	private List<Text> values;
	private List<Text> times;
	private List<Button> removeBtns;
	
	private Label warning;
	
	private Button addEventItemBtn,runSimulatorBtn;
	
	private Composite tables;
	
	protected Runnable runnable = null;

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
		
		shell.setLayout(null);
		shell.setSize(410, 400);
//		shell.setBounds(10, 10, 400, 500);
		
		tables = new Composite(shell, SWT.NONE);
		tables.setLayout(null);
		tables.setBounds(5, 5, 400, 200);
//		Composite labels = new Composite(shell, SWT.HORIZONTAL);
//		labels.setLayout(new GridLayout(4, true));
		
		Label titleIndicator = new Label(tables, SWT.HORIZONTAL | SWT.SHADOW_OUT);
		titleIndicator.setText("Indicator");
		titleIndicator.setBounds(0, 0, INDICATOR_WIDTH, LINE_HEIGHT);
		
		Label titleSetValue = new Label(tables, SWT.HORIZONTAL | SWT.SHADOW_OUT);
		titleSetValue.setText("Set Value");
		titleSetValue.setBounds(INDICATOR_WIDTH+5, 0, NUMBER_WIDTH, LINE_HEIGHT);
		
		Label titleWhen = new Label(tables, SWT.HORIZONTAL | SWT.SHADOW_OUT);
		titleWhen.setText("When");
		titleWhen.setBounds(INDICATOR_WIDTH+NUMBER_WIDTH+10, 0, NUMBER_WIDTH, LINE_HEIGHT);
		
		Label titleRemove = new Label(tables, SWT.HORIZONTAL | SWT.SHADOW_OUT);
		titleRemove.setText("  ");
 
		
		warning = new Label(tables, SWT.NONE);
		warning.setForeground(FrameUtil.COLOR_RED);
		warning.setText("Form not valid!");
		warning.setVisible(false);
		
		for(SimulatorEvent.EventInfo eventInfo:event.getEvents()){
			this.addOneRecord(eventInfo);
		}
		
		addEventItemBtn = new Button(tables,SWT.NONE);
		addEventItemBtn.setText("Insert Event");
		addEventItemBtn.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseUp(MouseEvent e) {
				event.addEvent(SimulatorEvent.EventInfo.create());
				addOneRecord(event.getLastEvent());
				tables.redraw();
				repackLocation();
				shell.pack();
				System.out.println("Insert Event");
			}
		});
		
		runSimulatorBtn = new Button(tables,SWT.NONE);
		runSimulatorBtn.setText("Run!!!!!");
		runSimulatorBtn.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseUp(MouseEvent e) {
				SimulatorEvent event = getEvent();
				if(event==null){
					warning.setVisible(true);
				}else{
					warning.setVisible(false);
//					((SensorProvider)provider).runSimulator(getEvent());
					runSimulator();
				}
				
			}
		});
		
		repackLocation();
	}
	
	private void repackLocation(){
		for(int i=0;i<indicators.size();i++){
			indicators.get(i).setBounds(0, (LINE_HEIGHT+5)*(i+1), INDICATOR_WIDTH, LINE_HEIGHT);
			values.get(i).setBounds(INDICATOR_WIDTH+5, (LINE_HEIGHT+5)*(i+1), NUMBER_WIDTH, LINE_HEIGHT-5);
			times.get(i).setBounds(INDICATOR_WIDTH+NUMBER_WIDTH+10, (LINE_HEIGHT+5)*(i+1), NUMBER_WIDTH, LINE_HEIGHT-5);
			removeBtns.get(i).setBounds(INDICATOR_WIDTH+NUMBER_WIDTH*2+15, (LINE_HEIGHT+5)*(i+1), BUTTON_WIDTH, LINE_HEIGHT-5);
		}
		//tables.setBounds(5, 5, 400, (LINE_HEIGHT+5)*(indicators.size()+1));
		addEventItemBtn.setBounds(INDICATOR_WIDTH+NUMBER_WIDTH*2, (LINE_HEIGHT+5)*(indicators.size()+1), BUTTON_WIDTH+15, LINE_HEIGHT-5);
		warning.setBounds(0, (LINE_HEIGHT+5)*(indicators.size()+1), INDICATOR_WIDTH, LINE_HEIGHT-5);
		
		runSimulatorBtn.setBounds(0, (LINE_HEIGHT+5)*(indicators.size()+2), INDICATOR_WIDTH+NUMBER_WIDTH*2+BUTTON_WIDTH+15, LINE_HEIGHT-5);
		
		tables.setBounds(10, 10, INDICATOR_WIDTH+NUMBER_WIDTH*3+25, (LINE_HEIGHT+5)*(indicators.size()+3));
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
					repackLocation();
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
			if(indicators.get(i).getText().equals("")){
				return null;
			}
			double value = 0;
			try {
				value = Double.parseDouble(values.get(i).getText());
			} catch (NumberFormatException e) {
//				e.printStackTrace();
				return null;
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
	
	protected void runSimulator(){
		this.runnable = new Runnable() {
			public void run() {
				// System.out.println("haha");
				getParent().getDisplay().timerExec(1000, this);
//				colorTimming = (colorTimming + 1) % 50;
//				alarmCanvas.redraw();
				
				System.out.println("EEE");
			}
		};
		getParent().getDisplay().timerExec(10, runnable);
	}
}
