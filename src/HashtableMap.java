// == CS400 Fall 2024 File Header Information ==
// Name: Vibhrav Jha
// Email: vjha3@wisc.edu
// Group: P2.3819
// Lecturer: Florian
// Notes to Grader: N/A
import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {

  protected class Pair {

    public KeyType key;
    public ValueType value;

    public Pair(KeyType key, ValueType value) {
      this.key = key;
      this.value = value;
    }

  }


  // initialize private variable array
  private LinkedList<Pair>[] table = null;
  private int countSize = 0;


  // CONSTRUCTORS FOR HASHTABLE MAP

  @SuppressWarnings("unchecked")
  public HashtableMap(int capacity) {
    // define table
    table = (LinkedList<Pair>[]) new LinkedList[capacity];
    // initialize each component of the array
    for (int i = 0; i < capacity; i++) {
      table[i] = new LinkedList<Pair>();
    }
  }


  @SuppressWarnings("unchecked")
  public HashtableMap() // with default capacity = 64
  {
    // define table
    table = (LinkedList<Pair>[]) new LinkedList[64];
    // initialize each component of the array
    for (int i = 0; i < 64; i++) {
      table[i] = new LinkedList<Pair>();
    }
  }

  // METHODS FROM MAPADT

  @SuppressWarnings("unchecked")
  /**
   * Adds a new key,value pair/mapping to this collection.
   *
   * @param key   the key of the key,value pair
   * @param value the value that key maps to
   * @throws IllegalArgumentException if key already maps to a value
   * @throws NullPointerException     if key is null
   */
  @Override
  public void put(KeyType key, ValueType value) throws IllegalArgumentException {
    // throws null exception based on the key
    if (key == null)
      throw new NullPointerException("Key is null");
      // throws argument exception based on the key
    else if (containsKey(key))
      throw new IllegalArgumentException("Key already maps to a value");
    else {
      // creates a pair object
      Pair toAdd = new Pair(key, value);
      // gets index based on the capacity
      int index = Math.abs(key.hashCode()) % getCapacity();
      if (table[index] == null)
        table[index] = new LinkedList<Pair>();
      table[index].add(toAdd);
      countSize++;

      // if the load factor is greater than 0.8, rehashes the table
      if ((double) countSize / (double) getCapacity() >= 0.8) {
        LinkedList<Pair>[] oldTable = table;
        table = (LinkedList<Pair>[]) new LinkedList[getCapacity() * 2];
        countSize = 0;
        // rehashes the old table in the new capacity
        for (int i = 0; i < oldTable.length; i++) {
          if (oldTable[i] != null) {
            for (Pair toCheck : oldTable[i]) {
              index = Math.abs(toCheck.key.hashCode()) % getCapacity();
              if (table[index] == null)
                table[index] = new LinkedList<Pair>();
              table[index].add(toCheck);
              countSize++;
            }
          }
        }
      }
    }
  }

  /**
   * Checks whether a key maps to a value in this collection.
   *
   * @param key the key to check
   * @return true if the key maps to a value, and false is the key doesn't map to a value
   */
  @Override
  public boolean containsKey(KeyType key) {
    try {
      // gets the element with the matching keytype
      ValueType contained = get(key);
      // if an element is found, then returns true
      if (contained != null)
        return true;
    } catch (NoSuchElementException e) {
      // no element found, returns false
      return false;
    }
    return false;
  }

  /**
   * Retrieves the specific value that a key maps to.
   *
   * @param key the key to look up
   * @return the value that key maps to
   * @throws NoSuchElementException when key is not stored in this collection
   */
  @Override
  public ValueType get(KeyType key) throws NoSuchElementException {
    // gets the benchmark index
    int index = Math.abs(key.hashCode()) % getCapacity();
    if (table[index] != null) {
      // checks for the matching key
      for (int i = 0; i < table[index].size(); i++) {
        if (table[index].get(i).key.equals(key))
          return table[index].get(i).value;
      }
    }

    for (int i = 0; i < table.length; i++) {
      if (table[i] != null) {
        for (int j = 0; j < table[i].size(); j++) {
          // gets the matching key and changes things accordingly
          if (table[i].get(j).key.equals(key)) {
            return table[i].get(j).value;
          }
        }
      }
    }

    // throws exception if an element isn't found and returned
    throw new NoSuchElementException("Key not stored in this collection");
  }

  /**
   * Remove the mapping for a key from this collection.
   *
   * @param key the key whose mapping to remove
   * @return the value that the removed key mapped to
   * @throws NoSuchElementException when key is not stored in this collection
   */
  @Override
  public ValueType remove(KeyType key) throws NoSuchElementException {
    // iterates through all the possible linked lists and pairs
    for (int i = 0; i < table.length; i++) {
      for (int j = 0; j < table[i].size(); j++) {
        // gets the matching key and changes things accordingly
        if (table[i].get(j).key.equals(key)) {
          ValueType temp = table[i].get(j).value;
          table[i].remove(j);
          countSize--;
          return temp;
        }
      }
    }
    // throws exception if an element isn't found and returned
    throw new NoSuchElementException("Key not stored in this collection");
  }

  /**
   * Removes all key,value pairs from this collection.
   */
  @Override
  public void clear() {
    // iterates through each linked list element
    for (int i = 0; i < table.length; i++) {
      // sets the entire list to null
      if (table[i] != null) {
        table[i] = null;
      }
    }
    countSize = 0;
  }

  /**
   * Retrieves the number of keys stored in this collection.
   *
   * @return the number of keys stored in this collection
   */
  @Override
  public int getSize() {
    return countSize;
  }

  /**
   * Retrieves this collection's capacity.
   *
   * @return the size of the underlying array for this collection
   */
  @Override
  public int getCapacity() {
    return table.length;
  }



  /**
   * Retrieves this collection's keys.
   *
   * @return a list of keys in the underlying array for this collection
   */

  public LinkedList<KeyType> getKeys() {
    LinkedList<KeyType> keys = new LinkedList<>();
    for (int i = 0; i < table.length; i++) {
      for (Pair pair : table[i]) {
        keys.add(pair.key);
      }
    }
    return keys;
  }
// TESTERS FOR HASHTABLE CLASS
  /**
   * Tests adding a new key, value pair/mapping to this collection. Also checks for when an Illegal
   * Argument Exception is thrown when duplicate values are attempted to be added.
   */
  @Test
  public void testForPairs() {
    // make pairs
    HashtableMap<String, String> tester = new HashtableMap<String, String>(4);
    String food = "Burger";
    String cuisine = "American";
    tester.put(food, cuisine);
    Assertions.assertEquals(true, tester.containsKey(food), "test FAILED");


    String doctor = "Doctor";
    String field = "General Surgeon";
    tester.put(doctor, field);
    Assertions.assertEquals(true, tester.containsKey(doctor), "test FAILED");

    String teacher = "Teacher";
    String grade = "Elementary";
    tester.put(teacher, grade);


    String design = "French";
    String type = "Gown";
    tester.put(design, type);

    // testing to make sure rehashing functions correctly
    Assertions.assertEquals(8, tester.getCapacity(), "test FAILED");
    Assertions.assertEquals(4, tester.getSize(), "test FAILED");

    // gets the exception thrown and then pulls the message for comparison
    Exception exceptionMethod = Assertions.assertThrows(IllegalArgumentException.class, () -> {
      tester.put(food, cuisine);
    });

    String expectedMessage = "Key already maps to a value";
    String actualMessage = exceptionMethod.getMessage();

    // tests the methods of the class, making sure the calculated values match properly
    Assertions.assertTrue(actualMessage.contains(expectedMessage), "test FAILED");

    // gets the exception thrown and then pulls the message for comparison
    Exception exceptionMethod2 = Assertions.assertThrows(NullPointerException.class, () -> {
      tester.put(null, null);
    });

    String expectedMessage2 = "Key is null";
    String actualMessage2 = exceptionMethod2.getMessage();


    // tests the methods of the class, making sure the calculated values match properly
    Assertions.assertTrue(actualMessage2.contains(expectedMessage2), "test FAILED");

  }

  /**
   * Tests the check for whether a key maps to a value in this collection. Should pass based on the
   * scenario for returning true or false.
   */
  @Test
  public void testForContains() {
    // make tester objects
    HashtableMap<String, String> tester = new HashtableMap<String, String>();
    String student = "Student";
    String major = "Computer Science";
    String doctor = "Doctor";
    tester.put(student, major);

    // tests the methods of the class, making sure the calculated values match properly
    boolean actual = tester.containsKey(student);

    Assertions.assertEquals(true, actual, "test FAILED");

    actual = tester.containsKey(doctor);

    Assertions.assertEquals(false, actual, "test FAILED");
  }


  /**
   * Tests the retrieval for the specific value that a key maps to. Makes sure the intended one is
   * returned by this method.
   */
  @Test
  public void testForGet() {
    // make tester objects
    HashtableMap<String, String> tester = new HashtableMap<String, String>();
    String student = "Student";
    String major = "Computer Science";
    tester.put(student, major);

    String doctor = "Doctor";
    String field = "General Surgeon";

    // tests the methods of the class, making sure the calculated values match properly
    String actual = tester.get(student);

    Assertions.assertEquals(major, actual, "test FAILED");

    // gets the exception thrown and then pulls the message for comparison
    Exception exceptionMethod = Assertions.assertThrows(NoSuchElementException.class, () -> {
      tester.get(doctor);
    });

    String expectedMessage = "Key not stored in this collection";
    String actualMessage = exceptionMethod.getMessage();

    // tests the methods of the class, making sure the calculated values match properly
    Assertions.assertTrue(actualMessage.contains(expectedMessage), "test FAILED");

  }

  /**
   * Tests the removal of the mapping for a key from this collection. Makes sure that the intended
   * removal key is returned by the removal method.
   */
  @Test
  public void testForRemove() {
    // make tester objects
    HashtableMap<String, String> tester = new HashtableMap<String, String>();
    String student = "Student";
    String major = "Computer Science";
    tester.put(student, major);

    String doctor = "Doctor";
    String field = "General Surgeon";
    tester.put(doctor, field);

    String teacher = "Teacher";

    // tests the methods of the class, making sure the calculated values match properly
    String actual = tester.remove(student);

    Assertions.assertEquals(major, actual, "test FAILED");

    // gets the exception thrown and then pulls the message for comparison
    Exception exceptionMethod = Assertions.assertThrows(NoSuchElementException.class, () -> {
      tester.remove(teacher);
    });

    String expectedMessage = "Key not stored in this collection";
    String actualMessage = exceptionMethod.getMessage();

    // tests the methods of the class, making sure the calculated values match properly
    Assertions.assertTrue(actualMessage.contains(expectedMessage), "test FAILED");
  }

  /**
   * Tests the removal of all key, value pairs from this collection. Makes sure the size is 0 after
   * the clear function.
   */
  @Test
  public void testForClear() {
    // make tester objects
    HashtableMap<String, String> tester = new HashtableMap<String, String>();
    String student = "Student";
    String major = "Computer Science";
    tester.put(student, major);

    String doctor = "Doctor";
    String field = "General Surgeon";
    tester.put(doctor, field);

    // tests the methods of the class, making sure the calculated values match properly
    tester.clear();
    int expected = 0;
    int actual = tester.getSize();

    Assertions.assertEquals(expected, actual, "test FAILED");
  }
}


