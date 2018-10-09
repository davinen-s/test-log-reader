package test.logreader.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Bean representing a log entry.
 * 
 * @author Davi
 */
@Getter
@Setter
public class LogEntry {
	
	private String userId;
	
	private String pId;
	
	private int startTime;
	
	private int endTime;
	
	private int resourceConsumption;
	
	@Override
	public String toString() {
		return "userId: " + userId + ", pId: " + pId + ", startTime: " + startTime + ", endTime: " + endTime + ", resourceConsumption: " + resourceConsumption;
	}

}
