package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "HelloServlet", urlPatterns = {"/insert"})
public class HelloServlet extends HttpServlet {
    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ommar";  // Your DB URL
    private static final String DB_USER = "root";                               // Your DB username
    private static final String DB_PASSWORD = "arardzstar";                               // Your DB password (empty if none)

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Variables to insert
        String firstName = "John";
        String lastName = "Doe";

        // Insert data into the database
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Step 1: Load the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Establish the connection
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Step 3: Create SQL query
            String sql = "INSERT INTO students (fname, lname) VALUES (?, ?)";

            // Step 4: Create a prepared statement
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);

            // Step 5: Execute the query
            int rowsInserted = stmt.executeUpdate();

            // Step 6: Provide feedback in the servlet response
            if (rowsInserted > 0) {
                out.println("<h1>Data successfully inserted!</h1>");
            } else {
                out.println("<h1>Failed to insert data.</h1>");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            out.println("<h1>Error: " + e.getMessage() + "</h1>");
        } finally {
            // Step 7: Close resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
