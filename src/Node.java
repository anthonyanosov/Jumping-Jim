import java.util.*;

public class Node {
    final private int x;
    final private int y;
    private int value;
    private boolean diagonal;
    final private Node parent;
    private List<Node> children = new ArrayList<>();

    public Node(int x, int y, Node parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public void setDiagonal(boolean diagonal) {
        this.diagonal = diagonal;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }

    public Node getParent() {
        return parent;
    }

    public boolean isDiagonal() {
        return diagonal;
    }

    @Override
    public String toString() {
        int realX = x + 1;
        int realY = y + 1;
        return "(" + realY + ", " + realX + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Node other = (Node) obj;
        return this.x == other.x && this.y == other.y && this.diagonal == other.diagonal;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
