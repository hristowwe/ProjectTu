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
import java.util.Collection;

@WebServlet("/tasks")
public class ShowAllTasksServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Collection<Task> allTasks = TaskRepository.getAllTasks();

        TaskList taskList = new TaskList();
        taskList.setTasks(new ArrayList<>(allTasks)); // преобръщаме Collection в ArrayList

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
    }
}