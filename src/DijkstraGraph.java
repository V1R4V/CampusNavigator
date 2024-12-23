
// === CS400 File Header Information ===
// Name: Vibhrav Jha
// Email: vjha3@wisc.edu
// Group and Team: <your group name: two letters, and team color>P2.3819
// Group TA: <name of your group's ta>
// Lecturer: FLorian Heimerl
// Notes to Grader: <optional extra notes>

import java.util.PriorityQueue;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.Arrays;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
    extends BaseGraph<NodeType, EdgeType>
    implements GraphADT<NodeType, EdgeType> {

  /**
   * While searching for the shortest path between two nodes, a SearchNode contains data about one
   * specific path between the start node and another node in the graph. The final node in this path
   * is stored in its node field. The total cost of this path is stored in its cost field. And the
   * predecessor SearchNode within this path is referened by the predecessor field (this field is
   * null within the SearchNode containing the starting node in its node field).
   *
   * SearchNodes are Comparable and are sorted by cost so that the lowest cost SearchNode has the
   * highest priority within a java.util.PriorityQueue.
   */
  protected class SearchNode implements Comparable<SearchNode> {
    public Node node;
    public double cost;
    public SearchNode predecessor;

    public SearchNode(Node node, double cost, SearchNode predecessor) {
      this.node = node;
      this.cost = cost;
      this.predecessor = predecessor;
    }

    public int compareTo(SearchNode other) {
      if (cost < other.cost) {
        return -1;
      }
      if (cost > other.cost) {
        return +1;
      }
      return 0;
    }
  }

  /**
   * Constructor that sets the map that the graph uses.
   */
  public DijkstraGraph() {
    super(new HashtableMap<>());
  }

  /**
   * This helper method creates a network of SearchNodes while computing the shortest path between
   * the provided start and end locations. The SearchNode that is returned by this method is
   * represents the end of the shortest path that is found: it's cost is the cost of that shortest
   * path, and the nodes linked together through predecessor references represent all of the nodes
   * along that shortest path (ordered from end to start).
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return SearchNode for the final end node within the shortest path
   * @throws NoSuchElementException when no path from start to end is found or when either start or
   *                                end data do not correspond to a graph node
   */
  protected SearchNode computeShortestPath(NodeType start, NodeType end) {
    // implement in step 5.3
    if (start == null || !this.containsNode(start) || end == null || !this.containsNode(end))
      throw new NoSuchElementException("The start or end node is not found");
    //throws exception for null elements
    // Retrieve the starting node from the graph then
    // Maps nodes to their corresponding SearchNodes to track the shortest path
    // at last building a Priority queue for selecting the node with the lowest path cost
    BaseGraph<NodeType, EdgeType>.Node startNodeType = this.nodes.get(start);
    HashtableMap<NodeType, SearchNode> visited = new HashtableMap<>();
    PriorityQueue<SearchNode> queue = new PriorityQueue<SearchNode>();
    SearchNode toStart = new SearchNode(startNodeType, 0, null);
    //adding the first element to the queue
    //visited.put(start, toStart);
    queue.add(toStart);
    while (!queue.isEmpty()) {
      //checking if the current node is already visited or not
      SearchNode current = queue.remove();
      if (!visited.containsKey(current.node.data)) {
        visited.put(current.node.data, current);

        if (visited.containsKey(current.node.data)) {
          if (visited.get(current.node.data).cost > current.cost) {
            visited.get(current.node.data).cost = current.cost;
          }
        }
        // Explore all outgoing edges from the current node
        for (Edge next : current.node.edgesLeaving) {
          SearchNode nextNode =
              new SearchNode(next.successor, current.cost + next.data.doubleValue(), current);

          if (!visited.containsKey(nextNode.node.data)) {
            queue.add(nextNode);

          }
        }
      }
    }
    //returns the last search node with the path or throws exception
    if (visited.containsKey(end))
      return visited.get(end);
    else
      throw new NoSuchElementException("No path found");
  }

  /**
   * Returns the list of data values from nodes along the shortest path from the node with the
   * provided start value through the node with the provided end value. This list of data values
   * starts with the start value, ends with the end value, and contains intermediary values in the
   * order they are encountered while traversing this shorteset path. This method uses Dijkstra's
   * shortest path algorithm to find this solution.
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return list of data item from node along this shortest path
   */
  public List<NodeType> shortestPathData(NodeType start, NodeType end) {
    // implement in step 5.4
    //makes new objects
    SearchNode result = computeShortestPath(start, end);
    LinkedList<NodeType> EndResult = new LinkedList<NodeType>();
    while (result.predecessor != null) {
      //cycles and adds elements from the search node
      EndResult.add(0, result.node.data);
      result = result.predecessor;
    }
    //adds the start node, predecessor is null and not added in loop
    EndResult.add(0, result.node.data);
    return EndResult;
  }

  /**
   * Returns the cost of the path (sum over edge weights) of the shortest path freom the node
   * containing the start data to the node containing the end data. This method uses Dijkstra's
   * shortest path algorithm to find this solution.
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return the cost of the shortest path between these nodes
   */
  public double shortestPathCost(NodeType start, NodeType end) {
    // implement in step 5.4
    //gets the value with the end node
    SearchNode result = computeShortestPath(start, end);
    return result.cost;
  }




  // TODO: implement 3+ tests in step 4.1

  /**
   * Tests the DijkstraGraph class by finding the shortest path and its cost from node "A" to node
   * "E". The test uses a directed graph and validates that the computed path and cost match the
   * expected results based on the lecture example. The expected shortest path is ["A", "B", "D",
   * "E"], and the expected cost is 8.0. This test ensures that the Dijkstra algorithm correctly
   * computes the shortest path and cost in a directed graph.
   */
  @Test
  public void testShortestPathAtoE() {
    // Creating a new graph object and adding all node objects
    DijkstraGraph<String, Double> example = new DijkstraGraph<>();
    example.insertNode("A");
    example.insertNode("B");
    example.insertNode("C");
    example.insertNode("D");
    example.insertNode("E");
    example.insertNode("F");
    example.insertNode("G");
    example.insertNode("H");

    // Inserting all weighted edges, THIS IS A DIRECTED GRAPH AS PER THE LECTURE
    example.insertEdge("A", "B", 4.0);
    //example.insertEdge("B", "A", 4.0);
    example.insertEdge("A", "C", 2.0);
    //example.insertEdge("C", "A", 2.0);
    example.insertEdge("B", "D", 1.0);
    //example.insertEdge("D", "B", 1.0);
    example.insertEdge("B", "E", 10.0);
    // example.insertEdge("E", "B", 10.0);
    example.insertEdge("C", "D", 5.0);
    //example.insertEdge("D", "C", 5.0);
    example.insertEdge("D", "E", 3.0);
    //  example.insertEdge("E", "D", 3.0);
    example.insertEdge("D", "F", 0.0);
    example.insertEdge("F", "D", 2.0);
    // example.insertEdge("E", "F", 0.0);
    //   example.insertEdge("F", "E", 0.0);
    //  example.insertEdge("E", "G", 0.0);
    // example.insertEdge("G", "E", 0.0);
    example.insertEdge("F", "H", 4.0);
    //  example.insertEdge("H", "F", 4.0);
    example.insertEdge("G", "H", 4.0);
    // example.insertEdge("H", "G", 4.0);

    List<String> shortestPath = example.shortestPathData("A", "E");

    // Expected path and cost based on the class example
    List<String> expectedPath = Arrays.asList("A", "B", "D", "E");
    double expectedCost = 8.0;

    // Assert the path and cost to verify correctness
    Assertions.assertEquals(expectedPath, shortestPath,
        "The shortest path should match the expected path.");
    Assertions.assertEquals(expectedCost, example.shortestPathCost("A", "E"),
        "The shortest path cost should match the expected cost.");
  }

  /**
   * Tests the DijkstraGraph class by finding the shortest path and its cost from node "C" to node
   * "H". The test ensures that the Dijkstra algorithm correctly computes the shortest path and cost
   * between the specified start and end nodes, with the expected path ["C", "D", "F", "H"] and
   * expected cost 9.0. This test verifies that the algorithm handles different node pairs
   * properly.
   */
  @Test
  public void testShortestPathCtoH() {
    // Creating a new graph object and adding all node objects
    DijkstraGraph<String, Double> example = new DijkstraGraph<>();
    example.insertNode("A");
    example.insertNode("B");
    example.insertNode("C");
    example.insertNode("D");
    example.insertNode("E");
    example.insertNode("F");
    example.insertNode("G");
    example.insertNode("H");

    example.insertEdge("A", "B", 4.0);
    example.insertEdge("A", "C", 2.0);
    example.insertEdge("B", "D", 1.0);
    example.insertEdge("B", "E", 10.0);
    example.insertEdge("C", "D", 5.0);
    example.insertEdge("D", "E", 3.0);
    example.insertEdge("D", "F", 0.0);
    example.insertEdge("F", "D", 2.0);
    example.insertEdge("F", "H", 4.0);
    example.insertEdge("G", "H", 4.0);

    List<String> expectedPath = Arrays.asList("C", "D", "F", "H");
    double expectedCost = 9.0;

    // Assert the path and cost to verify correctness
    Assertions.assertEquals(expectedPath, example.shortestPathData("C", "H"),
        "The shortest path should match the expected path.");
    Assertions.assertEquals(expectedCost, example.shortestPathCost("C", "H"),
        "The shortest path cost should match the expected cost.");
  }

  /**
   * Tests the DijkstraGraph class by verifying that an exception is thrown when no path exists
   * between two nodes in the graph. Specifically, the test checks the case where there is no path
   * between nodes "E" and "A". The expected exception is a NoSuchElementException with the message
   * "There is no path found", which is tested to ensure proper exception handling when no valid
   * path exists.
   */
  @Test
  public void testIncompletePath() {
    DijkstraGraph<String, Double> example = new DijkstraGraph<>();
    example.insertNode("A");
    example.insertNode("B");
    example.insertNode("C");
    example.insertNode("D");
    example.insertNode("E");
    example.insertNode("F");
    example.insertNode("G");
    example.insertNode("H");

    example.insertEdge("A", "B", 4.0);
    example.insertEdge("A", "C", 2.0);
    example.insertEdge("B", "D", 1.0);
    example.insertEdge("B", "E", 10.0);
    example.insertEdge("C", "D", 5.0);
    example.insertEdge("D", "E", 3.0);
    example.insertEdge("D", "F", 0.0);
    example.insertEdge("F", "D", 2.0);
    example.insertEdge("F", "H", 4.0);
    example.insertEdge("G", "H", 4.0);

    Exception exceptionMethod = Assertions.assertThrows(NoSuchElementException.class, () -> {
      example.shortestPathData("E", "A");
    });

    String expectedMessage = "No path found";
    String actualMessage = exceptionMethod.getMessage();

    //tests the methods of the class, making sure the calculated values match properly
    Assertions.assertTrue(actualMessage.contains(expectedMessage), "test FAILED");
  }

  /**
   * Tests the DijkstraGraph class by finding the shortest path and its cost from node "A" to node
   * "E". The test uses an Undirected graph and validates that the computed path and cost match the
   * expected results based on the lecture example. The expected shortest path is ["A", "B", "D",
   * "F", "E"], and the expected cost is 5.0. This test ensures that the Dijkstra algorithm
   * correctly computes the shortest path and cost in an undirected graph.
   */
  @Test
  public void testShortestPathUndirectedAtoE() {
    // Creating a new graph object and adding all node objects
    DijkstraGraph<String, Double> example = new DijkstraGraph<>();
    example.insertNode("A");
    example.insertNode("B");
    example.insertNode("C");
    example.insertNode("D");
    example.insertNode("E");
    example.insertNode("F");
    example.insertNode("G");
    example.insertNode("H");

    // Inserting all weighted edges, THIS IS A DIRECTED GRAPH AS PER THE LECTURE
    example.insertEdge("A", "B", 4.0);
    example.insertEdge("B", "A", 4.0);
    example.insertEdge("A", "C", 2.0);
    example.insertEdge("C", "A", 2.0);
    example.insertEdge("B", "D", 1.0);
    example.insertEdge("D", "B", 1.0);
    example.insertEdge("B", "E", 10.0);
    example.insertEdge("E", "B", 10.0);
    example.insertEdge("C", "D", 5.0);
    example.insertEdge("D", "C", 5.0);
    example.insertEdge("D", "E", 3.0);
    example.insertEdge("E", "D", 3.0);
    example.insertEdge("D", "F", 0.0);
    example.insertEdge("F", "D", 2.0);
    example.insertEdge("E", "F", 0.0);
    example.insertEdge("F", "E", 0.0);
    example.insertEdge("E", "G", 0.0);
    example.insertEdge("G", "E", 0.0);
    example.insertEdge("F", "H", 4.0);
    example.insertEdge("H", "F", 4.0);
    example.insertEdge("G", "H", 4.0);
    example.insertEdge("H", "G", 4.0);

    List<String> shortestPath = example.shortestPathData("A", "E");

    // Expected path and cost based on the class example
    List<String> expectedPath = Arrays.asList("A", "B", "D", "F", "E");
    double expectedCost = 5.0;

    // Assert the path and cost to verify correctness
    Assertions.assertEquals(expectedPath, shortestPath,
        "The shortest path should match the expected path.");
    Assertions.assertEquals(expectedCost, example.shortestPathCost("A", "E"),
        "The shortest path cost should match the expected cost.");
  }

  /**
   * This test checks if the algorithm correctly handles the case where the start and
   * end nodes are the same and there are no edges.
   */
  @Test
  public void testSingleNodeGraph() {
    DijkstraGraph<String, Double> example = new DijkstraGraph<>();
    example.insertNode("A");

    // In this case, the start node is the same as the end node
    List<String> shortestPath = example.shortestPathData("A", "A");
    double shortestCost = example.shortestPathCost("A", "A");

    // The shortest path should just be ["A"], and cost should be 0.0
    Assertions.assertEquals(Arrays.asList("A"), shortestPath, "The path should be just ['A']");
    Assertions.assertEquals(0.0, shortestCost, "The cost should be 0.0");
  }
  /**
   * Edge case for testing shortest path when end and starting Destinations are the same
   */
  @Test
  public void testShortestPathUndirectedAtoA() {
    // Creating a new graph object and adding all node objects
    DijkstraGraph<String, Double> example = new DijkstraGraph<>();
    example.insertNode("A");
    example.insertNode("B");
    example.insertNode("C");
    example.insertNode("D");
    example.insertNode("E");
    example.insertNode("F");
    example.insertNode("G");
    example.insertNode("H");

    // Inserting all weighted edges, THIS IS A DIRECTED GRAPH AS PER THE LECTURE
    example.insertEdge("A", "B", 4.0);
    example.insertEdge("B", "A", 4.0);
    example.insertEdge("A", "C", 2.0);
    example.insertEdge("C", "A", 2.0);
    example.insertEdge("B", "D", 1.0);
    example.insertEdge("D", "B", 1.0);
    example.insertEdge("B", "E", 10.0);
    example.insertEdge("E", "B", 10.0);
    example.insertEdge("C", "D", 5.0);
    example.insertEdge("D", "C", 5.0);
    example.insertEdge("D", "E", 3.0);
    example.insertEdge("E", "D", 3.0);
    example.insertEdge("D", "F", 0.0);
    example.insertEdge("F", "D", 2.0);
    example.insertEdge("E", "F", 0.0);
    example.insertEdge("F", "E", 0.0);
    example.insertEdge("E", "G", 0.0);
    example.insertEdge("G", "E", 0.0);
    example.insertEdge("F", "H", 4.0);
    example.insertEdge("H", "F", 4.0);
    example.insertEdge("G", "H", 4.0);
    example.insertEdge("H", "G", 4.0);

    List<String> shortestPath = example.shortestPathData("A", "A");

    // Expected path and cost based on the class example
    List<String> expectedPath = Arrays.asList("A");
    double expectedCost = 0.0;
    Assertions.assertEquals(expectedPath, shortestPath,
        "The shortest path should match the expected path.");
    Assertions.assertEquals(expectedCost, example.shortestPathCost("A", "A"),
        "The shortest path cost should match the expected cost.");
  }

  /**
   * Test when the graph contains a cycle
   */

  @Test
  public void testCyclicGraph() {
    DijkstraGraph<String, Double> example = new DijkstraGraph<>();
    example.insertNode("A");
    example.insertNode("B");
    example.insertNode("C");

    example.insertEdge("A", "B", 5.0);
    example.insertEdge("B", "C", 10.0);
    example.insertEdge("C", "A", 15.0); // This creates a cycle

    List<String> shortestPath = example.shortestPathData("A", "C");
    double shortestCost = example.shortestPathCost("A", "C");

    List<String> expectedPath = Arrays.asList("A", "B", "C");
    double expectedCost = 15.0;

    Assertions.assertEquals(expectedPath, shortestPath);
    Assertions.assertEquals(expectedCost, shortestCost);
  }

}
