
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Backend implements BackendInterface {

  private GraphADT<String, Double> graphs;
  private List<String> nodes;

  /*
   * Implementing classes should support the constructor below.
   * @param graph object to store the backend's graph data
   */
  public Backend(GraphADT<String, Double> graphs) {
    this.graphs = graphs;
  }

  /**
   * Loads graph data from a dot file.  If a graph was previously loaded, this method should first
   * delete the contents (nodes and edges) of the existing graph before loading a new one.
   *
   * @param filename the path to a dot file to read graph data from
   * @throws IOException if there was any problem reading from this file
   */

  @Override
  public void loadGraphData(String filename) throws IOException {
    //clears graph if any previous nodes
    for (String nodes : graphs.getAllNodes()) {
      graphs.removeNode(nodes);
    }

    File file = new File(filename);

    // Check if the file exists
    if (!file.exists()) {
      throw new IOException("File not found: " + file.getAbsolutePath());
    }

    // Example of a valid input line: A -> B [weight=2.5];
    // Use a Scanner to read the file line by line
    try (Scanner scanner = new Scanner(file)) {
      // Skip the first line ("digraph campus {")
      if (scanner.hasNextLine()) {
        scanner.nextLine();
      }

      // Read and parse each line in the DOT file
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine().trim();

        // Stop if empty lines or the closing brace
        if (line.isEmpty() || line.equals("}")) {
          break;
        }

        try {
          // Process the line to extract start node, end node, and weight
          line = line.strip().replace("\"", ""); // Remove quotes
          int arrow = line.indexOf("->");
          if (arrow == -1) {
            System.err.println("Invalid line format, missing '->': " + line);
            continue;
          }

          String start = line.substring(0, arrow).strip(); // Start node

          int bracket = line.indexOf("[");
          if (bracket == -1) {
            System.err.println("Invalid line format, missing '[': " + line);
            continue;
          }

          String end = line.substring(arrow + 2, bracket).strip(); // End node

          int equal = line.indexOf("=");
          int close = line.indexOf("]");
          if (equal == -1 || close == -1 || equal >= close) {
            System.err.println("Invalid line format for weight: " + line);
            continue;
          }

          Double weight;
          try {
            weight = Double.parseDouble(line.substring(equal + 1, close).strip()); // Weight
          } catch (NumberFormatException e) {
            System.err.println("Failed to parse weight as a double: " + line);
            continue;
          }

          // Insert the start node if it doesn't already exist
          if (!graphs.containsNode(start)) {
            graphs.insertNode(start);
          }

          // Insert the end node if it doesn't already exist
          if (!graphs.containsNode(end)) {
            graphs.insertNode(end);
          }
          // Insert the edge with the specified weight
          graphs.insertEdge(start, end, weight);

        } catch (StringIndexOutOfBoundsException e) {
          System.err.println("String index error while processing line: " + line);
        }
      }
    } catch (IOException e) {
      System.err.println("Error reading file: " + e.getMessage());
      throw e; // Re-throw to indicate failure
    }
  }
  //  public void loadGraphData(String filename) throws IOException {
  //    // Reset the list of nodes
  //    nodes = new ArrayList<>();
  //
  //    // Create a Scanner to read file and a File that take filename as a string and converts the
  //    // path into file
  //    File file = new File(filename);
  //    Scanner scan = new Scanner(file);
  //
  //    // Skip the first line as it is diagraph
  //    if (scan.hasNextLine()) {
  //      scan.nextLine();
  //    }
  //
  //    // Read through the file line by line
  //    while (scan.hasNextLine()) {
  //      String line = scan.nextLine().trim();
  //
  //      // Skip empty lines and closing brace
  //      if (line.isEmpty() || line.equals("}")) {
  //        continue;
  //      }
  //
  //      try {
  //        // Parse the line in DOT format: "node1" -> "node2" [weight=123]
  //        line = line.replace("\"", ""); // Remove quotes
  //
  //        // Find the arrow separator and weight section
  //        int arrowIndex = line.indexOf("->");
  //        //        if (arrowIndex == -1) continue; // Skip invalid lines
  //
  //        int bracketIndex = line.indexOf("[");
  //        if (bracketIndex == -1) continue; // same here
  //
  //        // For start and end nodes
  //        String startNode = line.substring(0, arrowIndex).trim();
  //        String endNode = line.substring(arrowIndex + 2, bracketIndex).trim();
  //
  //        // Edge weight value
  //        int equalsIndex = line.indexOf("=", bracketIndex);
  //        int closeBracketIndex = line.indexOf("]", bracketIndex);
  //        if (equalsIndex == -1 || closeBracketIndex == -1) continue;
  //
  //        String weightStr = line.substring(equalsIndex + 1, closeBracketIndex).trim();
  //        Double weight = Double.parseDouble(weightStr);
  //
  //        // Insert nodes if they don't exist
  //        if (!graphs.containsNode(startNode)) {
  //          graphs.insertNode(startNode);
  //          nodes.add(startNode);
  //        }
  //
  //        if (!graphs.containsNode(endNode)) {
  //          graphs.insertNode(endNode);
  //          nodes.add(endNode);
  //        }
  //
  //        // Insert or update the edge
  //        graphs.insertEdge(startNode, endNode, weight);
  //
  //        //Neccesary catch block
  //      } catch (NumberFormatException | IndexOutOfBoundsException e) {
  //        System.err.println("Error Parsing line" + line);
  //
  //      }
  //    }
  //
  //    scan.close();
  //
  //    // If no nodes were loaded, the file might be invalid
  //    if (nodes.isEmpty()) {
  //      throw new IOException("No valid graph data found in file: " + filename);
  //    }
  //  }


  /**
   * Returns a list of all locations (node data) available in the graph.
   *
   * @return list of all location names
   */

  @Override
  public List<String> getListOfAllLocations() {
    // Get and return all node data (locations) from the graph
    return graphs.getAllNodes();
  }

  /**
   * Return the sequence of locations along the shortest path from startLocation to endLocation, or
   * an empty list if no such path exists.
   *
   * @param startLocation the start location of the path
   * @param endLocation   the end location of the path
   * @return a list with the nodes along the shortest path from startLocation to endLocation, or an
   * empty list if no such path exists
   */

  @Override
  public List<String> findLocationsOnShortestPath(String startLocation, String endLocation) {
    try {
      return graphs.shortestPathData(startLocation, endLocation);
    } catch (NoSuchElementException e) {
      return new ArrayList<>(); // Return empty list if path is not found
    }
  }

  /**
   * Return the walking times in seconds between each two nodes on the shortest path from
   * startLocation to endLocation, or an empty list of no such path exists.
   *
   * @param startLocation the start location of the path
   * @param endLocation   the end location of the path
   * @return a list with the walking times in seconds between two nodes along the shortest path from
   * startLocation to endLocation, or an empty list if no such path exists
   */

  @Override
  public List<Double> findTimesOnShortestPath(String startLocation, String endLocation) {
    List<Double> times = new ArrayList<>();
    try {
      List<String> path = graphs.shortestPathData(startLocation, endLocation);
      for (int i = 0; i < path.size() - 1; i++) {
        String start = path.get(i);
        String end = path.get(i + 1);
        double time = graphs.getEdge(start, end);
        times.add(time);
      }
    } catch (NoSuchElementException e) {
      return new ArrayList<>();
    }
    return times;
  }

  /**
   * Returns the location can be reached from all of the specified start locations in the shortest
   * total time: minimizing the sum of the travel times from each start locations.
   *
   * @param startLocations the list of locations to minimize travel time from
   * @return the location that can be reached in the shortest total time from all of the specified
   * start locations
   * @throws NoSuchElementException if there is no destination that can be reached from all of the
   *                                start locations, or if any of the start locations does not exist
   *                                within the graph
   */


  @Override
  public String getClosestDestinationFromAll(List<String> startLocations)
      throws NoSuchElementException {
    // Validate that all startLocations exist in the graph
    for (String start : startLocations) {
      if (!graphs.containsNode(start)) {
        throw new NoSuchElementException(
            "Start location " + start + " does not exist in the graph.");
      }
    }

    // Retrieve all nodes in the graph
    List<String> allNodes = graphs.getAllNodes();

    // Lists to store nodes and their respective total path costs
    List<String> nodeList = new ArrayList<>();
    List<Double> costList = new ArrayList<>();

    // Iterate through all nodes in the graph
    for (String location : allNodes) {
      double totalCost = 0; // Total travel cost to this location
      boolean allReachable = true; // Flag to check if all start locations can reach this location

      for (String start : startLocations) {
        try {
          // Calculate the shortest path cost from start to location
          double cost = graphs.shortestPathCost(start, location);
          totalCost += cost; // Add to the total cost
        } catch (NoSuchElementException e) {
          // If a start location cannot reach this destination, mark as unreachable
          allReachable = false;
          break;
        }
      }

      // Only consider this location if all start locations can reach it
      if (allReachable) {
        insertNodeAndCostInOrder(nodeList, costList, location, totalCost);
      }
    }

    // If no destinations are reachable from all start locations, throw an exception
    if (nodeList.isEmpty()) {
      throw new NoSuchElementException(
          "No common destination found from the given start locations.");
    }

    // Return the destination with the smallest total cost
    return nodeList.get(0);
  }

  /**
   * Inserts a node and its cost into the lists in ascending order of cost.
   *
   * @param nodeList the list of nodes
   * @param costList the list of corresponding costs
   * @param node     the node to insert
   * @param cost     the cost associated with the node
   */
  private void insertNodeAndCostInOrder(List<String> nodeList, List<Double> costList, String node,
      double cost) {
    int index = 0;
    while (index < costList.size() && costList.get(index) < cost) {
      index++;
    }
    nodeList.add(index, node);
    costList.add(index, cost);
  }
}
