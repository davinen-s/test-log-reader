package test.logreader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import test.logreader.model.LogEntry;



/**
 * Main class.
 * 
 * @author Davi
 */
public class App 
{
	/** The delimiter used in the text file. */
	private static final String DELIMITER = "\t";
	
	/** The default text file location. */
    private static final String DEFAULT_FILE_LOCATION = "c://test/lines.txt";

    
	public static void main( String[] args )
    {
		
		HashMap<String, List<LogEntry>> usersLog = new HashMap<>();
		
		//we skip 1st line as file contain headers.
		try (Stream<String> stream = Files.lines(Paths.get(DEFAULT_FILE_LOCATION)).skip(1)) {

			stream.forEach(s -> {
				String[] splitString = s.split(DELIMITER);

				String userId = splitString[0];
				if (StringUtils.isNotBlank(userId)) {
					
					
					LogEntry logEntry = populateLogEntry(splitString);
					addLogEntryInUsersLog(usersLog, userId, logEntry);
				}
			});

		} catch (IOException  e) {
			e.printStackTrace();
		}
		
		displayAllLogRecords(usersLog);
		
		String userWithHighestConsumption = StringUtils.EMPTY;
		int highestConsumption = 0;
		for(Map.Entry<String, List<LogEntry>> pair: usersLog.entrySet() ) {
			int currentUserConsumption = 0;
			for (LogEntry log: pair.getValue()) {
				currentUserConsumption += log.getResourceConsumption();
			};
			
			if (currentUserConsumption == highestConsumption) {
				userWithHighestConsumption += (", " + pair.getKey());
			} else if ( currentUserConsumption > highestConsumption) {
				userWithHighestConsumption = pair.getKey();
				highestConsumption = currentUserConsumption;
			}
		}
		
		System.out.println("\n\n\nUser with highest resource consumption is : " + userWithHighestConsumption + " and consumed " + highestConsumption);
		
		
    }

	/**
	 * Output every logs in the console.
	 * 
	 * @param usersLog
	 *            the users' log.
	 */
	private static void displayAllLogRecords(HashMap<String, List<LogEntry>> usersLog) {
		for(Map.Entry<String, List<LogEntry>> pair: usersLog.entrySet() ) {
			System.out.println("---------------- User: " + pair.getKey() + "----------------");
			pair.getValue().forEach(s -> System.out.println(s.toString()));
		}
	}

	/**
	 * Populate a log entry with read line.
	 * 
	 * @param splitString
	 *            the split read line.
	 * @return a log entry
	 */
	private static LogEntry populateLogEntry(String[] splitString) {
		
		//TODO throw exception if invalid data found.
		
		LogEntry logEntry = new LogEntry();
		logEntry.setUserId(splitString[0]);
		logEntry.setPId(splitString[1]);
		logEntry.setStartTime(getIntegerAtArrayIndexOf(splitString, 2));
		logEntry.setEndTime(getIntegerAtArrayIndexOf(splitString, 3));
		logEntry.setResourceConsumption(getIntegerAtArrayIndexOf(splitString, 4));
		return logEntry;
	}
	
	
	/**
	 * Return the integer value at the specified index of the array if not null
	 * 
	 * @param array
	 *            the array which contain the values/split string
	 * @param index
	 *            the index in the array
	 * @return an integer if not null else return null
	 */
	private static Integer getIntegerAtArrayIndexOf(String[] array, int index) {
		String stringValue = array[index];

		if (StringUtils.isNotBlank(stringValue)) {
			return Integer.parseInt(array[index]);
		} else {
			return null;
		}
	}


	/**
	 * Add the log entry of a user in the Users' log.
	 * 
	 * @param userLog
	 *            the users's log
	 * @param userId
	 *            the userId of the current log entry
	 * @param logEntry
	 *            the logEntry
	 */
	private static void addLogEntryInUsersLog(HashMap<String, List<LogEntry>> userLog, String userId,
			LogEntry logEntry) {
		if (userLog.containsKey((userId))) {
			userLog.get(userId).add(logEntry);
		} else {
			ArrayList<LogEntry> entries = new ArrayList<LogEntry>();
			entries.add(logEntry);
			userLog.put(userId, entries);
		}
	}
}
