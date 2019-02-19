package uk.ac.ucl.bag;

/**
 * This class implements methods common to all concrete bag implementations
 * but does not represent a complete bag implementation.<br />
 *
 * New bag objects are created using a BagFactory, which can be configured in the application
 * setup to select which bag implementation is to be used.
 */
import java.util.Iterator;

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

      Bag<T> result = BagFactory.getInstance().getBag();
      for (T value : this)
      {
          result.addWithOccurrences(value, this.countOf(value));
      }
      for (T value : bag)
      {
          if (result.contains(value)){
              result.remove(value);
              if (result.countOf(value) == 0){


              }
          }

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


