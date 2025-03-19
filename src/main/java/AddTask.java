import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Task;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;

@WebServlet("/tasks/add")
public class AddTask extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Task.class);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            Task task = (Task) unmarshaller.unmarshal(request.getInputStream());

            TaskRepository.addTask(task);

            response.setContentType("application/xml");
            response.setCharacterEncoding("UTF-8");

            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(task, response.getOutputStream());


//            String id = request.getParameter("id");
//            String title = request.getParameter("title");
//            String description = request.getParameter("description");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid XML format or internal error.");
        }
    }
}
