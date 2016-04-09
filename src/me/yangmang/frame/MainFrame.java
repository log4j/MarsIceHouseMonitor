package me.yangmang.frame;

import java.util.Map;

import me.yangmang.controller.ControllerFacade;
import me.yangmang.controller.DataModel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class MainFrame {

	// private Panel actionPanel;

	private Label temperatureLabel;
	private Label temperatureStatusLabel;
	private Label temperatureDataLabel;
	private Label temperatureAlarmLabel;
	
	private Label oxygenLabel;
	private Label oxygenStatusLabel;
	private Label oxygenDataLabel;
	private Label oxygenAlarmLabel;
	
	private Label pressureLabel;
	private Label pressureStatusLabel;
	private Label pressureDataLabel;
	private Label pressureAlarmLabel;
	
	private Label humidityLabel;
	private Label humidityStatusLabel;
	private Label humidityDataLabel;
	private Label humidityAlarmLabel;
	
	
	

	private Button enableAllButton, disableAllButton, clearAlarmButton,enableTemperatureAndHumidity;

	private ControllerFacade facade;
	private UpdateHelper helper;

	public Display display;
	
	private Color normalColor,warningColor,disableColor;
	

	public MainFrame(Display display) {

		Shell shell = new Shell(display);
		shell.setText("Mars Ice House");
		shell.setSize(400, 300);

		this.display = display;
		// centerWindow(shell);
		
		normalColor = new Color(display, 12, 212, 12);
		warningColor = new Color(display, 212, 12, 12);
		disableColor = new Color(display,212, 212, 212);

		
		temperatureLabel = new Label(shell, SWT.PUSH);
		temperatureLabel.setText("Temperature");
		temperatureLabel.setBounds(20, 20, 100, 20);

		temperatureStatusLabel = new Label(shell, SWT.PUSH);
		temperatureStatusLabel.setText("NA");
		temperatureStatusLabel.setForeground(disableColor);
		temperatureStatusLabel.setBounds(125, 20, 40, 20);

		temperatureDataLabel = new Label(shell, SWT.PUSH);
		temperatureDataLabel.setText("NA");
		temperatureDataLabel.setBounds(30, 40, 90, 20);
		
		temperatureAlarmLabel = new Label(shell, SWT.PUSH);
		temperatureAlarmLabel.setText("!!ALARM!!");
		temperatureAlarmLabel.setForeground(warningColor);
		temperatureAlarmLabel.setBounds(120, 40, 70, 20);
		temperatureAlarmLabel.setVisible(false);
		
		
		
		
		oxygenLabel = new Label(shell, SWT.PUSH);
		oxygenLabel.setText("Oxygen");
		oxygenLabel.setBounds(20, 70, 100, 20);

		oxygenStatusLabel = new Label(shell, SWT.PUSH);
		oxygenStatusLabel.setText("NA");
		oxygenStatusLabel.setForeground(disableColor);
		oxygenStatusLabel.setBounds(125, 70, 40, 20);

		oxygenDataLabel = new Label(shell, SWT.PUSH);
		oxygenDataLabel.setText("NA");
		oxygenDataLabel.setBounds(30, 90, 90, 20);
		
		oxygenAlarmLabel = new Label(shell, SWT.PUSH);
		oxygenAlarmLabel.setText("!!ALARM!!");
		oxygenAlarmLabel.setForeground(warningColor);
		oxygenAlarmLabel.setBounds(120, 90, 70, 20);
		oxygenAlarmLabel.setVisible(false);

		
		humidityLabel = new Label(shell, SWT.PUSH);
		humidityLabel.setText("Humidity");
		humidityLabel.setBounds(20, 120, 100, 20);

		humidityStatusLabel = new Label(shell, SWT.PUSH);
		humidityStatusLabel.setText("NA");
		humidityStatusLabel.setForeground(disableColor);
		humidityStatusLabel.setBounds(125, 120, 40, 20);

		humidityDataLabel = new Label(shell, SWT.PUSH);
		humidityDataLabel.setText("NA");
		humidityDataLabel.setBounds(30, 140, 90, 20);
		
		humidityAlarmLabel = new Label(shell, SWT.PUSH);
		humidityAlarmLabel.setText("!!ALARM!!");
		humidityAlarmLabel.setForeground(warningColor);
		humidityAlarmLabel.setBounds(120, 140, 70, 20);
		humidityAlarmLabel.setVisible(false);
		
		
		
		pressureLabel = new Label(shell, SWT.PUSH);
		pressureLabel.setText("Pressure");
		pressureLabel.setBounds(20, 170, 100, 20);

		pressureStatusLabel = new Label(shell, SWT.PUSH);
		pressureStatusLabel.setText("NA");
		pressureStatusLabel.setForeground(disableColor);
		pressureStatusLabel.setBounds(125, 170, 40, 20);

		pressureDataLabel = new Label(shell, SWT.PUSH);
		pressureDataLabel.setText("NA");
		pressureDataLabel.setBounds(30, 190, 90, 20);
		
		pressureAlarmLabel = new Label(shell, SWT.PUSH);
		pressureAlarmLabel.setText("!!ALARM!!");
		pressureAlarmLabel.setForeground(warningColor);
		pressureAlarmLabel.setBounds(120, 190, 70, 20);
		pressureAlarmLabel.setVisible(false);
		
		
		enableAllButton = new Button(shell, SWT.PUSH);
		enableAllButton.setText("Enable All");
		enableAllButton.setBounds(200, 20, 120, 30);
		
		enableAllButton.addMouseListener(
				new MouseAdapter(){
					@Override
					public void mouseUp(MouseEvent arg0) {
						facade.enableAllMonitor();
					}
				});
		
		
		
		

		disableAllButton = new Button(shell, SWT.PUSH);
		disableAllButton.setText("Disable All");
		disableAllButton.setBounds(200, 60, 120, 30);
		
		disableAllButton.addMouseListener(
				new MouseAdapter(){
					@Override
					public void mouseUp(MouseEvent arg0) {
						facade.disableAllMonitor();
					}
				});

		clearAlarmButton = new Button(shell, SWT.PUSH);
		clearAlarmButton.setText("Fix All");
		clearAlarmButton.setBounds(200, 100, 120, 30);

		clearAlarmButton.addMouseListener(
				new MouseAdapter(){
					@Override
					public void mouseUp(MouseEvent e) {
						facade.fixAllMonitor();
					}
				});

		enableTemperatureAndHumidity = new Button(shell, SWT.PUSH);
		enableTemperatureAndHumidity.setText("Enable Temp Humidity");
		enableTemperatureAndHumidity.setBounds(200, 140, 160, 30);

		enableTemperatureAndHumidity.addMouseListener(
				new MouseAdapter(){
					@Override
					public void mouseUp(MouseEvent e) {
						facade.enableMonitors("temperature","humidity");
					}
				});
		

		facade = new ControllerFacade();
		helper = new UpdateHelper(this);

		 //this.updateData();
		shell.open();

		
		
		

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

//		display.asyncExec(new Runnable() {
//			@Override
//			public void run() {
//				while (true) {
//					updateData();
//					try {
//						Thread.sleep(200);
//
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//
//			}
//		});

	}

	
	
	public void updateSensorData(){
		if (facade == null)
			return;
		Map<String, DataModel> dataMap = facade.getMonitorData();

		DataModel temperature = dataMap.get("temperature");
		if (temperature != null) {
			temperatureStatusLabel.setText(temperature.isEnable ? "ON" : "OFF");
			temperatureStatusLabel.setForeground(temperature.isEnable ? normalColor:disableColor);
			temperatureDataLabel.setText(temperature.value);
			if(temperature.hasAlarm){
				temperatureStatusLabel.setForeground(warningColor);
			}
			temperatureAlarmLabel.setVisible(temperature.hasAlarm);
		}
		
		DataModel oxygen = dataMap.get("oxygen");
		if (oxygen != null) {
			oxygenStatusLabel.setText(oxygen.isEnable ? "ON" : "OFF");
			oxygenStatusLabel.setForeground(oxygen.isEnable ? normalColor:disableColor);
			oxygenDataLabel.setText(oxygen.value);
			if(oxygen.hasAlarm){
				oxygenStatusLabel.setForeground(warningColor);
			}
			oxygenAlarmLabel.setVisible(oxygen.hasAlarm);
		}
		
		DataModel humidity = dataMap.get("humidity");
		if (humidity != null) {
			humidityStatusLabel.setText(humidity.isEnable ? "ON" : "OFF");
			humidityStatusLabel.setForeground(humidity.isEnable ? normalColor:disableColor);
			humidityDataLabel.setText(humidity.value);
			if(humidity.hasAlarm){
				humidityStatusLabel.setForeground(warningColor);
			}
			humidityAlarmLabel.setVisible(humidity.hasAlarm);
		}
		
		DataModel pressure = dataMap.get("pressure");
		if (pressure != null) {
			pressureStatusLabel.setText(pressure.isEnable ? "ON" : "OFF");
			pressureStatusLabel.setForeground(pressure.isEnable ? normalColor:disableColor);
			pressureDataLabel.setText(pressure.value);
			if(pressure.hasAlarm){
				pressureStatusLabel.setForeground(warningColor);
			}
			pressureAlarmLabel.setVisible(pressure.hasAlarm);
		}
		
		
		
	}

	@SuppressWarnings("unused")
	private void centerWindow(Shell shell) {

		Rectangle bds = shell.getDisplay().getBounds();
		Point p = shell.getSize();
		int nLeft = (bds.width - p.x) / 2;
		int nTop = (bds.height - p.y) / 2;
		shell.setBounds(nLeft, nTop, p.x, p.y);
	}

	public static void main(String args[]) {
		Display display = Display.getDefault();
		final MainFrame mainFrame = new MainFrame(display);
		mainFrame.facade.disableAllMonitor();
		mainFrame.helper.end();
		display.dispose();
		
		
		
	}
}
