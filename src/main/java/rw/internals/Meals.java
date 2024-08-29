package rw.internals;
import java.util.*;

public class Meals implements Comparable<Meals> {

    private String name;

    private int calories;

    private int protein;

    private int carbohydrates;

    private int fats;

    private AttributeToCompare currentComparisonType;

    /**
     * Takes in user input as an array and then constructs the object using indexing of the array
     * @param meal_array - this is the user input as a string, that is converted into a String[] for easier construction
     *                   of the object
     */
    public Meals(String[] meal_array){
        this.name = meal_array[0];
        this.calories = Integer.parseInt(meal_array[1]);
        this.protein = Integer.parseInt(meal_array[2]);
        this.carbohydrates = Integer.parseInt(meal_array[3]);
        this.fats = Integer.parseInt(meal_array[4]);
    }

    /**
     * Takes the object that the method was invoked on and adds it to the current a dailyLog
     * @param TodayLog - this is a daily log object and will always be the current dailyLog, hence the name TodayLog
     */
    public void addMealToLog(DailyLogs TodayLog){

        //Grab the daily log of today, access its contents and then add the meal to it
        TodayLog.getMealsInLog().add(this);
    }


    /**
     * Rewrites the printing of a meal from hashcode to actually readable for the user, its really just a meal printing
     * helper function, easy to create using Strings
     * @return - String that is readable to the user, prints out meaningful interpretation of a meal
     */
    @Override
    public String toString() {

        // Now we can return the mealDetails string
        return "Name - " + this.getName() + "\n" +
                "\tCalories = " + this.getCalories() + "\n" +
                "\tProtein = " + this.getProtein() + "\n" +
                "\tCarbs = " + this.getCarbohydrates() + "\n" +
                "\tFats = " + this.getFats() + "\n";


    }

    /**
     * This override has its main use in the options of get highest protein and healthiest and unhealthiest meals, note
     * that a precondition of this statement is that the user sets up data
     * @param otherMeal - the other meal we are comparing with this one
     * @return - returns the result of a comparison, PROTEIN results in the highest protein, CALORIES_AND_FATS_HEALTHY
     * results in the meal with the lowest average of fat and calories, CALORIES_AND_FATS_UNHEALTHY results in the meal
     * with the highest average of fat and calories
     */
    @Override
    public int compareTo(Meals otherMeal) {

        return switch (this.currentComparisonType) {
            case PROTEIN -> Math.max(this.getProtein(), otherMeal.getProtein());
            case CALORIES_AND_FATS_HEALTHY ->
                    Math.min((this.getCalories() + this.getFats()) / 2, (otherMeal.getCalories() + otherMeal.getFats()) / 2);
            case CALORIES_AND_FATS_UNHEALTHY ->
                    Math.max((this.getCalories() + this.getFats()) / 2, (otherMeal.getCalories() + otherMeal.getFats()) / 2);
        };
    }

    //ALL GETTERS AND SETTERS - They are self-explanatory through their names
    String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public int getProtein() {
        return protein;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public int getFats() {
        return fats;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public void setCurrentComparisonType(AttributeToCompare currentComparisonType) {
        this.currentComparisonType = currentComparisonType;
    }

}