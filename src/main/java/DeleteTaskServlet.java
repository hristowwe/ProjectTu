import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Task;

import java.io.IOException;

@WebServlet("/tasks/delete")
public class DeleteTaskServlet extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        if (id == null) {
            response.getWriter().println("Please provide a task id as a parameter.");
        }

        Task deletedTask = TaskRepository.deleteTask(id);

        if (deletedTask == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Task not found for id: " + id);
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
}
