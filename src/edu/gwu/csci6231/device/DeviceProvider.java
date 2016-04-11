package edu.gwu.csci6231.device;

import java.util.Map;
import java.util.Set;

import edu.gwu.csci6231.device.model.DataModel;

public abstract class DeviceProvider {
	
	public static int REFRESH_RATE = 200;

	protected Map<String,DataModel> models;
	
	public Set<String> getDataModelNames(){
		return models.keySet();
	}
	
	public DataModel getDataModel(String modelName){
		if(models!=null)
			return models.get(modelName);
		return null;
	}

	public abstract boolean takeAction (String modelName, int cmd);
	
	public abstract boolean destory ();
}
