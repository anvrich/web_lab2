package servlets.lab2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@WebServlet("/checkArea")
public class AreaCheckServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.createLogger(AreaCheckServlet.class.getName());


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Date currentTime = new Date();
        long startTime = System.nanoTime();

        double x, y, r;
        try {
            x = Double.parseDouble(request.getParameter("x"));
            y = Double.parseDouble(request.getParameter("y"));
            r = Double.parseDouble(request.getParameter("r"));

            // Server-side validation
            if (x < -5 || x > 3 || y < -5 || y > 3 || r < 1 || r > 4) {
                logger.warning("Validation failed: x=" + x + ", y=" + y + ", r=" + r);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Invalid input values. X: -5 to 3, Y: -5 to 3, R: 1 to 4.\"}");
                return;
            }

            logger.info("|Processing request|: x=" + x + ", y=" + y + ", r=" + r);
        } catch (NumberFormatException | NullPointerException e) {
            logger.severe("Invalid parameters: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Invalid parameters\"}");
            return;
        }

        boolean hit = checkArea(x, y, r);
        long executionTime = System.nanoTime() - startTime;
        double timeMicsec = (double) executionTime / 1000.0;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        String formattedDate = dateFormat.format(currentTime);

        List<Model> results = (List<Model>) getServletContext().getAttribute("results");
        if (results == null) {
            results = new ArrayList<>();
            getServletContext().setAttribute("results", results);
        }
        double roundedTimeMicsec = Math.round(timeMicsec * 100.0) / 100.0;
        results.add(new Model(x, y, r, hit, formattedDate, roundedTimeMicsec));
        if (results != null) {
            for (Model res : results) {
                logger.info("Инфо : " + res.toString());
            }
        }else {
            logger.info("No res found in the context");
        }
        logger.info("|Result logged|: hit=" + hit + ", execution time=" + timeMicsec + " microsec");

        try (PrintWriter out = response.getWriter()) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String jsonResponse = String.format(Locale.ENGLISH,
                    "{\"x\": %.2f, \"y\": %.2f, \"r\": %.2f, \"hit\": %s, \"time\": \"%s\", \"execution\": %.2f}",
                    x, y, r, hit, formattedDate, timeMicsec);
            out.print(jsonResponse);
        }
    }

    private boolean checkArea(double x, double y, double r) {
        if (x >= 0 && y >= 0 && x <= r && y <= r / 2) {
            return true;
        }

        if (x <= 0 && y <= 0 && y >= -x - r / 2) {
            return true;
        }

        if (x <= 0 && y >= 0 && Math.sqrt(x * x + y * y) <= r / 2) {
            return true;
        }
        return false;
    }
}
