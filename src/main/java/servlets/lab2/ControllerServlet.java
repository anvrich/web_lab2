package servlets.lab2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.logging.Logger;


@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.createLogger(ControllerServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");

        if (request.getParameter("clear") != null) {
            logger.info("Clear request received");
            request.getRequestDispatcher("/clear").forward(request, response);
            return;
        }
        String x = request.getParameter("x");
        String y = request.getParameter("y");
        String r = request.getParameter("r");
        if (x == null || y == null || r == null) {
            logger.warning("Missing parameters: x, y, r");
            request.setAttribute("error", "Missing required parameters: x, y, r");
            request.getRequestDispatcher("/checkArea").forward(request, response);
            return;
        }
        logger.info("Forwarding to /checkArea with parameters: x=" + x + ", y=" + y + ", r=" + r);
        request.getRequestDispatcher("/checkArea").forward(request, response);


    }
}


