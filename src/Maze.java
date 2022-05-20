import java.io.*;
import java.util.*;

public class Maze {
    /* Fields */
    int length;
    int height;
    int[][] maze;
    Node start;
    Node end;
    List<Node> visited = new ArrayList<>();
    List<Node> order = new ArrayList<>();

    /* Constructor */
    public Maze(int x, int y, int[][] input) {
        this.maze = input;
        this.length = x;
        this.height = y;
        this.start = new Node (0, 0, null);
    }

    /* Driver method */
    public void execute() {
        start.setValue(maze[0][0]);
        start.setDiagonal(false);
        createGraph(start);
        getPath(end);
        printOrder();
    }

    /* Primary method for modeling problem as a graph */
    private void createGraph(Node node) {
        // Adding current node to visited
        visited.add(node);
        // Getting coordinates and value of node passed into function
        int x = node.getX();
        int y = node.getY();
        int move = node.getValue();
        // list prepared to store options from a square
        List<Node> options = new ArrayList<>();
        // Checks for end cell, denoted with a value of 0
        if (move != 0) {
            // If current node came from a diagonal move
            if (node.isDiagonal()) {
                Node northeast = new Node(x + move, y - move, node);
                Node southeast = new Node(x + move, y + move, node);
                Node northwest = new Node(x - move, y - move, node);
                Node southwest = new Node(x - move, y + move, node);
                Collections.addAll(options, northeast, southeast, northwest, southwest);
            // Else if came from a non-diagonal move
            } else {
                Node north = new Node(x, y - move, node);
                Node south = new Node(x, y + move, node);
                Node east = new Node(x + move, y, node);
                Node west = new Node(x - move, y, node);
                Collections.addAll(options, north, south, east, west);
            }
            // Trimming out of bounds nodes
            options.removeIf(this::isOutOfBounds);
            // Setting values
            setValueForEachOption(options);
            // Label diagonals
            labelDiagonals(node, options);
            // Trimming already visited nodes
            options.removeIf(n -> visited.contains(n));
            // Adding children to node
            node.setChildren(options);
            // Getting the children's children if not a dead end
            if (!options.isEmpty()) {
                for (Node n : options) {
                    createGraph(n);
                }
            }
        } else {
            end = node;
        }
    }

    /* Helper method to set the value of each of the nodes in options */
    private void setValueForEachOption(List<Node> options) {
        for (Node n : options) {
            n.setValue(maze[n.getX()][n.getY()]);
        }
    }

    /* Helper method to check if a potential child node is out of bounds */
    private boolean isOutOfBounds(Node n) {
        return n.getX() > length - 1 || n.getY() > height - 1 || n.getX() < 0 || n.getY() < 0;
    }

    /* Helper method to label each of the options as being diagonal-moving or not */
    private void labelDiagonals(Node node, List<Node> options) {
        for (Node n : options) {
            int value = n.getValue();
            if (value < 0 && node.isDiagonal()) {
                n.setDiagonal(false);
            } else if (value < 0 && !node.isDiagonal()) {
                n.setDiagonal(true);
            } else if (value > 0 && node.isDiagonal()) {
                n.setDiagonal(true);
            } else if (value > 0 && !node.isDiagonal()) {
                n.setDiagonal(false);
            }
        }
    }

    /* Method to get the path using the parent property of nodes */
    private void getPath(Node node) {
        order.add(node);
        if (node.getParent() != null) {
            Node parent = node.getParent();
            getPath(parent);
        }
    }

    /* Helper method to print the order */
    private void printOrder() {
        Collections.reverse(order);
        for (Node n : order) {
            System.out.print(n + " ");
        }
    }

    /* Driver code */
    public static void main(String[] args) {
        try {
            File input = new File("example.txt");
            Scanner reader = new Scanner(input);
            int x = reader.nextInt();
            int y = reader.nextInt();
            int[][] maze = new int[x][y];
            while (reader.hasNextLine()) {
                for (int i = 0; i < maze.length; i++) {
                    for (int j = 0; j < maze.length; j++) {
                        maze[i][j] = reader.nextInt();
                    }
                }
            }
            reader.close();
            Maze jumpingJim = new Maze(x, y, maze);
            jumpingJim.execute();
        } catch (FileNotFoundException e) {
            System.out.println("File read error");
            e.printStackTrace();
        }
    }
}
