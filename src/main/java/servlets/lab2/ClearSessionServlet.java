package servlets.lab2;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet("/clear")
public class ClearSessionServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.createLogger(ClearSessionServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("results");
            LoggerFactory.logInfo(logger, "Session cleared", Map.of(
                    "action", "clear",
                    "status", "success"
            ));
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
