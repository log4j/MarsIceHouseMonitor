package edu.gwu.csci6231.device;

import java.util.List;
import java.util.Map;

import edu.gwu.csci6231.device.model.DataModel;

public abstract class DeviceProvider {
	
	public static int REFRESH_RATE = 200;

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

	public abstract boolean takeAction (String modelName, int cmd);
	
	public abstract boolean destory ();
}
