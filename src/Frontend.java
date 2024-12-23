
import java.util.Arrays;
import java.util.List;

public class Frontend implements FrontendInterface {

    private BackendInterface backend;

    // Constructor
    public Frontend(BackendInterface backend) {
        this.backend = backend;
    }

    /**
     * Returns an HTML fragment that can be embedded within the body of a larger
     * HTML page. This HTML output should include:
     * - A text input field with the id="start", for the start location
     * - A text input field with the id="end", for the destination
     * - A button labeled "Find Shortest Path" to request this computation
     * Ensures that these text fields are clearly labeled, so that the user
     * can understand how to use them.
     *
     * @return an HTML string that contains input controls for the user to
     *         request a shortest path computation
     */
    @Override
    public String generateShortestPathPromptHTML() {
        return """
                <h2>Find the Shortest Path</h2>
                <p>Enter the Start Location and Destination</p>
                <p>Start Location: <input type="text" id="start" /></p>
                <p>Destination: <input type="text" id="end" /></p>
                <p><input type="button" value="Find Shortest Path" /></p>
                """;
    }

    /**
     * Generates an HTML fragment that can be embedded within the body of a larger
     * HTML page. This HTML output includes:
     * - A paragraph (p) describing the path's start and end locations
     * - An ordered list (ol) of locations along the shortest path
     * - A paragraph (p) with the total travel time along this path
     * If no path exists, it will display an error message.
     *
     * @param start the starting location for finding the shortest path
     * @param end   the destination for the shortest path
     * @return an HTML string that describes the shortest path between these
     *         two locations
     */
    @Override
    public String generateShortestPathResponseHTML(String start, String end) {
        List<String> locationsList; // Stores the list of locations on the path
        List<Double> timesList;     // Stores travel times between locations
        Double time;                // Total travel time
        try {
            // Retrieve the list of locations and times for the shortest path
            locationsList = this.backend.findLocationsOnShortestPath(start, end);
            timesList = this.backend.findTimesOnShortestPath(start, end); // Last element represents total time
        } catch (Exception e) {
            // Return error message if no path is found
            return "<p>There is no such path between " + start + " and " + end + "</p>";
        }

        String locations = ""; // Stores formatted HTML list items for each location

        // Generate HTML list items for each location on the path
        for (int i = 0; i < locationsList.size(); i++) {
            locations += ("<li>" + locationsList.get(i) + "</li>\n");
        }

        // Complete HTML fragment for displaying the shortest path details
        String htmlFragment = "<h2> Shortest Path </h2>"
            + "<p>The start location is " + start + " and the end location is " + end + ".</p>\n"
            + "<p>List of all locations along the shortest path:</p>\n"
            + "<ol>" + locations + "</ol>"
            + "<p>The total travel time along this path is " + timesList+ ".</p>\n";

        return htmlFragment;
    }

    /**
     * Generates an HTML fragment that can be embedded within the body of a larger
     * HTML page. This HTML output includes:
     * - A text input field with the id="from", for the start locations
     * - A button labeled "Closest From All" to submit this request
     * The text field should be clearly labeled, so that users can enter a
     * comma-separated list of multiple locations.
     *
     * @return an HTML string that contains input controls to request a calculation
     *         for the ten closest destinations
     */
    @Override
    public String generateClosestDestinationsFromAllPromptHTML() {
        return """
            <h2>Request Ten Closest Destinations Calculation</h2>
            <p>Enter the Locations</p>
            <p>Enter a comma separated list of as many locations as you would like:</p>
            <input type="text" id="from" />
            <input type="button" value="Closest From All" />
            """;
    }

    /**
     * Generates an HTML fragment that can be embedded within the body of a larger
     * HTML page. This HTML output includes:
     * - An unordered list (ul) of the start locations
     * - A paragraph describing the destination that is reached most quickly
     *   from all of those start locations
     * - A paragraph displaying the total/summed travel time to reach this destination
     * If no destination is found, it returns an error message.
     *
     * @param starts a comma-separated list of starting locations to search from
     * @return an HTML string that describes the closest destinations from the
     *         specified start locations
     */

    public String generateClosestDestinationsFromAllResponseHTML(String starts) {
        List<String> locationsList;
        String locations = "";
        String destination;
        Double totalTime = 0.0;

        try {
            // Split input
            locationsList = Arrays.asList(starts.split(","));
            destination = this.backend.getClosestDestinationFromAll(locationsList);

            // Calculate total time from each start location to the closest destination
            for (int i = 0; i < locationsList.size(); i++) {
                List<Double> times = this.backend.findTimesOnShortestPath(locationsList.get(i), destination);
                if (times != null && !times.isEmpty()) {
                    totalTime += times.get(times.size()); // Add the travel time to the destination
                }
            }
        } catch (Exception e) {
            // Return error message if no valid destination is found
            return "<p>No such destination can be found.</p>";
        }

        // If the input list of locations is empty, return a message indicating no valid paths
        if (locationsList.isEmpty()) {
            return "<p>No valid paths found from " + starts + ".</p>";
        }

        // Generate HTML list items for each start location
        for (int i = 0; i < locationsList.size(); i++) {
            locations += ("<li>" + locationsList.get(i) + "</li>\n");
        }

        // Complete HTML fragment for displaying the closest destination details
        String htmlFragment = "Closest Destination"
            + "<p>This is the list of locations:</p>\n"
            + "<ul>" + locations + "</ul>"
            + "<p>The destination that is reached most quickly from all of these start locations is "
            + destination + ".</p>\n"
            + "<p>The total travel time that it takes to reach this destination from all specified start locations is "
            + totalTime + ".</p>\n";

        return htmlFragment;
    }
}


//public String generateClosestDestinationsFromAllResponseHTML(String starts) {
    //    // Parse the input locations
    //    List<String> locationsList = Arrays.asList(starts.split(","));
    //    String locations = "";
    //    String destination;
    //
    //    // If the input list of locations is empty, return a message indicating no valid paths
    //    if (locationsList.isEmpty()) {
    //        return "<p>No valid paths found from " + starts + ".</p>";
    //    }
    //
    //    try {
    //        // Get the closest destination from all input locations
    //        destination = this.backend.getClosestDestinationFromAll(locationsList);
    //    } catch (Exception e) {
    //        // Handle exceptions and return a relevant error message
    //        return "<p>No such destination can be found.</p>";
    //    }
    //
    //    // Generate HTML list items for each start location
    //    for (String location : locationsList) {
    //        locations += ("<li>" + location + "</li>\n");
    //    }
    //
    //    // Generate HTML fragment for displaying the closest destination
    //    String htmlFragment = "Closest Destination"
    //        + "<p>This is the list of locations:</p>\n"
    //        + "<ul>" + locations + "</ul>"
    //        + "<p>The destination that is reached most quickly from all of these start locations is "
    //        + destination + ".</p>\n";
    //
    //    return htmlFragment;
    //}

