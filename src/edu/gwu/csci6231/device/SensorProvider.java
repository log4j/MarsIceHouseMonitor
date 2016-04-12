package edu.gwu.csci6231.device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.gwu.csci6231.device.model.DataModel;
import edu.gwu.csci6231.device.model.DataModelHumidity;
import edu.gwu.csci6231.device.model.DataModelOxygen;
import edu.gwu.csci6231.device.model.DataModelTemperature;

public class SensorProvider extends DeviceProvider implements Runnable{

	protected boolean running = true;
	
	
	public SensorProvider(){
		
		models = new HashMap<String,DataModel>();
		orderByName = new ArrayList<String>();
		
		for(DataModel model : generateModel()){
			models.put(model.getModelName(), model);
			orderByName.add(model.getModelName());
		}
		
		//start running
		new Thread(this).start();
	}
	
	

	

	
	
	
	
	/**
	 * mock function: generate all DataModel(s)
	 * @return
	 */
	protected List<DataModel> generateModel(){
		List<DataModel> list = new ArrayList<DataModel>();
		
		
		DataModel outTemperature = new DataModelTemperature("Outer Temperature", this);
		outTemperature.setShouldAlarm(false);
		outTemperature.setMaxSafeValue(200);
		outTemperature.setHowManyPercentageToAdd(0.5);
		outTemperature.setVibeRate(2.42);
		list.add(outTemperature);
		
		
		
		list.add(new DataModelTemperature("Inner Temperature", this));
		
		list.add(new DataModelHumidity("Humidity", this));
		
		list.add(new DataModelOxygen("Oxygen", this));
		
		return list;
	}
	
	


	@Override
	public boolean takeAction(String modelName, int cmd) {
		DataModel model = this.models.get(modelName);
		if(model!=null){
			model.setToBestValue();
			
			System.out.println(model.getData());
		}
		return false;
	}









	@Override
	public boolean destory() {
		this.running = false;
		return false;
	}









	@Override
	public void run() {
		
		while(this.running){
			
			for(DataModel model: models.values()){
				model.updateValue();
			}
			
			try {
				Thread.sleep(DeviceProvider.REFRESH_RATE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
