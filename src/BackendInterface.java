import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This is the interface that a backend developer will implement, so that
 * a frontend developer's code can make use of this functionality.  It makes
 * use of a GraphADT to perform shortest path computations.
 */
public interface BackendInterface {

  /*
   * Implementing classes should support the constructor below.
   * @param graph object to store the backend's graph data
   */
  // public Backend(GraphADT<String,Double> graph);

  /**
   * Loads graph data from a dot file.  If a graph was previously loaded, this
   * method should first delete the contents (nodes and edges) of the existing 
   * graph before loading a new one.
   * @param filename the path to a dot file to read graph data from
   * @throws IOException if there was any problem reading from this file
   */
  public void loadGraphData(String filename) throws IOException;

  /**
   * Returns a list of all locations (node data) available in the graph.
   * @return list of all location names
   */
  public List<String> getListOfAllLocations();

  /**
   * Return the sequence of locations along the shortest path from 
   * startLocation to endLocation, or an empty list if no such path exists.
   * @param startLocation the start location of the path
   * @param endLocation the end location of the path
   * @return a list with the nodes along the shortest path from startLocation 
   *         to endLocation, or an empty list if no such path exists
   */
  public List<String> findLocationsOnShortestPath(String startLocation, String endLocation);

  /**
   * Return the walking times in seconds between each two nodes on the 
   * shortest path from startLocation to endLocation, or an empty list of no 
   * such path exists.
   * @param startLocation the start location of the path
   * @param endLocation the end location of the path
   * @return a list with the walking times in seconds between two nodes along 
   *         the shortest path from startLocation to endLocation, or an empty 
   *         list if no such path exists
   */
  public List<Double> findTimesOnShortestPath(String startLocation, String endLocation);

  /**
   * Returns the location can be reached from all of the specified start 
   * locations in the shortest total time: minimizing the sum of the travel
   * times from each start locations.
   * @param startLocations the list of locations to minimize travel time from
   * @return the location that can be reached in the shortest total time from 
   *         all of the specified start locations
   * @throws NoSuchElementException if there is no destination that can be 
   *         reached from all of the start locations, or if any of the start
   *         locations does not exist within the graph
   */
  public String getClosestDestinationFromAll(List<String> startLocations) throws NoSuchElementException;

}
