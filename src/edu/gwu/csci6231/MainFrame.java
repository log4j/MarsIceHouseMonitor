package edu.gwu.csci6231;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import edu.gwu.csci6231.device.CameraProvider;
import edu.gwu.csci6231.device.DeviceProvider;
import edu.gwu.csci6231.device.SensorProvider;
import edu.gwu.csci6231.device.model.DataModel;
import edu.gwu.csci6231.frame.Console;
import edu.gwu.csci6231.frame.ConsoleOperator;
import edu.gwu.csci6231.frame.DebugEventDialog;

public class MainFrame {

	
	public Display display;
	
	public DeviceProvider sensorProvider,cameraProvider;
	
	protected Shell shell;
	
	public MainFrame(Display display) {

		shell = new Shell(display);
		shell.setText("Mars Ice House 2");
		shell.setSize(910, 470);

		this.display = display;
		
		shell.setLayout(new FillLayout());
		
		Console consoleOperator = new ConsoleOperator(shell,SWT.NONE);
//		Console consoleTester = new ConsoleTester(shell,SWT.NONE);
		
		sensorProvider = new SensorProvider();
		
		
		for(String name: sensorProvider.getDataModelNames()){
			DataModel model = sensorProvider.getDataModel(name);
			consoleOperator.addDataModel(model, Console.TARGET_INDICATOR);
//			consoleTester.addDataModel(model);
		}
		
		cameraProvider = new CameraProvider();
		for(String name: cameraProvider.getDataModelNames()){
			DataModel model = cameraProvider.getDataModel(name);
			consoleOperator.addDataModel(model, Console.TARGET_CAMERA);
//			consoleTester.addDataModel(model);
		}
		
		
		
		Menu menuBar = new Menu(shell, SWT.BAR);
        MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
        cascadeFileMenu.setText("&Tools");
        
        Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
        cascadeFileMenu.setMenu(fileMenu);

        MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
        exitItem.setText("&Debug");
        shell.setMenuBar(menuBar);
        
        
        exitItem.addListener(SWT.Selection, new Listener(){

			@Override
			public void handleEvent(Event e) {
				System.out.println("hahah");
				
				DebugEventDialog debugger = new DebugEventDialog(shell,SWT.None);
				debugger.open();
			}
        	
        });
        
        
        
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
		new MainFrame(display);
//		display.dispose();
		
	}
}
