package servlets.lab2;

import jakarta.servlet.ServletException;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Date currentTime = new Date();
        long startTime = System.nanoTime();

        double x, y, r;
        boolean valid = true;

        try {
            x = Double.parseDouble(request.getParameter("x"));
            y = Double.parseDouble(request.getParameter("y"));
            r = Double.parseDouble(request.getParameter("r"));

            if (x < -5 || x > 3 || y < -5 || y > 3 || r < 1 || r > 4) {
                valid = false;
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                LoggerFactory.logInfo(logger, "Invalid parameters", Map.of(
                        "x", x,
                        "y", y,
                        "r", r,
                        "status", "400 Bad Request"
                ));
                response.getWriter().write("{\"error\": \"Invalid parameters\"}");
            }
        } catch (NumberFormatException | NullPointerException e) {
            LoggerFactory.logInfo(logger, "Invalid request parameters", Map.of(
                    "error", e.getMessage()
            ));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (valid) {
            boolean hit = checkArea(x, y, r);
            long executionTime = System.nanoTime() - startTime;
            double timeMicsec = (double) executionTime / 1000.0;

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
            String formattedDate = dateFormat.format(currentTime);

            LoggerFactory.logInfo(logger, "Area check result", Map.of(
                    "x", x,
                    "y", y,
                    "r", r,
                    "hit", hit,
                    "executionTime", timeMicsec + " microsec"
            ));

            HttpSession session = request.getSession();
            List<Model> results = (List<Model>) session.getAttribute("results");
            if (results == null) {
                results = new ArrayList<>();
                session.setAttribute("results", results);
            }
            results.add(new Model(x, y, r, hit, formattedDate, timeMicsec));

            try (PrintWriter out = response.getWriter()) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print(String.format(Locale.ENGLISH,
                        "{\"x\": %.2f, \"y\": %.2f, \"r\": %.2f, \"hit\": %s, \"time\": \"%s\", \"execution\": %.2f}",
                        x, y, r, hit, formattedDate, timeMicsec));
            }catch (IOException e) {
                LoggerFactory.logInfo(logger, "Error writing response", Map.of(
                        "error", e.getMessage()
                ));
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\": \"Internal Server Error\"}");
            }
        }
    }

    private boolean checkArea(double x, double y, double r) {
        return (x >= 0 && y >= 0 && x <= r && y <= r / 2) ||
                (x <= 0 && y <= 0 && y >= -x - r / 2) ||
                (x <= 0 && y >= 0 && Math.sqrt(x * x + y * y) <= r / 2);
    }
}
