/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trainingconfiguration;

import edu.stanford.smi.protege.exception.OntologyLoadException;
import edu.stanford.smi.protegex.owl.inference.reasoner.exception.ProtegeReasonerException;
import java.io.FileNotFoundException;

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
        JFrame jf = new JFrame();
      //  jf.setVisible(true);
        Fitness fit = new Fitness();
        fit.getCollection();
        fit.CreateOWLAPIReasoner();
        fit.testConclude();
        System.out.println("blay");
        
    }
    
}
