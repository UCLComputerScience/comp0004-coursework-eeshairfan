import java.util.List;
import java.util.ArrayList;

import java.io.*;

public class ReadCSV {

    public List<Patient> ReadFile(String filename) throws IOException{
        return(Read(filename));
    }

    private List<Patient> Read(String filename) throws IOException{
        List<Patient> Patients = new ArrayList<>();
        File file = new File(filename);
        if (file.exists()){

                FileReader fr = new FileReader(filename);
                BufferedReader br = new BufferedReader(fr);
                br.readLine(); //Ignore first line in file as it is generic
                String input;
                while ((input = br.readLine()) != null){
                    Patients.add(new Patient(input));
                }
            }
        else  {
            System.out.println("File not found");
        }


        return Patients;

    }



}

