package edu.gwu.csci6231;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import edu.gwu.csci6231.device.DeviceProvider;
import edu.gwu.csci6231.device.SensorProvider;
import edu.gwu.csci6231.device.model.DataModel;
import edu.gwu.csci6231.frame.Console;

public class MainFrame {

	
	public Display display;
	
	public DeviceProvider sensorProvider;
	

	public MainFrame(Display display) {

		Shell shell = new Shell(display);
		shell.setText("Mars Ice House 2");
		shell.setSize(400, 300);

		this.display = display;
		
		shell.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Console console = new Console(shell,SWT.NONE);
		
		sensorProvider = new SensorProvider();
		
		
		for(String name: sensorProvider.getDataModelNames()){
			DataModel model = sensorProvider.getDataModel(name);
			console.addDataModel(model);
		}
		
		
		
		shell.open();

		
		


		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		sensorProvider.destory();
		
		
	}
	
	
	public static void main(String args[]) {
		Display display = Display.getDefault();
		final MainFrame mainFrame = new MainFrame(display);
//		display.dispose();
		
	}
}
