package rw.internals;
//This entire class could have been stored in menuHelperFunctions since it is only used within the menu/main, but
//for clarity reasons I separated it into its own class, so the static methods here are also allowed since this class
//is related to writing to the file

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteInFile {


    //Writing Files Are Below
    public static boolean clearComplete = false;
    public static boolean alreadyEmpty = false;
    /**
     * Writes all daily logs currently stored in the DailyLogManager to a specified file.
     * The method first checks if there are any logs to save. If there are, it creates a new file with the provided filename if it doesn't exist,
     * and then writes each log's date followed by its meals, separating different attributes with hyphens.
     * Each meal is separated from the date and other meals by a triple hyphen ("---").
     * If there are no logs to save, it prints a message indicating so.
     *
     * @param fileName The name of the file where the daily logs should be written. This includes the file path if the file is not in the current directory.
     */
    public static void WriteDailyLogsToFile(String fileName) {



            BufferedWriter myBufferedWriter = null;
            try {
                File myfile = new File(fileName);
                //make a file if it does not exist
                if (!myfile.exists()){
                    myfile.createNewFile();
                }
                //create new fileWriter for bufferedWriter
                FileWriter myFileWriter = new FileWriter(fileName,false);
                myBufferedWriter = new BufferedWriter(myFileWriter);

                //Let's write the logs to the file now

                //loop through daily logs that need to be saved

                for(DailyLogs CurrentLog : DailyLogs.getLogTracker().getStoredDailyLogs()){

                    //write the date
                    myBufferedWriter.write(CurrentLog.getLogDate());


                    //loop through all the meals
                    for (int i = 0; i <= CurrentLog.getMealsInLog().size() - 1; i++){
                        //write the regex identifier between meals and dates
                        myBufferedWriter.write("---");

                        Meals currentMeal = CurrentLog.getMealsInLog().get(i);

                        //loop through all the meal attributes
                        for (int j = 0; j <= 4; j++) {
                            if (j > 0) { // Add a hyphen before every attribute except the first one
                                myBufferedWriter.write("-");
                            }
                            //this will loop through all 4 attributes from getMeal and add them to .csv file
                            switch (j) {
                                case 0:
                                    myBufferedWriter.write(currentMeal.getName());
                                    break;
                                case 1:
                                    myBufferedWriter.write(String.valueOf(currentMeal.getCalories()));
                                    break;
                                case 2:
                                    myBufferedWriter.write(String.valueOf(currentMeal.getProtein()));
                                    break;
                                case 3:
                                    myBufferedWriter.write(String.valueOf(currentMeal.getCarbohydrates()));
                                    break;
                                case 4:
                                    myBufferedWriter.write(String.valueOf(currentMeal.getFats()));
                                    break;
                            }
                        }

                    }
                    myBufferedWriter.write("\n");
                }
            }
            //these exceptions are just for tracing, should not actually happen during program running
            catch (IOException e) {
                //Unknown Exception occurred, this is here for tracing
                System.out.println("An exception occurred");
                e.printStackTrace();
            }
            finally{
                try {
                    if (myBufferedWriter != null) {
                        myBufferedWriter.close();
                    }
                }
                //Unknown Exception occurred, this is here for tracing
                catch(IOException e){
                    System.out.println("An exception occurred");
                    e.printStackTrace();
                }

            }
    }

    /**
     * Clears all data previously saved to a file specified by the fileName parameter.
     * If the file exists, the method overwrites it with an empty string, effectively clearing its contents.
     * If the file does not exist, a message is printed indicating that there is no file to clear.
     * The method also prints a message once the data has been successfully cleared.
     *
     * @param fileName The name of the file from which saved data is to be cleared. This includes the file path if the file is not in the current directory.
     */
    public static void clearSavedData(String fileName) {
        FileWriter fw = null;
        try {
            File file = new File(fileName);
            if (file.exists()){
                if(file.length() == 0){
                    alreadyEmpty = true;
                    clearComplete = false;
                }
                else{
                    alreadyEmpty = false;
                    // Open the file with FileWriter and set append to false, so I can overwrite the file
                    fw = new FileWriter(fileName, false);
                    // Writing an empty string to overwrite the file
                    fw.write("");
                    clearComplete = true;
                }
            }
            else {
                alreadyEmpty = false;
                clearComplete = false;
            }
        }

        //again both of these catches are for tracing purposes no exception should actually occur during runtime
        catch (IOException e) {
            System.out.println("An exception occurred");
            //unknown exception occurred, used for tracing
            e.printStackTrace();
        }
        finally {
            try {
                // Always close the FileWriter
                if (fw != null) {
                    fw.close();
                }
            }
            //unknown exception occurred, used for tracing
            catch (IOException e) {
                System.out.println("An exception occurred");
                e.printStackTrace();
            }
        }
    }
}
//