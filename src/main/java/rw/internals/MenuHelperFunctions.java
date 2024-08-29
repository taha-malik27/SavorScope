//THESE ARE STATIC FUNCTIONS THAT ARE USED IN MENU.JAVA, MENU.JAVA WOULD BE REALLY LONG IF I DIDN'T DO THIS, SO I JUST
//PUT IT INTO A NEW FILE, ALSO YOU WILL TELL THAT ALMOST IF NOT ALL THE FUNCTIONS RETURN BOOLEANS IN THE AID OF
//DETERMINING VALID USER INPUT
package rw.internals;
import java.time.LocalDate;
import java.util.Objects;

public class MenuHelperFunctions {

    /**
     * Helper function 1 - doesALogForTodayExist() - This function simply checks if a log for today exist by checking
     * the dates, first it checks if no logs exist, which would return null for getLatestLog, then it checks if one log
     * exists does that log have today's date.
     */
    public static boolean doesALogForTodayExist() {
        String date = LocalDate.now().toString();
        return DailyLogs.getLogTracker().getLastLog() != null && DailyLogs.getLogTracker().getLastLog().getLogDate().equals(date);
    }

    /**
     * Helper function 2 - isInteger() - This function simply checks for if a string is able to be an integer if it was converted
     *
     * @param s - This parameter is just the actual string variable that will be checked if it can be an integer
     * @return - returns true if string can be an integer
     */
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s); // Try to parse the string as an integer
            return true;
        } catch (Exception e) {
            return false; // String is not an integer
        }
    }

    /**
     * Helper function 3 - isValidWeightHeightAgeGender() - This function is used first in calculate_recommended_calories_9()
     * and essentially after the user gives their weight, height and age, it goes through here to be validated in the
     * format we want. This function also uses help from the canBeParsedAsDouble() function which checks if a String
     * can be made into a double. Finally, it will check if the gender requirement was fulfilled
     * PRE-CONDITION - USER MUST ENTER IT IN ORDER WEIGHT HEIGHT AGE GENDER
     *
     * @param array - this is the array that contains weight, height, age in that order
     * @return - returns a boolean which when true means the user inputted a valid input and when false it's the
     * opposite, invalid input from user
     */
    public static boolean isValidWeightHeightAgeGender(String[] array) {
        // Check array length, it should be 3 and 3 only
        if (array.length != 4) {
            return false;
        }

        // Check if all elements can be parsed as a double
        for (int i = 0; i < array.length - 1; i++) {
            if (!canBeParsedAsDouble(array[i])) {
                return false; // Invalid if any element cannot be parsed as a double other than the last one
            }
        }

        for (int i = 0; i < array.length - 1; i++) {
            double elementAsDouble = Double.parseDouble(array[i]);
            if (!(elementAsDouble > 0)) {
                return false; // Invalid if any element is negative or zero (these things must be positive)
            }
        }

        //check if final part is indeed valid input for gender
        return Objects.equals(array[3], "M") || Objects.equals(array[3], "m") || Objects.equals(array[3], "F") || Objects.equals(array[3], "f");

    }

    /**
     * Helper function 4 - isValidActivityLevel() - This function is used to check if user entered a proper activity
     * level option from the instructions when trying to calculate their recommended calories (Menu option 11)
     *
     * @param activityLevel - user input of the activity level they gave
     * @return - returns true if user gave an exact copy of the options for the activity level, otherwise returns false
     * as its considered invalid input
     */
    public static boolean isValidActivityLevel(String activityLevel) {
        return switch (activityLevel) {
            case "Not active", "Extremely active", "Very active", "Moderately active", "Lightly active" -> true;
            //If invalid input, the switch statement goes to default
            default -> false;
        };
    }


    /**
     * Helper function 5 - is_date_valid() - This function simply checks for if a given string is in the valid format
     * for the trackers dates, which have the format YYYY-MM-DD. It checks the format and if it is not aligned it returns
     * false
     *
     * @param date - the string parameter that is being tested if it's in the valid date format
     * @return - returns true if the string was in the valid date format
     */
    public static boolean is_date_valid(String date) {

        //since format for date should be YYYY-MM-DD we can split it using "-" as the splitter character to make date into an array
        String[] date_as_array = date.split("-");

        //checks if the date given is of proper length, it should always be length 3
        if (date_as_array.length != 3) {
            return false;
        }

        // This part checks that all components of the date (YYYY, MM and DD) are all numeric integers
        for (String date_component : date_as_array) {
            if (!isInteger(date_component)) {
                return false;
            }
        }
        //returns true if the date passes all formatting tests
        return true;
    }


    /**
     * Helper function 6 - canBeParsedAsDouble() - This function is a copy of isInteger() is does the exact same thing
     * but simply does it with doubles, what we check for here is if a string can become a double or not if it was
     * converted.
     *
     * @param s - this is just a string, and it's going to be tested to see if it can become a double or not
     * @return - returns a boolean, true if the string can become a double and false if it cannot
     */
    public static boolean canBeParsedAsDouble(String s) {
        try {
            Double.parseDouble(s); // Try to parse the string as a double
            return true;
        } catch (Exception e) {
            return false; // String cannot be parsed as a double
        }
    }


    /**
     * Helper function 7 - verifyMeal() - This function simply checks for user input, and to see if its valid meal input according to the instructions given
     *
     * @param array - this is the user input split into an array
     * @return - returns a boolean to menu.java to verify if input is valid
     */
    public static boolean verifyMeal(String[] array) {
        // Check array length needs to be 5
        if (array.length != 5) {
            return false;
        }

        // Check if the first element is a name (simplistic check: contains no digits)
        if (isInteger(array[0])) {
            return false;
        }

        // Check if the last four elements are numbers (integers in this example)
        for (int i = 1; i < array.length; i++) {
            if (!isInteger(array[i])) {
                return false;
            }
        }
        // Adding in a check that ensures the last four elements are greater than 0 (calories, etc. cannot be negative)
        for (int i = 1; i < array.length; i++) {
            int integer_meal_values = Integer.parseInt(array[i]);
            if (!(integer_meal_values >= 0)) {
                return false;
            }
        }
        return true; // Passed all checks
    }

    /**
     * Helper Function 8 - compareMealAttributesFor() - Compares attributes of meals across all daily logs to find the
     * meal with either the highest or lowest specified attribute. The comparison is based on the type of attribute
     * passed as an argument, such as protein content, healthy calories and fats, or unhealthy calories and fats. This
     * method iterates through all daily logs and their respective meals to perform the comparison.
     *
     * @param comparisonType The attribute to compare across all meals. This is of type AttributeToCompare, an enum
     *                       that specifies the attribute type such as PROTEIN, CALORIES_AND_FATS_HEALTHY, or
     *                       CALORIES_AND_FATS_UNHEALTHY.
     * @return Returns the meal with the highest or lowest attribute value as specified by the comparisonType.
     *         If no meals are found or all logs are empty, returns null.
     */
    public static Meals compareMealAttributesFor(AttributeToCompare comparisonType) {

        //initialize the meal with the highest/lowest attribute
        Meals mealWithHighestOrLowestAttributeSoFar = null;

        //boolean that will be used to mark the first meal
        boolean firstMeal = true;

        //loop through all daily logs
        for (DailyLogs currentLog : DailyLogs.getLogTracker().getStoredDailyLogs()) {

            //if a log has no meals skip it
            if (currentLog.getMealsInLog().isEmpty()) {
                continue;
            }
            //loop through daily logs meals
            for (Meals currentMeal : currentLog.getMealsInLog()) {

                //if it's the first meal to be checked, we make this the highest attribute meal
                if (firstMeal) {
                    mealWithHighestOrLowestAttributeSoFar = currentMeal;
                        /*
                        we have to set this meal's comparisonType to the specified attribute since
                        we can be comparing different types from the enum class (protein, healthy or unhealthy)
                        this allows us to use the overridden compareTo method without error (as the compareTo
                        looks at the enum state to determine what attributes to compare)
                        */
                    mealWithHighestOrLowestAttributeSoFar.setCurrentComparisonType(comparisonType);
                }
                else {
                    //compare meals using overridden compareTo to get the highest or lowest attribute so far
                    int highestOrLowestAttribute = mealWithHighestOrLowestAttributeSoFar.compareTo(currentMeal);
                    //if the currentMeal had the highest or lowest attribute (depending on what it was comparing for)
                    //we change it to the new highest or lowest protein meal
                    switch (comparisonType) {
                        case PROTEIN:
                            //if the currentMeal had the highest or lowest attribute (depending on what it was comparing for)
                            //we change it to the new highest or lowest protein meal (here it's protein)
                            if (highestOrLowestAttribute == currentMeal.getProtein()) {
                                mealWithHighestOrLowestAttributeSoFar = currentMeal;
                                //reinitialize comparisonType since it's pointed to a new meal
                                mealWithHighestOrLowestAttributeSoFar.setCurrentComparisonType(comparisonType);
                            }
                            break;
                        case CALORIES_AND_FATS_HEALTHY, CALORIES_AND_FATS_UNHEALTHY:
                            //if the currentMeal had the highest or lowest attribute (depending on what it was comparing for)
                            //we change it to the new highest or lowest protein meal (here it's the average of fats and calories)
                            if (highestOrLowestAttribute == (currentMeal.getCalories() + currentMeal.getFats()) / 2) {
                                mealWithHighestOrLowestAttributeSoFar = currentMeal;
                                //reinitialize comparisonType since it's pointed to a new meal
                                mealWithHighestOrLowestAttributeSoFar.setCurrentComparisonType(comparisonType);
                            }
                    }
                }
                //after the first meal occurs it will never be first meal again, so we make this false and it remains this way
                firstMeal = false;
            }
        }
        return mealWithHighestOrLowestAttributeSoFar;
    }
}
//