package trainingconfiguration;

import org.openjena.atlas.iterator.Iter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by fahziar on 02/12/2015.
 */
public class Util {
    public static int calculateBMI(double Height, double Weight){
        return (int) Math.floor(Weight / (Height * Height));
    }

    public static Map<String, Integer> configure(int BMI, Fitness.KeaktifanOlahraga keaktifan, Fitness.Tujuan tujuan, Fitness.Lokasi lokasi, Fitness.Penyakit penyakit, Fitness.Gender gender, int availableTime){
        HashMap<String, Integer> map = new HashMap<>();
        try {
            Fitness fit = new Fitness();
            fit.getCollection();
            fit.CreateOWLAPIReasoner();
            Map <String, Integer> result = fit.testConclude(BMI, keaktifan, tujuan, lokasi, penyakit, gender);
            System.out.println("blay");

            int time = availableTime;


            for(Iterator<Map.Entry<String, Integer>> it = result.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, Integer> entry = it.next();
                if((entry.getValue() == 120) && (time >= 120)) {
                    time -= 120;
                    map.put(entry.getKey(), 120);
                    it.remove();
                }
            }

            for(Iterator<Map.Entry<String, Integer>> it = result.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, Integer> entry = it.next();
                if((entry.getValue() == 60) && (time >= 60)) {
                    time -= 60;
                    map.put(entry.getKey(), 60);
                    it.remove();
                }
            }

            for(Iterator<Map.Entry<String, Integer>> it = result.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, Integer> entry = it.next();
                if((entry.getValue() == 40) && (time >= 40)) {
                    time -= 40;
                    map.put(entry.getKey(), 40);
                    it.remove();
                }
            }

            for(Iterator<Map.Entry<String, Integer>> it = result.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, Integer> entry = it.next();
                if ((entry.getValue() == 20) && (time >= 20)) {
                    time -= 10;
                    map.put(entry.getKey(), 20);
                    it.remove();
                }
            }

            for(Iterator<Map.Entry<String, Integer>> it = result.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, Integer> entry = it.next();
                if((entry.getValue() >= 10) && (entry.getValue() < 20) && (time >= 10)) {
                    time -= 10;
                    map.put(entry.getKey(), 10);
                    it.remove();
                }
            }


        } catch (Exception e){

        }

        return map;
    }
}
