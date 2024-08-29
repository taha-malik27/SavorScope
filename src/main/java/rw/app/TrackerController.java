//TAHA MALIK - taha.malik2@ucalgary.ca
//Application was built for CPSC 233 at the University of Calgary

package rw.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import rw.internals.*;

import java.io.UncheckedIOException;

public class TrackerController {

    private static boolean OnlyCalculateBMI;
    @FXML
    private TextField Weight;
    @FXML
    private TextField Height;
    @FXML
    private TextField Age;
    @FXML
    private TextArea ViewArea;
    @FXML
    private DatePicker DatePicker;
    @FXML
    private TextField AttributeValue;
    @FXML
    private AnchorPane MealAddArea;
    @FXML
    private AnchorPane BMIAddArea;
    @FXML
    private AnchorPane EditMealArea;
    @FXML
    private AnchorPane SelectDateArea;
    @FXML
    private TextField MealName;
    @FXML
    private TextField MealCalories;
    @FXML
    private TextField MealProtein;
    @FXML
    private TextField MealCarbs;
    @FXML
    private TextField MealFats;
    @FXML
    private Label MainTextArea;
    @FXML
    private Label Status;
    @FXML
    private RadioButton MButton;
    @FXML
    private RadioButton FButton;
    @FXML
    private ChoiceBox<String> MealAttribute = new ChoiceBox<>();
    @FXML
    private ChoiceBox<String> ActivityLevel = new ChoiceBox<>();

    /**
     * Initializes the controller based on command-line arguments and sets up UI components.
     * If command-line arguments are provided, it attempts to load stored data from the specified file.
     * Initializes choice boxes for meal attributes and activity levels and sets default values.
     * Also configures gender selection and clears any previously set views.
     *
     * @param cmdArguments The command-line arguments passed to initialize the controller, expected to contain file path for stored data if any.
     */
    @FXML
    public void initialize(Object[] cmdArguments){

        //if no arguments, don't do anything, otherwise load from the cmd line specified
        if (cmdArguments.length != 0){
            try {
                ReadFile.readStoredData(cmdArguments[0].toString());
                Status.setStyle("-fx-text-fill: black;");
                Status.setText("All recorded saved data was uploaded");
                // I specifically included this just for user, it's not needed, but just also informs user via command line
                System.out.println("All recorded saved data was uploaded");
            }
            catch(IllegalStateException ise){
                Status.setStyle("-fx-text-fill: red;");
                Status.setText("File for stored data is empty, no saved data will be uploaded");
                // I specifically included this just for user, it's not needed, but just also informs user via command line
                System.out.println("File for stored data is empty, no saved data will be uploaded");
            }
            catch(UncheckedIOException uioe){
                Status.setStyle("-fx-text-fill: red");
                Status.setText("Failure to read file, no data was uploaded");
                // I specifically included this just for user, it's not needed, but just also informs user via command line
                System.out.println("Failure to read file, no data was uploaded");
            }
        }

        ViewArea.setText(""); //reset viewing area to show prompt text (otherwise it doesn't show for some reason)
        resetEditAreas();
        //making it so these cant both be toggled
        ToggleGroup BiologicalGender = new ToggleGroup();
        MButton.setToggleGroup(BiologicalGender);
        FButton.setToggleGroup(BiologicalGender);

        //this will be what the "prompt text is"
        MealAttribute.setValue("Choose Attribute");
        ActivityLevel.setValue("Choose Activeness");

        // Creating the ObservableList with the given items for MealAttributes
        ObservableList<String> attributeTypes = FXCollections.observableArrayList("Name", "Calories", "Protein", "Carbs", "Fats");

        // Setting the items to the Meal Attributes ChoiceBox
        MealAttribute.setItems(attributeTypes);

        // Creating the ObservableList with items for Activity Level
        ObservableList<String> levelsOfActivity = FXCollections.observableArrayList("Not active", "Lightly active", "Moderately active", "Very active", "Extremely active");

        // Setting the items to the Activity Level ChoiceBox
        ActivityLevel.setItems(levelsOfActivity);
    }

