/**
 * This class enum is used when I try to compare meals with each other, such as highest protein, healthiest and unhealthiest meals
 * The state of this essentially determines what the meal will compare to, so we have to change its state when we want to compare
 * different things
 */
package rw.internals;
public enum AttributeToCompare {
    PROTEIN, //used when we need the highest protein meal
    CALORIES_AND_FATS_HEALTHY,//used when we need the healthiest meal
    CALORIES_AND_FATS_UNHEALTHY//used when we need the unhealthiest meal
}
