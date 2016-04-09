package edu.gwu.csci6231.system;

import edu.gwu.csci6231.device.DeviceProvider;
import edu.gwu.csci6231.device.model.DataModel;

public class MonitorSystem {

	
	protected DeviceProvider sensorProvider,cameraProvider;
	
	public void addModel(DataModel model){

		model.isAlarming();
	}
}
