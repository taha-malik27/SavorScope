package rw.internals;
import java.time.LocalDate;
import java.util.ArrayList;

public class DailyLogs {

    private final String LogDate;

    private final ArrayList<Meals> mealsInLog;

    //only need 1 log tracker for all daily logs, so I don't put it into the constructor (Only 1 static field)
    private static final DailyLogManager LogTracker = new DailyLogManager();

    public DailyLogs() {

        //making log date into string and grabbing it from java.time
        this.LogDate = LocalDate.now().toString();

        //initialize meal tracker for dailyLog
        this.mealsInLog = new ArrayList<>();

        //keeping track of all dailyLogs via this DailyLog Manager
        LogTracker.addLog(this);

    }

    //this second constructor is a form of overloading constructors and is only used in ReadFile where we already have
    //a date that was stored
    public DailyLogs(String LogDateFromFile) {

        //grabbing log date from file
        this.LogDate = LogDateFromFile;

        //initialize meal tracker for the dailyLog
        this.mealsInLog = new ArrayList<>();

        //keeping track of all dailyLogs via this DailyLog Manager
        LogTracker.addLog(this);
    }

    /**
     * Overrides the default Object.toString method to provide a string representation of the DailyLog instance,
     * including the log date and the list of meals recorded in the log.
     *
     * @return A string representation of the DailyLog, including the log date and meals.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Log of ").append(this.getLogDate()).append("\n\n");
        int MealCounter = 1;
        for (Meals meal : this.getMealsInLog()) {
            stringBuilder.append("Meal ").append(MealCounter).append("\n");
            stringBuilder.append(meal.toString()).append("\n");
            MealCounter++;
        }
        return stringBuilder.toString();
    }

    /**
     * Retrieves the log date for this DailyLog instance.
     *
     * @return The log date as a String
     */
    public String getLogDate() {
        return this.LogDate;
    }

    /**
     * Retrieves the last meal entry in the DailyLog.
     *
     * @return The last Meals object added to the log.
     */
    public Meals getLastMeal() {
        return this.getMealsInLog().getLast();
    }

    /**
     * Provides access to the list of meals recorded in this DailyLog.
     *
     * @return An ArrayList of Meals objects that have been recorded in the log.
     */
    public ArrayList<Meals> getMealsInLog() {
        return mealsInLog;
    }

    /**
     * Accessor method for the singleton instance of DailyLogManager. This is the only static method
     *
     * @return The singleton instance of DailyLogManager used for tracking daily logs.
     */
    public static DailyLogManager getLogTracker() {
        return LogTracker;
    }
}