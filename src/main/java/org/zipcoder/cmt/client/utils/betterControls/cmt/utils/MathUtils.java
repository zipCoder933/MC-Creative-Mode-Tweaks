package org.zipcoder.cmt.client.utils.betterControls.cmt.utils;

public class MathUtils {

    public static double clamp(double value, double min, double max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }
}
