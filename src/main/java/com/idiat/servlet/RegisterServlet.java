package com.idiat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final String QUERY = "INSERT INTO BOOKDATA (BOOKNAME, BOOKEDITION, BOOKPRICE) VALUES (?, ?, ?)";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");
        
        String bookName = req.getParameter("bookName");
        String bookEdition = req.getParameter("bookEdition");
        float bookPrice = Float.parseFloat(req.getParameter("bookPrice"));
        
        try {
            bookPrice = Float.parseFloat(req.getParameter("bookPrice"));
        } catch (NumberFormatException e) {
            pw.println("<h2>Invalid book price format</h2>");
            pw.println("<a href='home.html'>Home</a>");
            pw.println("<br>");
            pw.println("<a href='bookList'>Book List</a>");
            return;
        }

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            pw.println("<h2>Driver class not found</h2>");
            pw.println("<a href='home.html'>Home</a>");
            pw.println("<br>");
            pw.println("<a href='bookList'>Book List</a>");
            return;
        }
        
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "root");
             PreparedStatement ps = con.prepareStatement(QUERY)) {

            ps.setString(1, bookName);
            ps.setString(2, bookEdition);
            ps.setFloat(3, bookPrice);
            
            int count = ps.executeUpdate();
            if (count == 1) {
                pw.println("<h2>Record is registered successfully</h2>");
            } else {
                pw.println("<h2>Record is not registered successfully</h2>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            pw.println("<h1>SQL Error: " + e.getMessage() + "</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>Error: " + e.getMessage() + "</h1>");
        }

        pw.println("<a href='home.html'>Home</a>");
        pw.println("<br>");
        pw.println("<a href='bookList'>Book List</a>");
    }
}
