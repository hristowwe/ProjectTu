import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Task;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/tasks/view")
public class ShowTaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");


        Task task = TaskRepository.getTask(id);

        if(task == null) {
            response.getWriter().println("Please provide a task id as a parameter.");
            return;
        }
        TaskList taskList = new TaskList();
        taskList.addTask(task);

        try {
            // Създаваме JAXBContext за TaskList
            JAXBContext jaxbContext = JAXBContext.newInstance(TaskList.class, Task.class);

            // Настройваме отговора да е XML
            response.setContentType("application/xml");
            response.setCharacterEncoding("UTF-8");

            // Маршалираме (TaskList -> XML)
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(taskList, response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Could not marshal tasks to XML.");
        }


//        if(id == null) {
//            response.getWriter().println("Please provide a task id as a parameter.");
//            return;
//        }
//
//        Task task = TaskRepository.getTask(id);
//        if(task != null) {
//            response.getWriter().println("Task Details: " + task);
//        } else {
//            response.getWriter().println("Task not found for id: " + id);
//        }
    }
}
