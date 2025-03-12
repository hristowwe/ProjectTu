import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Task;

import java.io.IOException;

@WebServlet("/tasks/view")
public class ShowTaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");

        if(id == null) {
            response.getWriter().println("Please provide a task id as a parameter.");
            return;
        }

        Task task = TaskRepository.getTask(id);
        if(task != null) {
            response.getWriter().println("Task Details: " + task);
        } else {
            response.getWriter().println("Task not found for id: " + id);
        }
    }
}
