//This entire class could have been stored in menuHelperFunctions since it is only used within the menu/main, but
//for clarity reasons I separated it into its own class, so the static methods here are also allowed since this class
//is  related to reading the file
//Meant for .txt or .csv files
package rw.internals;
import java.io.*;


public class ReadFile {

    /**
     * Reads into specified file and gets information according to a specific format,
     * FORMAT: Each line in the csv is a daily log, the data and all the meals are separated using 3 dashes '---'
     *          Then within each meal, all attributes are separated by 1 dash '-'
     *          Example: 2024-03-15---BBQ-500-25-21-11---Chicken-344-12-11-5
     * @param fileName - this is the file the code reads from
     */
    public static void readStoredData(String fileName){

        try {
            // Create a File object from the provided filename to check properties.
            File myfile = new File(fileName);
            // Setup FileReader and BufferedReader to read lines from the file.
            FileReader myLineReader = new FileReader(fileName);
            BufferedReader myBufferedLineReader = new BufferedReader(myLineReader);

            // Check if the file is empty and throw an exception if true.
            if (myfile.length() == 0) {
                throw new IllegalStateException(); // Intended to be caught elsewhere to notify user.
            } else {
                // Variable to count the total number of lines in the file.
                int lines = 0;
                while (myBufferedLineReader.readLine() != null) lines++; // Count each line until end of file.
                myBufferedLineReader.close(); // Close the initial BufferedReader.

                // Set up a new reader to read the data from the file.
                FileReader DataReader = new FileReader(fileName);
                BufferedReader BufferedDataReader = new BufferedReader(DataReader);

                // Clear previously stored logs to avoid data duplication.
                DailyLogs.getLogTracker().clearStoredDailyLogs();

                // Read each line of the file and process it.
                for (int currentLineNumber = 1; currentLineNumber <= lines; currentLineNumber++) {
                    // Split the line into parts using "---" as the delimiter.
                    String [] storedLogAsArray = BufferedDataReader.readLine().split("---");

                    // First part is always the log date.
                    DailyLogs storedLog = new DailyLogs(storedLogAsArray[0]);

                    // Process the remaining parts of the array which are meal details.
                    for(int i = 1 ; i < storedLogAsArray.length ; i++){
                        // Split each meal detail using "-" as the delimiter.
                        String[] storedMealAsArray = storedLogAsArray[i].split("-");

                        // Create a new Meals object from the split data.
                        Meals storedMeal = new Meals(storedMealAsArray);

                        // Add each parsed meal to the corresponding daily log.
                        storedMeal.addMealToLog(storedLog);
                    }
                }
            }
        } catch (IOException e) {
            // Handle file I/O exceptions and rethrow a runtime exception for GUI error handling.
            throw new UncheckedIOException(new IOException()); // Rethrow as an unchecked exception for error handling in the GUI.
        }
    }

}