    /**
     * Attempts to load saved data from a specific CSV file named "Saved_Log_Data.csv".
     * Resets the UI view area and edit areas before loading.
     * If the file is empty or an error occurs, updates the UI status with an error message.
     */
    @FXML
    public void LoadSave1(){
        ViewArea.setText("");//resetting view area
        resetEditAreas();
        try {
            ReadFile.readStoredData("Saved_Log_Data.csv");
            Status.setStyle("-fx-text-fill: black;");
            Status.setText("All recorded saved data was uploaded");
        }
        catch(IllegalStateException ise){
            Status.setStyle("-fx-text-fill: red;");
            Status.setText("File for stored data is empty, no saved data will be uploaded");
        }
        catch(UncheckedIOException uioe){
            Status.setStyle("-fx-text-fill: red");
            Status.setText("Failure to read file, no data was uploaded");
        }
    }

    /**
     * Saves current data to a file named "Saved_Log_Data.csv".
     * Resets the view and edit areas before saving.
     * If there are no logs to save, updates the status with an error message.
     * On successful save, confirms the action with a status message.
     */
    @FXML
    public void SaveData2(){
        ViewArea.setText("");//resetting view area
        resetEditAreas();
        if (DailyLogs.getLogTracker().getStoredDailyLogs().isEmpty()){
            Status.setStyle("-fx-text-fill: red");
            Status.setText("You don't have any logs that need to be saved!");
        }
        else{
            WriteInFile.WriteDailyLogsToFile("Saved_Log_Data.csv");
            Status.setStyle("-fx-text-fill: black;");
            Status.setText("Data has been saved!");
        }
    }

    /**
     * Clears all saved data from a file named "Saved_Log_Data.csv".
     * Resets the view and edit areas before attempting the clear operation.
     * Updates the status based on the outcome of the operation:
     * If the file is already empty, or there is no file, or if the clear operation is successful,
     * a corresponding message is displayed in the status label.
     */
    @FXML
    public void ClearSaveData3(){
        ViewArea.setText("");//resetting view area
        resetEditAreas();
        WriteInFile.clearSavedData("Saved_Log_Data.csv");
        if (WriteInFile.clearComplete){
            Status.setStyle("-fx-text-fill: black;");
            Status.setText("Saved data has been cleared!");
            MainTextArea.setText("Previously saved data is still within the program\nRestart the application if you would like to reset data");
        }

        else if(WriteInFile.alreadyEmpty){
            Status.setStyle("-fx-text-fill: red;");
            Status.setText("File is already empty, nothing to clear!");
        }
        else{
            Status.setStyle("-fx-text-fill: red");
            Status.setText("There is no file to clear from, saved some data to create a file to clear!");
        }
    }

    /**
     * Creates a new daily log for the current day if one does not already exist.
     * Resets the view and edit areas before creating the log.
     * The status is updated to indicate whether a new log was created or if one already exists for the day.
     */
    @FXML
    public void CreateDailyLog4(){
        ViewArea.setText("");//resetting view area
        resetEditAreas();
        //Checking if a log for if today's date has already been made or if its empty
        if (MenuHelperFunctions.doesALogForTodayExist()) {
            Status.setStyle("-fx-text-fill: red;");
            Status.setText("You have already made a daily log for today!");
        }
        else {
            //make a daily log now since we know one has not been created yet for today
            new DailyLogs(); //just need to make one, not set it as a variable, as it's already stored into the tracker
            Status.setStyle("-fx-text-fill: black;");
            Status.setText("Creation completed");
            MainTextArea.setText("Great! Your daily log has been created");

        }
    }

    /**
     * Prepares the UI for adding a new meal to today's daily log.
     * Checks if a daily log for today exists and enables the meal addition area if it does.
     * If no daily log exists for the current day, instructs the user to create one first.
     * Resets the view and edit areas before enabling meal addition.
     */
    @FXML
    public void AddMeal5(){
        ViewArea.setText("");//resetting view area
        //check if daily log with today's date exist
        resetEditAreas();
        if (MenuHelperFunctions.doesALogForTodayExist()) {
            Status.setStyle("-fx-text-fill: black;");
            Status.setText("Add a Meal selected");
            MealAddArea.setDisable(false);//enable add meal area
        }
        else {
            //Daily log was not created, so user is sent back to menu to create one
            Status.setStyle("-fx-text-fill: red;");
            Status.setText("You need to create a daily log! Use menu option 1 next time!");
        }

    }

