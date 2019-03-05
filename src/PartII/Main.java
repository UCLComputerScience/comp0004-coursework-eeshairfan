import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Main {

    public void go (){
        ReadCSV reader = new ReadCSV();
        JSONFormattter format = new JSONFormattter();
        try {
            List<Patient> records = new ArrayList<>();
            records = reader.ReadFile("C:\\Users\\eesha\\Documents\\Coursework\\JavaII\\src\\PartII\\patients100.csv");
            HashMap<String, String> record = records.get(1).getRecord();
            System.out.println(record.get("CITY"));
            records.get(1).setRecord("CITY", "Rome");
            record = records.get(1).getRecord();
            System.out.println(record.get("CITY"));
            //System.out.println(format.SingleFormat(records.get(28)));
            System.out.println(format.AllFormat(records));
        }

        catch (Exception e){
            System.out.println("Error: " + e);
        }
    }

    public static void main(String[] args)
    {
        new Main().go();
    }
}
