package edu.gwu.csci6231.device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.gwu.csci6231.device.model.DataModel;
import edu.gwu.csci6231.device.model.DataModelHumidity;
import edu.gwu.csci6231.device.model.DataModelTemperature;

public class SensorProvider extends DeviceProvider implements Runnable{

	protected boolean running = true;
	
	
	public SensorProvider(){
		
		models = new HashMap<String,DataModel>();
		
		for(DataModel model : generateModel()){
			models.put(model.getModelName(), model);
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
		
		list.add(new DataModelTemperature("Outer Temperature", this));
		
		list.add(new DataModelTemperature("Inner Temperature", this));
		
		list.add(new DataModelHumidity("Humidity", this));
		
		
		
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
			System.out.println("Randoming");
			
			for(DataModel model: models.values()){
				model.updateValue();
				
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