    /**
     * Submits the meal entered by the user to the current day's daily log.
     * Validates the meal details before submission and updates the log accordingly.
     * Updates the status and main text area to reflect the outcome of the meal submission,
     * indicating either success or the nature of any input errors.
     */
    @FXML
    public void submitMeal(){

        String[] add_meal_user_input = {MealName.getText(),MealCalories.getText(),MealProtein.getText(),MealCarbs.getText(),MealFats.getText()};

        //If user entered "EXIT" they do not make a meal

        MenuHelperFunctions.verifyMeal(add_meal_user_input);

        if (MenuHelperFunctions.verifyMeal(add_meal_user_input) && DailyLogs.getLogTracker().getLastLog() != null){
            //DailyLogs.getLatestLog() will not be null, this check is really just so the IDE stopped giving a warning

            //meal was verified, lets make it
            Meals new_meal = new Meals(add_meal_user_input);
            //add meal to today's daily log
            new_meal.addMealToLog(DailyLogs.getLogTracker().getLastLog());
            //inform user valid meal has been made
            Status.setStyle("-fx-text-fill: black;");
            Status.setText("Valid Meal, creating...");
            MainTextArea.setText("Your meal: '"+ MealName.getText() + "' has been logged!\nHope it was tasty!");
        }
        else{
            //invalid status, inputted invalid meal, inform user of this
            Status.setStyle("-fx-text-fill: red;");
            Status.setText("Invalid meal inputted, ensure all macros and calories are numeric and name is non-numeric");
        }



    }
    /**
     * Prepares the UI for editing the most recently added meal to today's daily log.
     * Checks if today's daily log exists and if it contains any meals.
     * Enables the meal editing area if conditions are met, or updates the status with instructions
     * or errors based on the existence of logs or meals.
     */
    @FXML
    public void EditMeal6(){
        ViewArea.setText("");//resetting view area
        resetEditAreas();
        if (!MenuHelperFunctions.doesALogForTodayExist()) {
            //Daily log was not created, so user is sent back to menu to create one
            Status.setStyle("-fx-text-fill: red;");
            Status.setText("You need to create a daily log! Use menu option 1 next time!");
        }
        else {
            DailyLogs latestDailyLog = DailyLogs.getLogTracker().getLastLog(); // Consolidated log retrieval
            if (latestDailyLog.getMealsInLog().isEmpty()) {
                Status.setStyle("-fx-text-fill: red;");
                Status.setText("There are no meals in today's log to edit! Add a meal first using menu option 4.");
            }
            else {
                Status.setStyle("-fx-text-fill: black;");
                Status.setText("Edit Latest Meal selected");
                MainTextArea.setText("Edit your meal in the area below");
                ViewArea.setText("Your last meal:\n" + latestDailyLog.getLastMeal());
                EditMealArea.setDisable(false);
            }
        }

    }
    /**
     * Submits the changes made to a meal's attributes based on user input in the editing area.
     * Validates the new attribute values and applies changes to the selected meal attribute.
     * Updates the UI to reflect the success or failure of the edit, providing specific error messages
     * for invalid inputs.
     */
    @FXML
    public void submitEditedMeal(){
        DailyLogs latestDailyLog = DailyLogs.getLogTracker().getLastLog(); // Retrieve the latest daily log available.
        Meals lastMeal = latestDailyLog.getLastMeal(); // Get the most recent meal added to the latest log.

        String userChoice = MealAttribute.getValue(); // Get the attribute selected by the user for editing.
        String attributeValue = AttributeValue.getText(); // Get the new value for the selected attribute from user input.

        switch (userChoice) { // Process the edit based on the attribute selected.
            case "Name":
                // Check if the new value for 'Name' is not a numeric value.
                if (!MenuHelperFunctions.isInteger(attributeValue)){
                    lastMeal.setName(attributeValue); // Set the new name if valid.
                    Status.setStyle("-fx-text-fill: black;");
                    Status.setText("Edit Successful");
                    MainTextArea.setText(userChoice + " has been changed");
                }
                else{
                    // Display error if the input for 'Name' is numeric.
                    Status.setStyle("-fx-text-fill: red;");
                    Status.setText("Invalid " + userChoice + " , ensure its non-numeric");
                }
                break;
            // Additional cases for 'Calories', 'Protein', 'Carbs', 'Fats' follow similar pattern.
            case "Calories":
                if (MenuHelperFunctions.isInteger(attributeValue) && Integer.parseInt(attributeValue) >= 0){
                    lastMeal.setCalories(Integer.parseInt(attributeValue));
                    Status.setStyle("-fx-text-fill: black;");
                    Status.setText("Edit Successful");
                    MainTextArea.setText(userChoice + " has been changed");
                }
                else{
                    Status.setStyle("-fx-text-fill: red;");
                    Status.setText("Invalid " +userChoice+ " , ensure it is numeric and non negative");
                }
                break;
            case "Protein":
                if (MenuHelperFunctions.isInteger(attributeValue) && Integer.parseInt(attributeValue) >= 0){
                    lastMeal.setProtein(Integer.parseInt(attributeValue));
                    Status.setStyle("-fx-text-fill: black;");
                    Status.setText("Edit Successful");
                    MainTextArea.setText(userChoice + " has been changed");
                }
                else{
                    Status.setStyle("-fx-text-fill: red;");
                    Status.setText("Invalid " +userChoice+ " , ensure it is numeric and non negative");
                }
                break;
            case "Carbs":
                if (MenuHelperFunctions.isInteger(attributeValue) && Integer.parseInt(attributeValue) >= 0){
                    lastMeal.setCarbohydrates(Integer.parseInt(attributeValue));
                    Status.setStyle("-fx-text-fill: black;");
                    Status.setText("Edit Successful");
                    MainTextArea.setText(userChoice + " has been changed");
                }
                else{
                    Status.setStyle("-fx-text-fill: red;");
                    Status.setText("Invalid " +userChoice+ " , ensure it is numeric and non negative");
                }
                break;
            case "Fats":
                if (MenuHelperFunctions.isInteger(attributeValue) && Integer.parseInt(attributeValue) >= 0){
                    lastMeal.setFats(Integer.parseInt(attributeValue));
                    Status.setStyle("-fx-text-fill: black;");
                    Status.setText("Edit Successful");
                    MainTextArea.setText(userChoice + " has been changed");
                }
                else{
                    Status.setStyle("-fx-text-fill: red;");
                    Status.setText("Invalid " +userChoice+ " , ensure it is numeric and non negative");
                }
                break;
            default:
                Status.setStyle("-fx-text-fill: red;");
                Status.setText("Select a attribute to edit!");
        }

    }
    /**
     * Prepares the UI for viewing any stored daily log by enabling the date selection area.
     * Checks if there are stored daily logs available and updates the UI accordingly.
     * If no logs are available, it updates the status with an appropriate message.
     */
    @FXML
    public void ViewAnyLog7(){
        resetEditAreas();
        if(DailyLogs.getLogTracker().getStoredDailyLogs().isEmpty()){
            Status.setStyle("-fx-text-fill: red;");
            Status.setText("You do not have any daily logs, make some!");
        }
        else{
            Status.setStyle("-fx-text-fill: black;");
            Status.setText("View Any Log selected");
            SelectDateArea.setDisable(false);
        }
    }

