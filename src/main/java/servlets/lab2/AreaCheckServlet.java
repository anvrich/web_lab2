
package servlets.lab2;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

@WebServlet("/checkArea")
public class AreaCheckServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.createLogger(AreaCheckServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        String error = (String) request.getAttribute("error");
        if (error != null) {
            logger.warning("Error: " + error);
            sendErrorResponse(response, error);
            return;
        }

        double x, y, r;
        try {
            x = Double.parseDouble(request.getParameter("x"));
            y = Double.parseDouble(request.getParameter("y"));
            r = Double.parseDouble(request.getParameter("r"));
        } catch (NumberFormatException e) {
            logger.warning("Invalid number format");
            sendErrorResponse(response, "Invalid number format");
            return;
        }

        if (!isValidRange(x, y, r)) {
            logger.warning(String.format("Invalid parameters: x=%.2f, y=%.2f, r=%.2f", x, y, r));
            sendErrorResponse(response, String.format(Locale.US, "Invalid parameters: x=%.2f, y=%.2f, r=%.2f", x, y, r));
            return;
        }

        long startTime = System.nanoTime();
        boolean hit = checkArea(x, y, r);
        double executionTime = (System.nanoTime() - startTime) / 1000.0;

        String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        LoggerFactory.logInfo(logger, "Area check result", Map.of("x", x, "y", y, "r", r, "hit", hit, "executionTime", executionTime + " microsec"));

        HttpSession session = request.getSession();
        List<Model> results = (List<Model>) session.getAttribute("results");
        if (results == null) {
            results = new ArrayList<>();
            session.setAttribute("results", results);
        }
        results.add(new Model(x, y, r, hit, formattedDate, executionTime));

        sendSuccessResponse(response, x, y, r, hit, formattedDate, executionTime);
    }

    private boolean isValidRange(double x, double y, double r) {
        return x >= -5 && x <= 3 && y >= -5 && y <= 3 && r >= 1 && r <= 4;
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        try (PrintWriter out = response.getWriter()) {
            out.print(String.format("{\"error\": \"%s\"}", message));
        }
    }


    private void sendSuccessResponse(HttpServletResponse response, double x, double y, double r, boolean hit, String formattedDate, double executionTime) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(String.format(Locale.ENGLISH,
                    "{\"x\": %.2f, \"y\": %.2f, \"r\": %.2f, \"hit\": %s, \"time\": \"%s\", \"execution\": %.2f}",
                    x, y, r, hit, formattedDate, executionTime));
        }
    }

    private boolean checkArea(double x, double y, double r) {
        return (x >= 0 && y >= 0 && x <= r && y <= r / 2) ||
                (x <= 0 && y <= 0 && y >= -x - r / 2) ||
                (x <= 0 && y >= 0 && Math.sqrt(x * x + y * y) <= r / 2);
    }
}