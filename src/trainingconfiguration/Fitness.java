/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trainingconfiguration;

import edu.stanford.smi.protege.exception.OntologyLoadException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Mochamad Lutfi F
 */
public class Fitness {
   // 50000-14000 = 36000-13500 = 22500-15500 = 7000
    public Map<String, OWLNamedClass> Class = new HashMap<>();
    public Map<String, OWLObjectProperty> Property = new HashMap<>();
    public Map<String, OWLIndividual> Individual = new HashMap<>();
    
}
