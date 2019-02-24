
package uk.ac.ucl.bag;

/**
 * This class implements methods common to all concrete bag implementations
 * but does not represent a complete bag implementation.<br />
 *
 * New bag objects are created using a BagFactory, which can be configured in the application
 * setup to select which bag implementation is to be used.
 */
import java.nio.file.Files;
import java.util.Comparator;
import java.util.Iterator;
import java.io.*;

public abstract class AbstractBag<T extends Comparable> implements Bag<T>
{
  public Bag<T> createMergedAllOccurrences(Bag<T> b) throws BagException {
    Bag<T> result = BagFactory.getInstance().getBag();
    for (T value : this)
    {
      result.addWithOccurrences(value, this.countOf(value));
    }
    for (T value : b)
    {
      result.addWithOccurrences(value, b.countOf(value));
    }
    return result;
  }

  public Bag<T> createMergedAllUnique(Bag<T> b) throws BagException {
    Bag<T> result = BagFactory.getInstance().getBag();
    for (T value : this)
    {
      if (!result.contains(value)) result.add(value);
    }
    for (T value : b)
    {
      if (!result.contains(value)) result.add(value);
    }
    return result;
  }

  public void removeAllCopies() throws BagException {
      Bag<T> result = BagFactory.getInstance().getBag();
      for (T value: this) {
          if(this.countOf(value) > 1){
              int count = this.countOf(value) - 1;
              for(int i = 0; i < count; i++){
                  this.remove(value);
              }
          }
      }

  }

  public Bag<T> subtract(Bag<T> bag) throws BagException {
      /*
      returns a new Bag containing all
      values and occurrences that occur in the this bag but not the argument bag.
      */

      Bag<T> intermediate_result = BagFactory.getInstance().getBag();
      Bag<T> result = BagFactory.getInstance().getBag();

      for (T value : this)
      {
          intermediate_result.addWithOccurrences(value, this.countOf(value));
      }
      for (T value : bag)
      {
          intermediate_result.remove(value);
      }
      for (T value: intermediate_result){
          if (intermediate_result.countOf(value) != 0){
              result.addWithOccurrences(value, intermediate_result.countOf(value));
          }
      }
      return result;
  }

  public void saveFile(String name) throws BagException {
      try {
          String filename = name + ".txt";
          File bagFile = new File(filename);
          if (bagFile.exists()) {
              bagFile.delete(); //Need to have empty file
          }
          bagFile.createNewFile();
          FileWriter fw = new FileWriter(filename);
          PrintWriter pw = new PrintWriter(filename);
          String input = this.toString();
          input = input.substring(1,input.length() - 1);
          input = input.replaceAll(" ", "");
          String[] inputArray = input.split(",");
          for(String item: inputArray){
              pw.println(item);
          }
          pw.close();
      }

      catch(Exception e){
        System.out.println("Error: " + e);
      }
  }



  public Bag<T> createBag(String name) throws BagException {
      Bag<T> result = BagFactory.getInstance().getBag();
      String filename = name + ".txt";
      try {
          File bagFile = new File(filename);
          if (bagFile.exists()){
              FileReader fr = new FileReader(filename);
              BufferedReader br = new BufferedReader(fr);
              String input;
              while ((input = br.readLine()) != null) {
                  String[] bagInput = input.split(":");
                  T value = (T) bagInput[0];
                  int count = Integer.parseInt(bagInput[1]);
                  result.addWithOccurrences(value, count);
              }

          }

      }

      catch (Exception e){
          System.out.println("Error: " + e);
      }
      return result;
  }


  @Override
  public String toString() {
      String strRep = "";


      boolean first = true;

      strRep = "[";
      for (T value : this)
      {
          if (!first) { strRep = strRep + ", "; }
          first = false;
          strRep = strRep + value + ": " + this.countOf(value);
      }
      strRep = strRep + "]";


      return strRep;

  }

}