    /**
     * Displays the daily log corresponding to the user-selected date.
     * Validates the selected date and searches for a log with that date.
     * Updates the UI to show the log or an error message if no log exists for the selected date.
     */
    @FXML
    public void submitViewLogDate(){
        ViewArea.setText(""); //reset viewing area
        if(DatePicker.getValue() != null) {

            String dateUserInput = DatePicker.getValue().toString();

            if (MenuHelperFunctions.is_date_valid(dateUserInput)) {

                //get log with the inputted date
                DailyLogs logByDate = DailyLogs.getLogTracker().getLogByDate(dateUserInput);

                //if a match is not found, alert the user
                if (logByDate == null) {
                    Status.setStyle("-fx-text-fill: red;");
                    Status.setText("You do not have any logs with that date");
                }
                //show log otherwise
                else {
                    Status.setStyle("-fx-text-fill: black;");
                    Status.setText("Log found");
                    MainTextArea.setText("Here is the log you selected!");
                    ViewArea.setText(logByDate.toString());
                }
            }
        }

        else{
            Status.setStyle("-fx-text-fill: red;");
            Status.setText("Select a date!");
        }
    }

    /**
     * Finds and displays the meal with the highest protein content from all stored daily logs.
     * Checks if there are stored logs and meals within those logs before attempting to find the meal.
     * Updates the UI with details of the meal or an error message if no applicable meals are found.
     */
    @FXML
    public void ViewHighestProteinMeal8(){
        ViewArea.setText(""); // Clear the viewing area for new output.
        resetEditAreas(); // Reset editing areas to ensure only relevant UI components are active.

        // Check if there are any stored daily logs available for analysis.
        if (DailyLogs.getLogTracker().getStoredDailyLogs().isEmpty()) {
            Status.setStyle("-fx-text-fill: red;");
            Status.setText("You do not have any daily logs");
        }
        else {
            // Attempt to find the meal with the highest protein content.
            Meals highestProteinMeal = MenuHelperFunctions.compareMealAttributesFor(AttributeToCompare.PROTEIN);
            if (highestProteinMeal != null){
                Status.setStyle("-fx-text-fill: black;");
                Status.setText("Highest protein meal found");
                MainTextArea.setText("Here is your highest protein meal!");
                // Display the highest protein meal details.
                ViewArea.setText("Your highest protein meal is:\n\n" + highestProteinMeal);
            }
            else{
                // Inform the user if no meals were found in the logs.
                Status.setStyle("-fx-text-fill: red;");
                Status.setText("You had no meals in any of your logs!");
            }
        }
    }
    /**
     * Calculates and displays the average values of meal attributes (calories, protein, carbs, fats) across all stored daily logs.
     * Checks if there are stored logs and if they contain meals. Calculates averages only if applicable meals exist.
     * Updates the UI with the calculated averages or an error message if no meals are available for calculation.
     */
    @FXML
    public void ViewAllTimeAverages9(){
        ViewArea.setText(""); //reset viewing area
        resetEditAreas();
        if (DailyLogs.getLogTracker().getStoredDailyLogs().isEmpty()) {
            Status.setStyle("-fx-text-fill: red;");
            Status.setText("You do not have any daily logs");
        }
        else {
            // Defining the attribute names we're interested in tracking
            String[] attributes = {"Average Calories", "Average Protein", "Average Carbs", "Average Fats"};
            // Initialize arrays to hold the total values for each attribute across all meals
            int[] attributeTotals = new int[attributes.length];
            // Counters to keep track of the number of meals for each attribute, to later calculate the average
            int[] counters = new int[attributes.length];

            // Loop through all stored daily logs
            for (DailyLogs log : DailyLogs.getLogTracker().getStoredDailyLogs()) {
                // Skip this log if there are no meals in it, no point in processing further
                if (log.getMealsInLog().isEmpty()) continue;

                // Iterate through each meal in the current log
                for (Meals meal : log.getMealsInLog()) {
                    // Aggregate the total values for each attribute from this meal
                    attributeTotals[0] += meal.getCalories();
                    attributeTotals[1] += meal.getProtein();
                    attributeTotals[2] += meal.getCarbohydrates();
                    attributeTotals[3] += meal.getFats();
                    // Increment the counters for each attribute to reflect this new meal
                    for (int i = 0; i < counters.length; i++) counters[i]++;
                }
            }
            StringBuilder sb = new StringBuilder();
            // Loop over each attribute to calculate and print out the average value
            for (int i = 0; i < attributes.length; i++) {
                // Ensure we're not dividing by zero if no meals have been recorded
                if (counters[i] != 0) {
                    // Create the string for what will show user
                    sb.append(attributes[i]).append(": ").append(attributeTotals[i] / counters[i]).append("\n");
                }
            }

            //if first counter is 0 all of them are, meaning there are no meals in any of the logs
            if(counters[0] == 0){
                //tell user that there were no meals in any of the logs
                Status.setStyle("-fx-text-fill: red;");
                Status.setText("You had no meals in any of your logs!");
            }
            else{
                MainTextArea.setText("Here are all your averages!");
                Status.setStyle("-fx-text-fill: black;");
                Status.setText("Averages were calculated");
                ViewArea.setText("Your all time averages:\n\n" + sb);

            }
        }
    }
    /**
     * Identifies and displays the healthiest and unhealthiest meals based on calorie and fat content across all stored daily logs.
     * Checks if there are stored logs and meals before attempting to find the meals.
     * Updates the UI with details of the meals or an error message if no applicable meals are found.
     */
    @FXML
    public void Healthiest_Unhealthiest_Meal10(){
        ViewArea.setText(""); //reset viewing area
        resetEditAreas();
        if (DailyLogs.getLogTracker().getStoredDailyLogs().isEmpty()) {
            Status.setStyle("-fx-text-fill: red;");
            Status.setText("You do not have any daily logs");
        }
        else {
            StringBuilder sb = new StringBuilder();

            Meals healthiestMeal = MenuHelperFunctions.compareMealAttributesFor(AttributeToCompare.CALORIES_AND_FATS_HEALTHY);
            if (healthiestMeal != null){
                //print out highest meal if it exist, using overridden toString()
                sb.append("Your healthiest meal is:\n\n").append(healthiestMeal).append("\n");

                Meals unhealthiestMeal = MenuHelperFunctions.compareMealAttributesFor(AttributeToCompare.CALORIES_AND_FATS_UNHEALTHY);

                if (unhealthiestMeal != null){
                    //print out highest meal if it exist, using overridden toString()
                    sb.append("Your unhealthiest meal is:\n\n").append(unhealthiestMeal).append("\n");
                    MainTextArea.setText("Here are your healthiest and unhealthiest meal!");
                    Status.setStyle("-fx-text-fill: black;");
                    Status.setText("Found healthiest and unhealthiest meal");
                    ViewArea.setText(sb.toString());

                }
            }
            else{
                //tell user that there were no meals in any of the logs
                Status.setStyle("-fx-text-fill: red;");
                Status.setText("You had no meals in any of your logs!");
            }
        }

    }
    /**
     * Prepares the UI for BMI calculation by enabling relevant input fields and disabling irrelevant ones.
     * Resets other editing areas and updates the status to guide the user to provide necessary information for BMI calculation.
     */
    @FXML
    public void BMI11(){
        OnlyCalculateBMI = true; // Set the flag to true, indicating that only BMI should be calculated.
        resetEditAreas(); // Call to reset UI components that are not relevant to BMI calculation.
        Status.setStyle("-fx-text-fill: black;"); // Set the status text color to black.
        Status.setText("Calculate BMI selected"); // Update the status to inform the user that BMI calculation is selected.
        MainTextArea.setText("Add in the necessary information for your BMI"); // Guide the user to input necessary data.
        BMIAddArea.setDisable(false); // Enable the BMI input area for user input.
        Age.setDisable(true); // Disable the age input as it is not needed for BMI calculation.
        MButton.setDisable(true); // Disable the male gender selection button.
        FButton.setDisable(true); // Disable the female gender selection button.
        ActivityLevel.setDisable(true); // Disable the activity level choice box as it is irrelevant to BMI.
    }

