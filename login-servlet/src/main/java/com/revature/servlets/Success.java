package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.resources.ConnectionUtil;

@WebServlet("/success")
public class Success extends HttpServlet {
	@Override
	protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			PreparedStatement ps = null;
			String sql = "SELECT COUNT(*) FROM ERS_USERS WHERE U_USERNAME = ? AND U_PASSWORD = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int pwCheck = rs.getInt("COUNT(*)");
				if (pwCheck == 1) {
					RequestDispatcher rd = req.getRequestDispatcher("success.html");
					rd.forward(req, resp);
				}
				else {
					RequestDispatcher rd = req.getRequestDispatcher("/home");
					rd.forward(req, resp);
				}
				
			} 
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException e) {
			e.getMessage();
		}
		
	}
}
