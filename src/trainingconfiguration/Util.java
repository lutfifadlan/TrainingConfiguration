package trainingconfiguration;

/**
 * Created by fahziar on 02/12/2015.
 */
public class Util {
    public static int calculateBMI(double Height, double Weight){
        return (int) Math.floor(Weight / (Height * Height));
    }
}