    /**
     * Prepares the UI for daily caloric need calculation based on user inputs including weight, height, age, gender, and activity level.
     * Enables all relevant input fields and resets other editing areas. Provides UI guidance for entering necessary information.
     */
    @FXML
    public void CalcCalories12(){
        OnlyCalculateBMI = false; // Set the flag to false, indicating that calorie calculation is intended.
        resetEditAreas(); // Reset other UI editing areas to prevent interaction that's not relevant.
        Status.setStyle("-fx-text-fill: black;"); // Update the status text color to black for consistency.
        Status.setText("Calculate Calories selected"); // Inform the user that calorie calculation mode is active.
        MainTextArea.setText("Add in the necessary information for your calories"); // Prompt user for necessary input data.
        BMIAddArea.setDisable(false); // Enable the calorie calculation input area.
        Age.setDisable(false); // Enable the age input field as it's necessary for calorie calculations.
        MButton.setDisable(false); // Enable male gender selection, required for accurate calorie calculations.
        FButton.setDisable(false); // Enable female gender selection.
        ActivityLevel.setDisable(false); // Enable activity level choice, as it impacts caloric needs.
    }

    /**
     * Calculates and displays either BMI or daily caloric needs based on user input.
     * Determines which calculation to perform based on the state of `OnlyCalculateBMI`.
     * Validates input and updates the UI with the result or error messages based on input validity and selected calculation.
     */
    @FXML
    public void submitBMIorCalories(){
        ViewArea.setText(""); // Clear the viewing area for output.

        // Determine if the operation is for BMI or Caloric calculation based on the OnlyCalculateBMI flag.
        if(OnlyCalculateBMI){
            String weight = Weight.getText(); // Get the user input for weight.
            String height = Height.getText(); // Get the user input for height.
            // Validate that weight and height are numeric and greater than zero.
            if (MenuHelperFunctions.canBeParsedAsDouble(weight) && MenuHelperFunctions.canBeParsedAsDouble(height) && Double.parseDouble(weight) > 0 && Double.parseDouble(height) > 0 ){
                double BMI = BMIAndCalories.BMICalculator(weight, height); // Calculate BMI using provided values.
                Status.setStyle("-fx-text-fill: black;");
                Status.setText("Calculating BMI..."); // Update status to indicate BMI calculation.
                MainTextArea.setText("Here is your BMI!"); // Inform user that BMI calculation is complete.
                ViewArea.setText(String.format("\nYour BMI is: %.2f\n", BMI)); // Display calculated BMI.
            }
            else {
                Status.setStyle("-fx-text-fill: red;");
                Status.setText("Weight and height should be valid numbers"); // Error message for invalid input.
            }
        }
        else{
            // Retrieve all necessary inputs for calorie calculation.
            String age = Age.getText();
            String weight = Weight.getText();
            String height = Height.getText();
            String gender = null;
            if(MButton.isSelected()){
                gender = "M"; // Set gender to male if MButton is selected.
            }
            if(FButton.isSelected()){
                gender = "F"; // Set gender to female if FButton is selected.
            }
            String[] arrayWeightHeightAgeGender = {weight, height, age, gender}; // Array of inputs.
            // Validate the input parameters before proceeding.
            if (MenuHelperFunctions.isValidWeightHeightAgeGender(arrayWeightHeightAgeGender)){
                double numWeight = Double.parseDouble(arrayWeightHeightAgeGender[0]);
                double numHeight = Double.parseDouble(arrayWeightHeightAgeGender[1]) * 100; // Convert height from meters to centimeters.
                double numAge = Double.parseDouble(arrayWeightHeightAgeGender[2]);
                String Gender = arrayWeightHeightAgeGender[3];
                String activityLevel = ActivityLevel.getValue(); // Retrieve the activity level from the user input.
                // Validate the activity level input before proceeding.
                if (MenuHelperFunctions.isValidActivityLevel(activityLevel)) {
                    double recCalories = BMIAndCalories.userCaloriesCalculator(numWeight, numHeight, numAge, Gender, activityLevel);
                    Status.setStyle("-fx-text-fill: black;");
                    Status.setText("Calculating Calories..."); // Update status to indicate calorie calculation.
                    MainTextArea.setText("Here are your recommended calories!"); // Inform user that calorie calculation is complete.
                    ViewArea.setText(String.format("Your recommended maintenance calories are: %.2f calories\n", recCalories)); // Display calculated daily calories.
                }
                else{
                    Status.setStyle("-fx-text-fill: red;");
                    Status.setText("Invalid activity level, select from one of the options"); // Error message for invalid activity level input.
                }
            }
            else{
                Status.setStyle("-fx-text-fill: red;");
                Status.setText("Invalid Height, Weight, Age, or Gender, ensure all texts are numeric and gender was selected"); // Error message for invalid inputs.
            }
        }
    }

