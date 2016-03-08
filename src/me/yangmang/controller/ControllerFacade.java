package me.yangmang.controller;

import java.util.HashMap;
import java.util.Map;



public class ControllerFacade {
	private HumidityController humidityController;
	private OxygenController oxygenController;
	private PressureController pressureController;
	private TemperatureController temperatureController;
	private Map<String,DataModel> dataMap;
	private Map<String,AbstractController> ctrlMap;
	
	
	
	
	public ControllerFacade(){
		
		dataMap = new HashMap<String,DataModel>();
		ctrlMap = new HashMap<String, AbstractController> ();
		
		humidityController = new HumidityController();
		oxygenController = new OxygenController();
		pressureController = new PressureController();
		temperatureController = new TemperatureController();
		
		dataMap.put("humidity", new DataModel());
		dataMap.put("oxygen", new DataModel());
		dataMap.put("temperature", new DataModel());
		dataMap.put("pressure", new DataModel());
		
		ctrlMap.put("humidity", humidityController);
		ctrlMap.put("oxygen", oxygenController);
		ctrlMap.put("temperature", temperatureController);
		ctrlMap.put("pressure", pressureController);
		
	}
	
	public void enableAllMonitor(){
		humidityController.enable();
		oxygenController.enable();
		pressureController.enable();
		temperatureController.enable();
	}
	
	public void disableAllMonitor(){
		humidityController.disable();
		oxygenController.disable();
		pressureController.disable();
		temperatureController.disable();
	}
	
	
	
	public void enableMonitors(String... arg){
		if(arg!=null){
			for(String key : arg){
				AbstractController ctrl = this.ctrlMap.get(key);
				ctrl.enable();
			}
		}
	}
	
	public void fixAllMonitor(){
		humidityController.fix();
		oxygenController.fix();
		pressureController.fix();
		temperatureController.fix();
	}
	
	public Map<String,DataModel> getMonitorData(){
		
		for(String key:dataMap.keySet()){
			AbstractController ctrl = this.ctrlMap.get(key);
			if(ctrl!=null){
				DataModel model = dataMap.get(key);
				model.value = ctrl.toString();
				model.isEnable = ctrl.isEnable;
				model.hasAlarm = ctrl.hasAlarm;
			}
		}
		
		
		return this.dataMap;
	}
	
	
	
}
