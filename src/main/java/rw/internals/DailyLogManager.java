package rw.internals;
import java.util.ArrayList;

public class DailyLogManager {
        private final ArrayList<DailyLogs> StoredDailyLogs;

        public DailyLogManager() {
            this.StoredDailyLogs = new ArrayList<>();
        }

        void addLog(DailyLogs log) {
            this.StoredDailyLogs.add(log);
        }

    /**
     * This function goes through all existing daily logs and then checks if any dates match up and will retrieve them,
     * this will come in very handy for viewLogs option.
     * @param date - the date of the log you are trying to find
     * @return - returns the dailyLog object that matches with your date, or returns null if it couldn't find any
     */
    public DailyLogs getLogByDate(String date) {
        for (DailyLogs log : this.StoredDailyLogs) {
            if (log.getLogDate().equals(date)) {
                return log;
            }
        }
        return null;
    }

    /**
     * This function simply goes checks if storedDailyLogs is empty or not and will return the last log accordingly
     * @return - returns last log if it exists, otherwise returns null (if the StoredDailyLogs is empty)
     */
    public DailyLogs getLastLog()  {
        if(getStoredDailyLogs().isEmpty()){
            return null;
        }
        return this.getStoredDailyLogs().getLast();
    }

    /**
     * Needed to add this function to clear out stored dailyLogs, only when we load a new file, as we need to clear the
     * data from here.
     */
    public void clearStoredDailyLogs(){
        this.StoredDailyLogs.clear();

    }

    /**
     * Added this function to grab all the stored daily logs, when we need to go through all of them, say when we try
     * to saved data, etc
     * @return - returns the list of all stored daily logs
     */
    public ArrayList<DailyLogs> getStoredDailyLogs() {
            return new ArrayList<>(this.StoredDailyLogs);
            // Return a copy to avoid external modifications
        }

    }

//