/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trainingconfiguration;

import edu.stanford.smi.protege.exception.OntologyLoadException;
import edu.stanford.smi.protegex.owl.inference.reasoner.exception.ProtegeReasonerException;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *
 * @author Mochamad Lutfi F
 */
public class TrainingConfiguration {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws OntologyLoadException, FileNotFoundException, ProtegeReasonerException {
        // TODO code application logic here
        LogManager.getLogManager().reset();
        JFrame jf = new JFrame();
        jf.setVisible(true);
       // Map<String, Integer> result = Util.configure(22, Fitness.KeaktifanOlahraga.aktif3Bulan, Fitness.Tujuan.meningkatkanStamina, Fitness.Lokasi.gym, Fitness.Penyakit.tidakAda, Fitness.Gender.pria, 80);
        
//System.out.println(result);
        System.out.println("b");
        
    }
    
}
