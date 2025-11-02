package com.nimbus.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/attendance")
public class AttendanceServlet extends HttpServlet {

    private static final String URL = "jdbc:mysql://localhost:3306/nimbusdb";
    private static final String USER = "root";
    private static final String PASS = "your_mysql_password";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String studentId = request.getParameter("studentId");
        String attDate = request.getParameter("attDate");
        String status = request.getParameter("status");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);

            String query = "INSERT INTO Attendance (studentId, attDate, status) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, studentId);
            ps.setDate(2, java.sql.Date.valueOf(attDate));
            ps.setString(3, status);

            int inserted = ps.executeUpdate();

            if (inserted > 0) {
                out.println("<h3>Attendance Recorded Successfully!</h3>");
                out.println("<p>Student ID: " + studentId + "</p>");
                out.println("<a href='attendance.jsp'>Go Back</a>");
            } else {
                out.println("<h3>Failed to record attendance!</h3>");
            }

            con.close();

        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
