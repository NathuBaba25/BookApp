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
@WebServlet("/deleteurl")
public class DeleteServlet extends HttpServlet {
	String query = "DELETE FROM BOOKDATA WHERE id = ?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");
        //int id=Integer.parseInt(req.getParameter("id"));
        // Retrieve parameters from the request
        int  id = Integer.parseInt(req.getParameter("id"));
        
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        }
        	catch (ClassNotFoundException e) {
        		System.out.println(e);
        	}
				// TODO: handle exception
			
        	try {
        	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "root");
        
             PreparedStatement ps = con.prepareStatement(query);{

              ps.setInt(1, id);
            // Execute the update
            int count = ps.executeUpdate();
            if (count > 0) {
                pw.println("<h2>Record Delete  successfully</h2>");
            } else {
                pw.println("<h2>Record not Delete successfully</h2>");
            }
             }
        } catch (SQLException e) {
            e.printStackTrace();
            pw.println("<h2>Error: SQL exception</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h2>" + e.getMessage() + "</h2>");
        }

        // Provide navigation links
        pw.println("<a href='home.html'>Home</a>");
        pw.println("<br>");
        pw.println("<a href='booklist'>Book List</a>");
    }
}


