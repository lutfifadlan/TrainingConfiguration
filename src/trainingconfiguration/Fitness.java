/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trainingconfiguration;

import edu.stanford.smi.protege.exception.OntologyLoadException;
import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.inference.pellet.ProtegePelletOWLAPIReasoner;
import edu.stanford.smi.protegex.owl.inference.protegeowl.ReasonerManager;
import edu.stanford.smi.protegex.owl.inference.reasoner.ProtegeReasoner;
import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;
import edu.stanford.smi.protegex.owl.model.OWLIndividual;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.model.OWLObjectProperty;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Mochamad Lutfi F
 */
public class Fitness {
   // 50000-14000 = 36000-13500 = 22500-15500 = 7000
    private Fitness Collection;
    private Map<String, OWLNamedClass> Class = new HashMap<>();
    private Map<String, OWLObjectProperty> Property = new HashMap<>();
    private Map<String, OWLIndividual> Individual = new HashMap<>();
    private JenaOWLModel owlModel;
    private static final String IRI = "http://www.semanticweb.org/fahziar/ontologies/2015/10/fitness#";
    private static final String OWL = "Fitness.owl";
    private static final String[] Classes = {
        "Training", "Intensitas"
    };
    private static final String[] Properties = {
        "hasTime", "hasBeban"
    };
    private static final String[] Individuals = {
        "Sebentar", "Lama", // Waktu
        "Ringan", "Menengah", "Berat" // Beban
    };
    
    public Fitness() throws OntologyLoadException, FileNotFoundException{
        owlModel = ProtegeOWL.createJenaOWLModelFromInputStream(new FileInputStream(OWL));
    }   
          
    public void inputClasses(){
        for(String c: Classes){
            Class.put(c, (OWLNamedClass) owlModel.getOWLNamedClass(IRI + c));
        }
    }
    
    public void inputProperties(){
        for(String p : Properties){
            Property.put(p, (OWLObjectProperty) owlModel.getOWLObjectProperty(IRI + p));
        }
    }
    
    public void inputIndividuals(){
        for(String i : Individuals){
            Individual.put(i, (OWLIndividual) owlModel.getOWLIndividual(IRI + i));
        }
    }
    
    public Fitness getCollection() throws OntologyLoadException, FileNotFoundException{
        if(Collection == null){
            Collection = new Fitness();
        }
        return Collection;
    }
    
    public ProtegeReasoner CreateOWLAPIReasoner(){
        ReasonerManager reasonerManager = ReasonerManager.getInstance();
        ProtegeReasoner reasoner = reasonerManager.createProtegeReasoner(owlModel, ProtegePelletOWLAPIReasoner.class);
        return reasoner;
    }
}