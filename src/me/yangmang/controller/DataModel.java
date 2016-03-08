package me.yangmang.controller;

public class DataModel {

	public String value;
	public boolean isEnable;
	public boolean hasAlarm;
	
	public DataModel (String value, boolean isEnable,boolean hasAlarm){
		this.value = value;
		this.isEnable = isEnable;
		this.hasAlarm = hasAlarm;
	}
	
	public DataModel(){
		this("",false,false);
	}
}
