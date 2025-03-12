import model.Task;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TaskRepository {
    private static Map<String, Task> tasks = new ConcurrentHashMap<>();

    public static void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public static Task getTask(String id) {
        return tasks.get(id);
    }

    public static Collection<Task> getAllTasks() {
        return tasks.values();
    }
}
