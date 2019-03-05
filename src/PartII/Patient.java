import java.util.HashMap;

public class Patient{


    private HashMap<String, String> patient;
    private String[] keys = {"ID", "BIRTHDATE", "DEATHDATE", "SSN", "DRIVERS", "PASSPORT", "PREFIX", "FIRST", "LAST", "SUFFIX", "MAIDEN", "MARITAL", "RACE", "ETHNICITY", "GENDER", "BIRTHPLACE", "ADDRESS", "CITY", "STATE"};

    Patient(String patientRecord){
        String[] Record = patientRecord.split(",");
        patient = new HashMap<String, String>();
        int count = 0;

        for (String key: this.keys){ //Inserts all but ZIP code
            this.patient.put(key, Record[count]);
            count++;
        }
        if (Record.length == 19) { //Deals with no ZIP code
            this.patient.put("ZIP", "");
        }
        else{
            this.patient.put("ZIP", Record[19]);
        }
    }

    public HashMap<String, String> getRecord(){
        return(patient);
   }

   public void setRecord(String Key, String Value){
        patient.replace(Key, Value);
   }



}
