package com.project.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;


import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//Define datasource/connection pool for resource injection
	
	
	@Resource(name="jdbc/web_student_tracker")
	
	private DataSource dataSource;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//step-1: set up the printwriter
		
		PrintWriter out = response.getWriter();
		
		response.setContentType("text/plain");

		//step-2:connect to database 
		
		Connection myConn = null;
		
		Statement myStmnt = null;
		
		ResultSet myRs = null;
		
		try {
			
			myConn = dataSource.getConnection();
			
			//step-3: create a sql statement
			
			String sql = "select * from student";
			
			myStmnt = myConn.createStatement();
			
			//step-4 execute sql query
			
			myRs = myStmnt.executeQuery(sql);
			
			while(myRs.next()) {
				
				String email = myRs.getString("email");
				
				out.println(email);
			}
		}
		
		catch(Exception exp) {
			exp.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	


}
