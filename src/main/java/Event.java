public class Event extends Task {

    private String at;

    public Event(String task, String at) {
        super(task);
        this.at = at;
    }

    @Override
    public String toString() {
        return String.format("[E][%s] %s (at: %s)", getStatusIcon(), this.description, this.at);
    }
}
