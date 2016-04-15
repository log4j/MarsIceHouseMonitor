package edu.gwu.csci6231.system;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import edu.gwu.csci6231.device.CameraProvider;
import edu.gwu.csci6231.device.DeviceProvider;
import edu.gwu.csci6231.device.SensorProvider;
import edu.gwu.csci6231.device.model.DataModel;
import edu.gwu.csci6231.frame.*;

public class MonitorSystem {

	
	public Display display;
	
	public DeviceProvider sensorProvider,cameraProvider;
	
	protected Shell shell;
	
	public MonitorSystem(Display display) {

		shell = new Shell(display);
		shell.setText("Mars Ice House 2");
		shell.setSize(910, 470);

		this.display = display;
		
		shell.setLayout(new FillLayout());
		
		sensorProvider = new SensorProvider();
		
		
//		Console console = new ConsoleOperator(shell,SWT.NONE);
		Console console = new ConsoleTester(shell,SWT.NONE);
		
		console.setSensorProvider(sensorProvider);
//		Console consoleTester = new ConsoleTester(shell,SWT.NONE);
		
		
		
		
		for(String name: sensorProvider.getDataModelNames()){
			DataModel model = sensorProvider.getDataModel(name);
			console.addDataModel(model, Console.TARGET_INDICATOR);
//			consoleTester.addDataModel(model);
		}
		
		cameraProvider = new CameraProvider();
		for(String name: cameraProvider.getDataModelNames()){
			DataModel model = cameraProvider.getDataModel(name);
			console.addDataModel(model, Console.TARGET_CAMERA);
//			consoleTester.addDataModel(model);
		}
		
		
		
		
        
        
        
		shell.open();

		
		


		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		sensorProvider.destory();
		cameraProvider.destory();
		
		
	}
	
	
	public static void main(String args[]) {
		Display display = Display.getDefault();
		new MonitorSystem(display);
//		display.dispose();
		
	}
}
