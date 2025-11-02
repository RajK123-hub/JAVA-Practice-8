package com.nimbus.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {

    private static final String URL = "jdbc:mysql://localhost:3306/nimbusdb";
    private static final String USER = "root";
    private static final String PASS = "your_mysql_password";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String empIdParam = request.getParameter("empId");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);

            out.println("<h2>Employee Records</h2>");
            out.println("<form method='get' action='employees'>");
            out.println("Search by Employee ID: <input type='text' name='empId'>");
            out.println("<input type='submit' value='Search'>");
            out.println("</form><br>");

            String query;
            PreparedStatement ps;

            if (empIdParam != null && !empIdParam.isEmpty()) {
                query = "SELECT * FROM Employee WHERE EmpID = ?";
                ps = con.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(empIdParam));
            } else {
                query = "SELECT * FROM Employee";
                ps = con.prepareStatement(query);
            }

            ResultSet rs = ps.executeQuery();

            out.println("<table border='1' cellpadding='8'>");
            out.println("<tr><th>EmpID</th><th>Name</th><th>Salary</th></tr>");

            boolean found = false;
            while (rs.next()) {
                found = true;
                out.println("<tr>");
                out.println("<td>" + rs.getInt("EmpID") + "</td>");
                out.println("<td>" + rs.getString("Name") + "</td>");
                out.println("<td>" + rs.getDouble("Salary") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            if (!found) {
                out.println("<p>No employee found with the given ID.</p>");
            }

            con.close();

        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
