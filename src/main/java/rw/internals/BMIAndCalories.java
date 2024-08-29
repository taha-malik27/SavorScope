//allowed to keep this static because this does not use data and simply uses user_input, I asked Prof Jonathan, and he
//allowed for this because it doesn't actually use the data, its external from that as an accessory
package rw.internals;
public class BMIAndCalories {

    /**
     * Calculates user's recommended calories - This method simply takes in the following
     * as user input, weight, height, age, biological gender/sex , and activity level. Valid input has already been checked for, so we
     * can simply plug it in to a formula that was found on a website, citation of that will be in menu.java.
     */
    public static double userCaloriesCalculator(double weight, double height, double age, String gender, String activityLevel) {
        double bmr;
        if (gender.equals("M") || gender.equals("m")) {
            bmr = 10 * weight + 6.25 * height - 5 * age + 5;
        }
        //if not male it must be female since we already check for invalid inputs
        else {
            bmr = 10 * weight + 6.25 * height - 5 * age - 161;

        }
        return switch (activityLevel) {
            case "Not active" -> bmr * 1.2; //these formulas come off of the cited website at the end of the file
            case "Lightly active" -> bmr * 1.375;
            case "Moderately active" -> bmr * 1.55;
            case "Very active" -> bmr * 1.725;
            case "Extremely active" -> bmr * 1.9;
            default -> 0; //this default should never be reached because invalid inputs are already accounted for
        };

    }

    /**
     * This function performs a simple calculation after retrieving all valid user input in menu.java to get BMI
     * @param weight - Gets use weight in KG
     * @param height - Gets user height in Meters
     *
     * @return Gives back BMI of the user as a double
     */
    public static double BMICalculator(String weight, String height) {
        double num_weight = Double.parseDouble(weight);
        double num_height = Double.parseDouble(height);

        return num_weight / (num_height * num_height); //this is body mass index (BMI)
    }


}
