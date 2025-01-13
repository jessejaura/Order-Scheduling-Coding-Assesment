package Models;

public class OrderEntry {
    private String name;
    private int priority;

    public OrderEntry(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }
}