    /**
     * Displays an information dialog about the application, its authors, and its version.
     * Sets the appearance of the dialog and provides details about the software purpose and usage.
     */
    @FXML
    public void About(){
        //create the alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //set title for the alert
        alert.setTitle("About Us");
        //now set the main header text
        alert.setHeaderText("About Us and SavorScope");
        DialogPane aboutUs = alert.getDialogPane();
        //set style and text

        aboutUs.setStyle("-fx-font-family: 'Tw Cen MT'; -fx-font-size: 16px;");
        aboutUs.setContentText(
                """
                        Author: Taha Malik
                        Version: 1.0
                        SavorScope is designed to help you monitor and manage your dietary intake effectively. It allows you to track daily meals, calculate BMI, and estimate daily caloric needs based on personal data such as age, weight, height, and activity level.\s

                        Features include:
                        - Ability to log meals and their nutritional content (calories, proteins, carbs, fats).
                        - Calculation of BMI and personalized caloric needs.
                        - Viewing and editing of meal logs, with options to add, edit, or remove meal entries as needed.
                        - Saving and loading of data to ensure no loss of your daily logs and meal information.
                        - Reporting features that display highest protein meals, healthiest and unhealthiest meals, and overall nutritional averages.
                        - Customizable meal attributes for detailed tracking.

                        We are committed to continuous improvement and welcome your feedback to make SavorScope even better. Please reach out to us via our contact information in the 'Contact Us' section for any suggestions.

                        Thank you for choosing SavorScope to support your health and fitness goals!"""
        );
        aboutUs.setPrefSize(881,575);
        //now add the icon to the alert as well for consistency and professionalism
        Image icon = new Image("SavorScope.png");
        Stage alertStage = (Stage)alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(icon);

        alert.show();
    }

