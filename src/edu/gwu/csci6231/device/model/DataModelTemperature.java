package edu.gwu.csci6231.device.model;

public class DataModelTemperature extends DataModel {

	public DataModelTemperature(String modelName){
		
		this.modelName = modelName;
		this.unit = "F";
		this.bestValue = 72;
		this.data = this.bestValue;
		this.minSafeValue = 35;
		this.maxSafeValue = 90;
		this.vibeRate = 0.6;
		this.howManyPercentageToAdd = 0.7;
	}


}
