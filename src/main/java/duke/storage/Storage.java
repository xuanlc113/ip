package duke.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

/**
 * Represents a save file modifier. A storage object corresponds to a save file in a specified
 * location
 */
public class Storage {

    String path;

    public Storage(String filepath) {
        this.path = filepath;
    }

    /**
     * Saves list of tasks to a text file in a specified location
     *
     * @param tasks list of tasks to save
     * @throws IOException If file cannot be opened, e.g. location specified is a folder rather than a
     *         regular file
     */
    public void saveTasks(List<Task> tasks) throws IOException {
        File saveFile = new File(this.path);
        if (!saveFile.exists()) {
            saveFile.createNewFile();
        }
        FileWriter writer = new FileWriter(saveFile);
        for (Task item : tasks) {
            writer.append(item.toSave() + System.lineSeparator());
        }
        writer.close();
    }

    /**
     * Extracts tasks from a save file and places the tasks into a task array
     *
     * @return List of tasks
     * @throws FileNotFoundException If the save file cannot be found
     */
    public List<Task> load() throws FileNotFoundException {
        List<Task> tasks = new ArrayList<Task>();
        File saveFile = new File(this.path);
        Scanner s = new Scanner(saveFile);
        while (s.hasNext()) {
            String[] taskParts = s.nextLine().split(" \\| ");
            String command = taskParts[0];
            String description = taskParts[1];
            Boolean isDone = Boolean.parseBoolean(taskParts[2]);
            switch (command) {
            case "todo":
                tasks.add(new Todo(description, isDone));
                break;
            case "deadline":
                tasks.add(new Deadline(description, taskParts[3], isDone));
                break;
            case "event":
                tasks.add(new Event(description, taskParts[3], isDone));
                break;
            }
        }
        s.close();
        return tasks;
    }

}
