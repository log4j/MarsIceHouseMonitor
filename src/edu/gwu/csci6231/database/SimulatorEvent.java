package edu.gwu.csci6231.database;

import java.util.ArrayList;
import java.util.List;

public class SimulatorEvent {
	

	protected List<EventInfo> events;
	
	public SimulatorEvent(){
		events = new ArrayList<EventInfo>();
	}
	
	public void addEvent(){
		events.add(new EventInfo("",0,0));
	}
	
//	public void updateEvent
	
	public List<EventInfo> getEvents(){
		return this.events;
	}
	
	public void addEvent(EventInfo info){
		events.add(info);
	}
	
	public EventInfo getLastEvent() {
		return this.events.get(this.events.size()-1);
	}
	
	public String toString(){
		String str = "";
		for(EventInfo info: events){
			str+=info+"\n";
		}
		return str;
	}
	
	public static class EventInfo {
		public String indicator;
		public double value;
		public int time;
		public EventInfo(String indicator,double value,int time){
			this.indicator = indicator;
			this.value = value;
			this.time = time;
		}
		
		public static EventInfo create(){
			return new EventInfo("",0,0);
		}
		
		public String toString(){
			return indicator+" to "+value+" in "+time+"ms";
		}
	}
}


