# Java-based Shortest Path Visualization Application

This Java-based application is a powerful tool that dynamically generates interactive HTML elements, offering a sophisticated visualization of shortest path computations and other graph-based operations. It provides a seamless, user-friendly interface for users to engage with the backend, which leverages Dijkstra's algorithm to compute the shortest paths between locations within Madison. Through this robust system, users can easily explore and interact with complex algorithms, making data-driven decisions and gaining deeper insights into the efficiency of various pathfinding strategies.
## Features

### 1. **Shortest Path Prompt Generation**
- **Description**: Allows users to input a starting location and a destination.
- **Functionality**:
  - Generates an HTML interface with fields for the user to enter the start and end locations.
  - Includes a button to compute the shortest path between the two locations.

### 2. **Shortest Path Results Display**
- **Description**: Dynamically generates an HTML section to display the results of the shortest path computation.
- **Key Elements**:
  - Start and end locations.
  - An ordered list of locations along the computed shortest path.
  - Total travel time for the journey.
  - Error messages if no valid path exists or if input is invalid.

### 3. **Closest Destinations Prompt**
- **Description**: Provides an interface to input multiple starting locations.
- **Functionality**:
  - Facilitates the calculation of the ten closest destinations from all input locations.

### 4. **Closest Destinations Results Display**
- **Description**: Visualizes the results of the closest destination computation.
- **Key Elements**:
  - An unordered list of starting locations.
  - The most quickly reachable destination from all provided starting locations.
  - The total travel time to the closest destination.
  - Error messages for invalid inputs or computations.
 
### 5. **Demo**
<img width="873" alt="Screenshot 2024-12-23 at 11 41 44 AM" src="https://github.com/user-attachments/assets/05a83113-a47a-4c46-8d84-2d293590dc9a" />
 <img width="481" alt="Screenshot 2024-12-23 at 11 42 13 AM" src="https://github.com/user-attachments/assets/26b1ba46-179d-471a-b296-22830128dff7" />

### Java-based Shortest Path Visualization Application

This Java-based application is a powerful tool that dynamically generates interactive HTML elements, offering a sophisticated visualization of shortest path computations and other graph-based operations. It provides a seamless, user-friendly interface for users to engage with the backend, which leverages Dijkstra's algorithm to compute the shortest paths between locations within Madison. Through this robust system, users can easily explore and interact with complex algorithms, making data-driven decisions and gaining deeper insights into the efficiency of various pathfinding strategies.

## Features

### 1. **Shortest Path Prompt Generation**
- **Description**: Allows users to input a starting location and a destination.
- **Functionality**:
  - Generates an HTML interface with fields for the user to enter the start and end locations.
  - Includes a button to compute the shortest path between the two locations.

### 2. **Shortest Path Results Display**
- **Description**: Dynamically generates an HTML section to display the results of the shortest path computation.
- **Key Elements**:
  - Start and end locations.
  - An ordered list of locations along the computed shortest path.
  - Total travel time for the journey.
  - Error messages if no valid path exists or if input is invalid.

### 3. **Closest Destinations Prompt**
- **Description**: Provides an interface to input multiple starting locations.
- **Functionality**:
  - Facilitates the calculation of the ten closest destinations from all input locations.

### 4. **Closest Destinations Results Display**
- **Description**: Visualizes the results of the closest destination computation.
- **Key Elements**:
  - An unordered list of starting locations.
  - The most quickly reachable destination from all provided starting locations.
  - The total travel time to the closest destination.
  - Error messages for invalid inputs or computations.

### 5. **Demo**
<img width="873" alt="Screenshot 2024-12-23 at 11 41 44 AM" src="https://github.com/user-attachments/assets/05a83113-a47a-4c46-8d84-2d293590dc9a" />
<img width="481" alt="Screenshot 2024-12-23 at 11 42 13 AM" src="https://github.com/user-attachments/assets/26b1ba46-179d-471a-b296-22830128dff7" />

## System Architecture

The architecture of this application is designed to be modular, with clear separation between the frontend, backend, and data handling layers. The backend handles complex computations, while the frontend provides a dynamic, user-friendly interface.

### Key Components:

1. **Frontend**:
   - **WebApp.java**: Handles dynamic HTML generation for the user interface.
   - **FrontendInterface.java**: Defines methods for interaction between the frontend and backend.
   - **HTML Generation**: Dynamically generates HTML content based on user input and backend responses.

2. **Backend**:
   - **GraphADT.java**: Defines the graph structure with nodes and edges.
   - **DijkstraGraph.java**: Implements Dijkstra’s algorithm to compute the shortest paths.
   - **MapADT.java**: A map abstraction used to store and retrieve nodes and edges efficiently.
   - **BackendInterface.java**: Defines methods for the backend to communicate with the frontend.

3. **Data Structures**:
   - **Adjacency List**: Efficiently stores nodes and edges for graph traversal.
   - **Priority Queue**: Utilized by Dijkstra’s algorithm to find the next node with the smallest tentative distance.
   - **Hash Map**: Stores nodes and edge relationships for fast lookup.

## Data Structures and Algorithms (DSA) Used

### 1. **Graph Representation (Adjacency List)**:
   - **GraphADT.java**: Represents the graph as an adjacency list, where each node has a list of connected nodes (edges) and their associated weights (travel times).
   - The adjacency list structure allows efficient traversal and retrieval of neighbors.

### 2. **Dijkstra’s Algorithm**:
   - **DijkstraGraph.java**: Implements Dijkstra's algorithm for shortest path computation. It uses a **priority queue** (min-heap) to process nodes with the smallest tentative distance efficiently.
   - The time complexity is **O(E log V)**, where V is the number of vertices and E is the number of edges.

### 3. **Priority Queue**:
   - **Min-Heap**: A priority queue is used to always process the node with the smallest distance, ensuring that the algorithm is both correct and efficient.

### 4. **Hash Maps**:
   - **MapADT.java**: A hash map is used for storing and quickly retrieving node relationships and edge weights, ensuring fast access during pathfinding.

### 5. **Heap (Min-Heap)**:
   - The priority queue implemented as a min-heap ensures efficient node selection for processing in Dijkstra’s algorithm.

## Contributors

- **Vibhrav**: Development and design of the app.
- **Collaborators**: None

