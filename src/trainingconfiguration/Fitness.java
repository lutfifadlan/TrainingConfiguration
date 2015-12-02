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
    private static final String IRI = "http://www.semanticweb.org/windyamelia/ontologies/2015/11/fitnessOntology#";
    private static final String OWL = "fitnessOntology.owl";
    private static final String[] Classes = {
        "Keaktifan",
        "Kondisi_Tubuh", "Jenis_Kelamin", "Penyakit",
        "Level", "Advance", "Beginner", "Intermediate",
        //Advance
        "Bench_Press", "Biceps_Curls", "Cable_Crossover", "Dumbbell_Files", "Dummbell_Shrugs",
        "Flat_Abs_Crunches", "Kickboxing", "Lower_Back_Machine", "Lying_Hamstring_Curls", "Machine_Squats",
        "Military_Press", "Pilates", "Seated_Calf_Presses", "Shadow_Boxing", "Tricep_Kickbacks",
        "Twisting_Abs_Machine",
        //Beginner
        "Ball_Workouts", "Trampolining", "Walking", "Wall_Sit",
        //Intermediate
        "Aqua_Aerobics", "Aqua_Jogging", "Cycling", "Elliptical_Machine", "Jumping_Rope",
        "Location",
        "Training", "Time_10", "Time_20", "Time_40", "Time 60", "Time 120",
        //Time_10
        "Cable_Abs_Crunches", "Deadlift", "Dumbbell_Lift", "Jumping_Jack", "Leg_Extension",
        "Plank", "Pull_Downs", "Pull_Ups", "Push_Ups", "Seated_Rows",
        "Sit_Ups", "Skipping", "Squat", "Stair_Stepper", "Trampolining", 
        "Tricep_Kickbacks", "Triceps_Pushdowns", "Tuck_Jump",
        //Time_20
        "Aerobic_Program", "Back_Ups", "Cross_Trainers", "Shadow_Boxing", "Treadmill",
        //Time_40
        "Baseball", "Biking", "Dance", "Jogging", "Kickboxing",
        "Running", "Voleyball",
        //Time_60
        "Basketball", "Pilates", "Rowing", "Swimming", "Tai_Chi", "Yoga",
        //Time_120
        "Hiking", "Soccer",
        "Tujuan", "Type",
        "Kurang_Berat_Badan", "Lebih_Berat_Badan", "Obesitas", "Sehat"
    };
    private static final String[] Properties = {
        "atLocation", "hasDisease", "hasGoal", "isGender", "isLevel", "isType"
    };
    private static final String[] Individuals = {
        //Keaktifan
        "Aktif_Dalam_3_Bulan_Terakhir", "Aktif_Dalam_6_Bulan_Terakhir", "Tidak_Aktif_Dalam_3_Bulan_Terakhir", 
        //Jenis_Kelamin
        "Laki-Laki", "Perempuan",
        //Penyakit
        "Penyakit_Jantung", "Penyakit_Paru_Paru", "Penyakit_Punggung", "Tidak_Ada_Penyakit",
        //Location
        "Gym", "Luar_Ruangan", "Rumah",
        //Tujuan
        "Memperbesar_Otot", "Meningkatkan_Stamina", "Menjaga_Kebugaran", "Menurunkan_Berat_Badan" 
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
        String namaKelas = "Penyakit";
        ProtegeReasoner reasoner = CreateOWLAPIReasoner();
        OWLNamedClass kelas = Class.get(("Training"));
        OWLIndividual kls = kelas.createOWLIndividual(namaKelas);
        System.out.println("Melakukan inferensi dari" + namaKelas);
        kls.addPropertyValue(Property.get("atLocation"), Individual.get("Rumah"));
        kls.addPropertyValue(Property.get("isGender"), Individual.get("Laki-Laki"));
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