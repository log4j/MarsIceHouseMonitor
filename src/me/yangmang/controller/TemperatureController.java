package me.yangmang.controller;

import java.text.DecimalFormat;

public class TemperatureController extends AbstractController{


	protected Thread sensor;

	private DecimalFormat format = new DecimalFormat("0.00");

	public TemperatureController() {
		this.unit = "F";

	}

	private void initThread() {
		sensor = new Thread() {

			public void run() {
				while (isEnable) {
					
					if(!hasAlarm){
						double value = (Math.random()-0.5) * 5 + 70;
						data = value;
						
						if(value>72){
							hasAlarm = true;
						}
					}
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// System.out.println(data);
				}
			}

		};
	}

	protected boolean startSensor() {
		if (sensor != null && sensor.isAlive())
			return false;
		initThread();
		sensor.start();
		return true;
	}

	@Override
	public boolean enable() {
		this.isEnable = true;
		return startSensor();
	}

	@Override
	public boolean disable() {
		this.isEnable = false;
		this.hasAlarm = false;
		return true;
	}

	@Override
	public String getDataString() {
		return String.valueOf(this.data);
	}

	@Override
	public String getUnit() {
		return unit;
	}

	public String toString() {
		if (isEnable) {
			return format.format(this.data) + " " + this.unit;
		} else {
			return "NA";
		}
	}

	@Override
	public boolean fix() {
		this.data = 70;
		this.hasAlarm = false;
		return true;
	}

}
