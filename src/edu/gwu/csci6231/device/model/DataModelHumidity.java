package edu.gwu.csci6231.device.model;

import edu.gwu.csci6231.device.DeviceProvider;

public class DataModelHumidity extends DataModel{

	public DataModelHumidity(String modelName, DeviceProvider provider){
		this.modelName = modelName;
		this.unit = "%";
		this.bestValue = 72;
		this.data = this.bestValue;
		this.minSafeValue = 35;
		this.maxSafeValue = 80;
		this.vibeRate = 0.6;
		this.howManyPercentageToAdd = 0.7;
		
		this.provider = provider;
	}

}
