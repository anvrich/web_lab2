package servlets.lab2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.logging.Logger;



@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.createLogger(ControllerServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String clear = request.getParameter("clear");
        logger.info("from controller=|clear| : " + clear);

        if ("true".equals(clear)) {
            request.getRequestDispatcher("/clear").forward(request, response);
        } else {
            String x = request.getParameter("x");
            String y = request.getParameter("y");
            String r = request.getParameter("r");
            logger.info("Forwarding to checkarea with parameters:  x=" + x + ", y=" + y + ", r=" + r);
            request.getRequestDispatcher("/checkArea").forward(request, response);
        }
    }
}