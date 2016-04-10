package edu.gwu.csci6231.device.model;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.Observable;

import edu.gwu.csci6231.device.DeviceProvider;

public abstract class DataModel extends Observable{

	public static int CMD_REPAIR = 0x1;
	public static int CMD_CAMERA_UP = 0x11;
	public static int CMD_CAMERA_DOWN = 0x12;
	public static int CMD_CAMERA_LEFT = 0x13;
	public static int CMD_CAMERA_RIGHT = 0x14;
	
	protected double data = 0;
	protected String modelName;
	protected boolean shouldAlarm = true;
	protected boolean enableAlarm = true;
	
	protected DeviceProvider provider;
	
	protected double bestValue = 0;
	protected double minSafeValue = Double.MIN_VALUE;
	protected double maxSafeValue = Double.MAX_VALUE;
	/**
	 * every time generating a new value, it will randomly plus or minus vibeRate to current data
	 */
	protected double vibeRate = 0.01;
	
	protected double howManyPercentageToAdd = 0.5;
	
	protected Format fortmat = new DecimalFormat("0.00");
	protected String unit = "";
	
	
	
	/**
	 * check the alarming status
	 * @return
	 */
	public boolean isAlarming(){
		if(this.shouldAlarm && this.enableAlarm){
			if(this.data>this.maxSafeValue || this.data<this.minSafeValue)
				return true;
		}
		return false;
	}
	
	
	public boolean sendRobotToFix(){
		return provider.takeAction(modelName, DataModel.CMD_REPAIR);
	}
	
	public String getModelName(){
		return this.modelName;
	}
	
	public double getData(){
		return this.data;
	}
	
	public String getDataString() {
		return this.fortmat.format(this.data)+this.unit;
	}
	/**
	 * generate fake data
	 */
	public void generateFakeData(){
		if(this.isAlarming()){
			//if alarming, stop generating new data
			return;
		}
		if(Math.random()<this.howManyPercentageToAdd)
			this.data += this.vibeRate;
		else
			this.data -= this.vibeRate;
	}
	
	
	public void updateValue(){
//		System.out.println(newData);
		this.generateFakeData();
		this.setChanged();
		this.notifyObservers();
	}
	
	public void setToBestValue(){
		this.data = this.bestValue;
		this.setChanged();
		this.notifyObservers();
	}
	
//	protected 
}
