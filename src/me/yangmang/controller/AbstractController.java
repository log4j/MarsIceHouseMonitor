package me.yangmang.controller;

public abstract class AbstractController {

	protected boolean isEnable;
	protected Object data;
	protected String unit;
	protected boolean hasAlarm;
	
	public abstract boolean enable();
	
	public abstract boolean disable();
	
	public abstract boolean fix();
	
	public abstract String getDataString();
	
	public abstract String getUnit();
}
