import java.util.List;
import java.util.HashMap;

public class JSONFormattter {

        public String SingleFormat(Patient patient){

            return(CreateFormat(patient));

        }

        public String AllFormat(List<Patient> Records){
            StringBuilder sb = new StringBuilder();
            sb.append("{\"patients\": [");
            sb.append(System.getProperty("line.separator"));
            for (Patient patient: Records){
                String record = CreateFormat(patient);
                sb.append(record);
                sb.append(",");
                sb.append(System.getProperty("line.separator"));
            }
            sb.deleteCharAt(sb.length() - 3); //Delete the final comma
            sb.append("] }");

            return(sb.toString());
        }



        private String CreateFormat(Patient patient) {
            HashMap<String, String> Record = patient.getRecord();
            String[] keys = {"ID", "BIRTHDATE", "DEATHDATE", "SSN", "DRIVERS", "PASSPORT", "PREFIX", "FIRST", "LAST", "SUFFIX", "MAIDEN", "MARITAL", "RACE", "ETHNICITY", "GENDER", "BIRTHPLACE", "ADDRESS", "CITY", "STATE"};
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            for (String key : keys) {
                String val = Record.get(key);
                sb.append("\"" + key + "\": \"" + val + "\",");
                sb.append(System.getProperty("line.separator"));
            }

            String val = Record.get("ZIP");
            sb.append("\"ZIP\": \""  + val + "\""); //Do Separately because ZIP is the only field that is of type number.
            sb.append("}");

            return(sb.toString());
        }


}
