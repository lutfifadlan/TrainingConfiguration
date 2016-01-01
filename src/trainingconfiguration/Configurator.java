package trainingconfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fahziar on 02/12/2015.
 */
public class Configurator {
    public static List<Integer> createConfig(int minuteAvailable){
        List <Integer> out = new ArrayList<>();

        //120 menit
        while (minuteAvailable > 120){
            out.add(120);
            minuteAvailable -= 120;
        }

        //60 menit



        return out;
    }
}