    /**
     * Displays a contact information dialog with details for reaching the developers.
     * This method creates an informational alert dialog that lists the names, university IDs, and email addresses
     * of the developers. It's designed to provide users with a straightforward way to contact the developers
     * for questions or support.
     * The dialog is styled and an application-specific icon is added to maintain consistency and professionalism.
     */
    @FXML
    public void ContactUs(){
        //create the alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //set title for the alert
        alert.setTitle("Contacts");
        //now set the main header text
        alert.setHeaderText("Contact Us!");
        DialogPane contactInfo = alert.getDialogPane();
        //set style and text

        contactInfo.setStyle("-fx-font-family: 'Tw Cen MT'; -fx-font-size: 16px;");
        contactInfo.setContentText(
                """
                        If you have any questions or need support, feel free to contact me:

                        Taha Malik
                        UCID: 30204169
                        Email: taha.malik2@ucalgary.ca"""
        );

        //now add the icon to the alert as well for consistency and professionalism
        Image icon = new Image("SavorScope.png");
        Stage alertStage = (Stage)alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(icon);

        alert.show();
    }

    /**
     * Exits the application.
     * Terminates the running JavaFX application, effectively closing all windows and stopping all processes.
     */
    @FXML
    public void quit(){
        System.exit(0);
    }

    /**
     * Resets all interactive UI areas to their initial disabled state.
     * This method ensures that all areas such as meal addition, meal editing, date selection, and BMI input
     * are set to disabled until explicitly enabled by other actions within the controller.
     * This is used to prevent user interaction with UI components until they are relevant.
     */
    private void resetEditAreas(){
        // Disable all editable UI areas to ensure they are only enabled explicitly when needed.
        MealAddArea.setDisable(true);
        EditMealArea.setDisable(true);
        SelectDateArea.setDisable(true);
        BMIAddArea.setDisable(true);
    }
}