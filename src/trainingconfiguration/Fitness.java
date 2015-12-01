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
import edu.stanford.smi.protegex.owl.inference.reasoner.exception.ProtegeReasonerException;
import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;
import edu.stanford.smi.protegex.owl.model.OWLIndividual;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.model.OWLObjectProperty;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mochamad Lutfi F
 */
public class Fitness {
    private Fitness DataCollection;
    private Map<String, OWLNamedClass> Class = new HashMap<>();
    private Map<String, OWLObjectProperty> Property = new HashMap<>();
    private Map<String, OWLIndividual> Individual = new HashMap<>();
    private JenaOWLModel owlModel;
    private static final String IRI = "http://www.semanticweb.org/fahziar/ontologies/2015/10/fitness#";
    private static final String OWL = "transportation.owl";
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
    
    public Fitness() throws ProtegeReasonerException{
        try{
        System.out.println("a");
        owlModel = ProtegeOWL.createJenaOWLModelFromInputStream(new FileInputStream(OWL));
        System.out.println("b");
        inputClasses();
        inputProperties();
        inputIndividuals();}
        catch (FileNotFoundException | OntologyLoadException e){
            System.out.println("ini");
            System.out.println(e);
        }
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
    
    public Fitness getCollection() throws OntologyLoadException, FileNotFoundException, ProtegeReasonerException{
        if(DataCollection == null){
            DataCollection = new Fitness();
        }
        return DataCollection;
    }
    
    public ProtegeReasoner CreateOWLAPIReasoner(){
        ReasonerManager reasonerManager = ReasonerManager.getInstance();
        ProtegeReasoner reasoner = reasonerManager.createProtegeReasoner(owlModel, ProtegePelletOWLAPIReasoner.class);
        return reasoner;
    }
    
    public List<String> conclude(String namaKelas, Map<String, String> rule){
        ProtegeReasoner reasnoer = CreateOWLAPIReasoner();
        OWLNamedClass kelas = Class.get(("Training"));
        OWLIndividual kls = kelas.createOWLIndividual(namaKelas);
        System.out.println("Melakukan inferensi " + namaKelas);
        
        for(Map.Entry<String, String> entry : rule.entrySet()){
            String key, value;
            key = entry.getKey();
            value = entry.getValue();
            if(!value.equals("NULL")){
                kls.addPropertyValue(Property.get(key), Individual.get(value));
            }
        }
        
        Collection inferredSuperClasses = null;
        List<String> tipes = new ArrayList<>();
        for(Iterator it = inferredSuperClasses.iterator(); it.hasNext();){
            OWLNamedClass curClass = (OWLNamedClass) it.next();
            String class_name = curClass.getName().contains("Thing") ? "- Thing" : curClass.getName().replaceAll(IRI, "- ");
            tipes.add(class_name);
        }
        return tipes;
    }
    
    public List<String> testConclude() throws ProtegeReasonerException{
        String namaKelas = "Latihan";
        ProtegeReasoner reasoner = CreateOWLAPIReasoner();
        OWLNamedClass kelas = Class.get(("Training"));
        OWLIndividual kls = kelas.createOWLIndividual(namaKelas);
        System.out.println("Melakukan inferensi dari" + namaKelas);
        kls.addPropertyValue(Property.get("hasTime"), Individual.get("Sebentar"));
        kls.addPropertyValue(Property.get("hasBeban"), Individual.get("Ringan"));
        Collection inferredSuperClasses;
        List<String> tipes = new ArrayList<>();
        inferredSuperClasses = reasoner.getIndividualTypes(kls);
        for(Iterator it = inferredSuperClasses.iterator(); it.hasNext();){
            OWLNamedClass curClass = (OWLNamedClass) it.next();
            String className = curClass.getName().contains("Thing") ? "- Thing" : curClass.getName().replaceAll(IRI, "- ");
            tipes.add(className);
        }
        return tipes;
    }
}