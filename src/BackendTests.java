import java.io.IOException;
import org.junit.*;
 import org.junit.jupiter.api.Assertions;
 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains tests for the Backend implementation that
 * interacts with the Graph_Placeholder.
 * Each test method corresponds to a specific method from the BackendInterface.
 */


public class BackendTests {
  private Backend backend;
  private GraphADT<String, Double> graph;

  @BeforeEach
  public void setup() {
    // Create the graph
    graph = new Graph_Placeholder();
    // Assign the new Backend instance to the class field
    backend = new Backend(graph);
  }

  /**
   * Tests the loadGraphData() method.
   * Verifies that the graph data is successfully loaded from a dot file.
   *
   * @throws IOException if the graph data fails to load.
   */
  @Test
  public void roleTest1() {
    try {
      backend.loadGraphData("campus.dot");
    } catch (IOException e) { // if catch block runs tests fail
      fail("Failed to load graph data: " + e.getMessage());
    }
  }

  /**
   * Tests the findLocationsOnShortestPath method.
   * Verifies that the shortest path between two locations is correctly computed.
   * Compares the actual path with the expected list of locations.
   */

  @Test
  public void roleTest2(){
    try {
      backend.loadGraphData("campus.dot");
    } catch (IOException e) {
      fail("Failed to load graph data: " + e.getMessage());
    }
    // checks if the functionality works as expected by comparing results
    List<String> actual =
        backend.findLocationsOnShortestPath("Union South","Atmospheric, Oceanic and Space Sciences");


    List<String> expected = Arrays.asList("Union South", "Computer Sciences and Statistics",
        "Atmospheric, Oceanic and Space Sciences");
    Assertions.assertEquals(expected, actual, "test FAILED");
  }

  /**
   * Tests the getListOfAllLocations method.
   * Verifies that all locations in the graph are listed.
   * Checks if specific key locations are present in the result.
   */

  @Test
  public void roleTest3(){
    try {
      backend.loadGraphData("campus.dot");
    } catch (IOException e) {
      fail("Failed to load graph data: " + e.getMessage());
    }
    List<String> actual =
        backend.getListOfAllLocations();

    assertTrue(actual.contains("Union South"));
    assertTrue(actual.contains("Atmospheric, Oceanic and Space Sciences"));
    assertTrue(actual.contains("Computer Sciences and Statistics"));


  }


  /**
   * Tests the findTimesOnShortestPath method.
   * Verifies that the walking times between locations along the shortest path
   * are correctly calculated.
   * Compares the actual walking times with the expected values.
   */

  @Test
  public void roleTest4(){
    try {
      backend.loadGraphData("campus.dot");
    } catch (IOException e) {
      // Use fail() instead of just printing the error
      fail("Failed to load graph data: " + e.getMessage());
    }
    {
      List<Double> actual =
          backend.findTimesOnShortestPath("Union South","Computer Sciences and Statistics");
      List<Double> expected= new ArrayList<>();
      expected.add(1.0);
      //expected.add(2.0);
      System.out.println(expected);
      Assertions.assertEquals(expected, actual);
    }
    {
      List<Double> actual =
          backend.findTimesOnShortestPath("Union South","Atmospheric, Oceanic and Space Sciences");
      List<Double> expected= new ArrayList<>();
      expected.add(1.0);
      expected.add(2.0);
      System.out.println(expected);
      Assertions.assertEquals(expected, actual);
    }
  }


  /**
   * Tests the getClosestDestinationFromAll method.
   * Verifies that the closest destination is correctly identified based on
   * the list of starting locations.
   * Compares the actual result with the expected destination.
   */

  @Test
  public void roleTest5(){
    try {
      backend.loadGraphData("campus.dot");
    } catch (IOException e) {
      fail("Failed to load graph data: " + e.getMessage());
    }
    //results are based on placeholders currently and edge wait given
    {
      List<String> startLocation = new ArrayList<>() ;
      startLocation.add("Union South");
      String actual = backend.getClosestDestinationFromAll(startLocation);
      String expected = "Computer Sciences and Statistics";
      Assertions.assertEquals(expected, actual);
    }
    {
      List<String> startLocation = new ArrayList<>() ;
      startLocation.add("Computer Sciences and Statistics");
      String actual = backend.getClosestDestinationFromAll(startLocation);
      String expected = "Atmospheric, Oceanic and Space Sciences";
      Assertions.assertEquals(expected, actual);
    }
    {
      List<String> startLocation = new ArrayList<>() ;
      startLocation.add("Atmospheric, Oceanic and Space Sciences");
      String actual = backend.getClosestDestinationFromAll(startLocation);
      String expected ="Union South" ;
      Assertions.assertEquals(expected, actual);
    }
  }


    /**
     * Integration test for verifying that the backend can correctly load graph data
     * from a DOT file and retrieve the list of all locations.
     */
    @Test
    public void testLoadGraphIntegration() {
      Backend backend = new Backend(new DijkstraGraph<>()); // Use DijkstraGraph implementation
      try {
        // Load the graph data from a valid DOT file
        backend.loadGraphData("campus.dot");

        // Retrieve all locations
        List<String> locations = backend.getListOfAllLocations();

        // Assertions to verify locations
        assertTrue(locations != null, "The list of locations cannot be null.");
        assertFalse(locations.isEmpty(), "The list of locations cannot be empty.");
        assertTrue(locations.contains("Memorial Union"));
        assertTrue(locations.contains("Union South"));
      } catch (IOException e) {
        fail("IOException is not to be thrown" + e.getMessage());
      }
    }


    /**
     * Integration test for verifying shortest path calculations using the integrated backend and graph.
     */
    @Test
    public void testShortestPathIntegration() {
      Backend backend = new Backend(new DijkstraGraph<>()); // Use DijkstraGraph implementation
      try {
        backend.loadGraphData("campus.dot");

        // Test shortest path between two locations
        List<String> path = backend.findLocationsOnShortestPath("Memorial Union", "Mack House");

        // Assertions to verify the path
        assertTrue(path != null);
        assertFalse(path.isEmpty());
        assertEquals("Memorial Union", path.get(0));
        assertEquals("Mack House", path.get(path.size() - 1));
            assertTrue(path.size()==13);

      } catch (IOException e) {
        fail("IOException is not to be thrown"  + e.getMessage());
      }
    }
  /**
   * Integration test for verifying shortest path time calculations using the  backend
   * and graph.
   */
  @Test
  public void testTimeIntegration() {
    Backend backend = new Backend(new DijkstraGraph<>()); // Use DijkstraGraph implementation
    try {
      backend.loadGraphData("campus.dot");
      List<Double> Time = backend.findTimesOnShortestPath("Memorial Union","Science Hall");
      assertTrue(Time != null);
      assertTrue(Time.get(0)==105.8);
    } catch (IOException e) {
      fail("IOException is not to be thrown"  + e.getMessage());
    }
  }

    /**
     * Integration test for verifying the retrieval of the closest destinations
     * to a starting location using the backend and graph.
     */
    @Test
    public void testClosestDestinationFromAllIntegration() {
      Backend backend = new Backend(new DijkstraGraph<>()); // Use DijkstraGraph implementation
      try {
        backend.loadGraphData("campus.dot");
        List<String> start = new ArrayList<>();
        start.add("Jorns Hall");
        // Test closest destinations for a valid location
        String closestDestinations = backend.getClosestDestinationFromAll(start);

        // Assertions to verify the list
        assertTrue(closestDestinations != null);
        assertTrue(closestDestinations.equals("Humphrey Hall"));
      } catch (IOException e) {
        fail("IOException is not to be thrown"  + e.getMessage());
      }
    }
}



