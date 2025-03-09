package frc.robot.util;

public class Util {
    
    /**
     * @param value Input value.
     * @param min All inputs less than this are rounded down to 0.
     * @return 0 if value is less than min, else value
     */
    public static double applyDeadband(double value, double min) {
        return (Math.abs(value) < min) ? 0 : value;
    }

}
