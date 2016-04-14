package edu.gwu.csci6231.device;

import java.util.List;
import java.util.Map;

import edu.gwu.csci6231.device.model.DataModel;

public abstract class DeviceProvider {
	
	public static int REFRESH_RATE = 1000;
	public static int CAMERA_REFRESH_RATE = 100;

	protected Map<String,DataModel> models;
	protected List<String> orderByName;
	
	public List<String> getDataModelNames(){
		return orderByName;
	}
	
	public DataModel getDataModel(String modelName){
		if(models!=null)
			return models.get(modelName);
		return null;
	}

	public abstract boolean takeAction (String modelName, int cmd,String...paras);
	
	public abstract boolean destory ();

	public DataModel getLastModel() {
		if(orderByName!=null && orderByName.size()>0){
			return this.models.get(this.orderByName.get(this.orderByName.size()-1));
		}
		return null;
	}
	
}
