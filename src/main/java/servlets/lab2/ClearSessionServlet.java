package servlets.lab2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@WebServlet("/clear")
public class ClearSessionServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.createLogger(ClearSessionServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("|Clearing session results|");
        getServletContext().setAttribute("results", new ArrayList<Model>());
    }
}
