package com.idiat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {
    private static final String query = "SELECT BOOKNAME, BOOKEDITION, BOOKPRICE FROM BOOKDATA WHERE id=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");
        int id=Integer.parseInt(req.getParameter("id"));
        // Check if 'id' parameter is provided and valid
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish database connection
        Connection    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "root");
            // Create prepared statement
        PreparedStatement    ps = con.prepareStatement(query);
            // Execute query
            ps.setInt(1, id);
          ResultSet rs   = ps.executeQuery();

            if (rs.next()) {
                pw.println("<form action='editurl?id=" + id + "' method='Get'>");
                pw.println("<table align='center'>");
                pw.println("<tr>");
                pw.println("<td>Book Name</td>");
                pw.println("<td><input type='text' name='bookName' value='" + rs.getString(1) + "'></td>");
                pw.println("</tr>");
                pw.println("<tr>");
                pw.println("<td>Book Edition</td>");
                pw.println("<td><input type='text' name='bookEdition' value='" + rs.getString(2) + "'></td>");
                pw.println("</tr>");
                pw.println("<tr>");
                pw.println("<td>Book Price</td>");
                pw.println("<td><input type='text' name='bookPrice' value='" + rs.getFloat(3) + "'></td>");
                pw.println("</tr>");
                pw.println("<tr>");
                pw.println("<td><input type='submit' value='Edit'></td>");
                pw.println("<td><input type='reset' value='Cancel'></td>");
                pw.println("</tr>");
                pw.println("</table>");
                pw.println("</form>");
          
            }
        }
        catch (SQLException | ClassNotFoundException e) { 
            e.printStackTrace();
        
        }
        pw.println("<a href='home.html'>GO to Home</a>");
    }
}
    


