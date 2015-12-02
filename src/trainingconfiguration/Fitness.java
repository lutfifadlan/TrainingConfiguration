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
import edu.stanford.smi.protegex.owl.model.OWLDatatypeProperty;
import edu.stanford.smi.protegex.owl.model.OWLIndividual;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.model.OWLObjectProperty;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
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
    private Map<String, OWLDatatypeProperty> datatypeProperties = new HashMap<>();
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
        "atLocation", "hasDisease", "hasGoal", "isActive", "isGender", "isLevel", "isType"
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

    private static final String[] DatatypeProperties = {
        "BMI"
    };

    public enum KeaktifanOlahraga {
        tidakAktif,
        aktif3Bulan,
        aktif6Bulan
    }

    public enum Tujuan {
        meningkatkanStamina,
        menjagaKebugaran,
        menurunkanBeratBadan,
        memperbesarOtot
    }

    public enum Lokasi {
        outdoor,
        gym,
        rumah
    }

    public enum Penyakit {
        tidakAda,
        paru,
        punggung,
        jantung
    }

    public enum Gender{
        pria,
        wanita
    }

    public Fitness() throws ProtegeReasonerException{
        try{
            System.out.println("a");
            owlModel = ProtegeOWL.createJenaOWLModelFromInputStream(new FileInputStream(OWL));
            System.out.println("b");
            inputClasses();
            inputProperties();
            inputIndividuals();
            inputDatatypeProperties();
        }
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

    public void inputDatatypeProperties(){
        for(String i : DatatypeProperties){
            datatypeProperties.put(i, (OWLDatatypeProperty) owlModel.getOWLDatatypeProperty(IRI + i));
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
    
    public List<String> testConclude(int BMI, KeaktifanOlahraga keaktifan, Tujuan tujuan, Lokasi lokasi, Penyakit penyakit, Gender gender) throws ProtegeReasonerException{
        String namaKelas = "Training";
        ProtegeReasoner reasoner = CreateOWLAPIReasoner();
        OWLNamedClass kelas = Class.get(("Training"));
        OWLIndividual kls = kelas.createOWLIndividual("Oji");

        System.out.println("Melakukan inferensi dari" + namaKelas);
        switch (keaktifan){
            case tidakAktif:
                kls.addPropertyValue(Property.get("isActive"), Individual.get("Tidak_Aktif_Dalam_3_Bulan_Terakhir"));
                break;
            case aktif3Bulan:
                kls.addPropertyValue(Property.get("isActive"), Individual.get("Aktif_Dalam_3_Bulan_Terakhir"));
                break;
            case aktif6Bulan:
                kls.addPropertyValue(Property.get("isActive"), Individual.get("Aktif_Dalam_6_Bulan_Terakhir"));
                break;
        }

        switch (lokasi){
            case rumah:
                kls.addPropertyValue(Property.get("atLocation"), Individual.get("Rumah"));
                break;
            case outdoor:
                kls.addPropertyValue(Property.get("atLocation"), Individual.get("Luar_Ruangan"));
                break;
            case gym:
                kls.addPropertyValue(Property.get("atLocation"), Individual.get("Gym"));
                break;
        }

        switch (tujuan){
            case meningkatkanStamina:
                kls.addPropertyValue(Property.get("hasGoal"), Individual.get("Meningkatkan_Stamina"));
                break;
            case menurunkanBeratBadan:
                kls.addPropertyValue(Property.get("hasGoal"), Individual.get("Menurunkan_Berat_Badan"));
                break;
            case menjagaKebugaran:
                kls.addPropertyValue(Property.get("hasGoal"), Individual.get("Menjaga_Kebugaran"));
                break;
            case memperbesarOtot:
                kls.addPropertyValue(Property.get("hasGoal"), Individual.get("Memperbesar_Otot"));
                break;
        }

        switch (penyakit){
            case tidakAda:
                kls.addPropertyValue(Property.get("hasDisease"), Individual.get("Tidak_Ada_Penyakit"));
                break;
            case jantung:
                kls.addPropertyValue(Property.get("hasDisease"), Individual.get("Penyakit_Jantung"));
                break;
            case paru:
                kls.addPropertyValue(Property.get("hasDisease"), Individual.get("Penyakit_Paru_Paru"));
                break;
            case punggung:
                kls.addPropertyValue(Property.get("hasDisease"), Individual.get("Penyakit_Punggung"));
                break;
        }

        switch (gender){
            case pria:
                kls.addPropertyValue(Property.get("isGender"), Individual.get("Laki-Laki"));
                break;
            case wanita:
                kls.addPropertyValue(Property.get("isGender"), Individual.get("Perempuan"));
                break;
        }


        kls.setPropertyValue(datatypeProperties.get("BMI"), BMI);

        System.out.println("isActive " + kls.getPropertyValue(Property.get("isActive")));
        System.out.println("atLocation " + kls.getPropertyValue(Property.get("atLocation")));
        System.out.println("hasDisease " + kls.getPropertyValue(Property.get("hasDisease")));
        System.out.println("isGender " + kls.getPropertyValue(Property.get("isGender")));
        System.out.println("hasGoal " + kls.getPropertyValue(Property.get("hasGoal")));

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