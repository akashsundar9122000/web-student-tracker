package com.project.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDBUtil {

	private DataSource dataSource;
	
	public StudentDBUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
	
	public List<Student> getStudents() throws Exception{
		
		List<Student> students = new ArrayList<>();
		
		Connection myConn = null;
		
		Statement myStmt = null;
		
		ResultSet myRs = null;
		
		try{
			
			//get connection
			
			myConn = dataSource.getConnection();
			
			//create a sql query
			
			String sql = "select * from student order by last_name";
			
			myStmt = myConn.createStatement();
			
			//execute query
			
			myRs = myStmt.executeQuery(sql);
			
			//process result set
			
			while(myRs.next()) {
				
				//retrieve data from result set row
				
				int id = myRs.getInt("id");
				
				String firstName = myRs.getString("first_name");
				
				String lastName = myRs.getString("last_name");
				
				String email  = myRs.getString("email");
				
				//create a new student object
				
				Student tempStudent = new Student(id,firstName,lastName,email);
				
				//add it to the array list
				
				students.add(tempStudent);
			}
			
			return students;
		}
		finally {
			
			//close JDBC objects
			
			close(myConn,myStmt,myRs);
		}
	}


	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		
		try {
			if(myRs!=null) {
				myRs.close();
			}
			
			if(myStmt!=null) {
				myStmt.close();
			}
			
			if(myConn!=null) {
				myConn.close(); //doesn't actually close the connection puts back in the pool where others can use.
			}
		}
		
		catch(Exception exp) {
			exp.printStackTrace();
		}
		
	}


	public void addStudent(Student theStudent) throws Exception {
		
		Connection myConn = null;
		
		PreparedStatement myStmt = null;
		
		try {
			
			//get db connection
			
			myConn = dataSource.getConnection();
			
			// create sql for insert
			
			String sql = "insert into student "
						+ "(first_name, last_name, email) "
						+ "values (?, ?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			//set param values
			
			myStmt.setString(1, theStudent.getFirstName());
			
			myStmt.setString(2, theStudent.getLastName());
			
			myStmt.setString(3, theStudent.getEmail());
			
			//execute sql insert
			 
			myStmt.execute();
		}
		
		finally {
			//clearJDBC objects
			
			close(myConn,myStmt,null); //because no resutltSet
		}
	}


	public Student getStudent(String theStudentId) throws Exception {
		
		Student theStudent = null;
		
		Connection myConn = null;
		
		PreparedStatement myStmt = null;
		
		ResultSet myRs = null;
		
		int studentId;
		
		try{
			
			//convert student id to int 
			
			studentId = Integer.parseInt(theStudentId);
			
			//get database connection 
			
			myConn = dataSource.getConnection();
			
			//sql to get the selected student
			
			String sql = "select * from student where id=?";
			
			//create prepared statement
			
			myStmt = myConn.prepareStatement(sql);
			
			//set the params
			
			myStmt.setInt(1, studentId);
			
			//execute the statement
			
			myRs = myStmt.executeQuery();
			
			//retrieve data from result set row
			
			if(myRs.next()) {
				
				String firstName = myRs.getString("first_name");
				
				String lastName = myRs.getString("last_name");
				
				String email = myRs.getString("email");
				
				//use studentId during construction
				
				theStudent = new Student(studentId, firstName, lastName, email);
			}
			
			else {
				
				throw new Exception("Could not find the student id " + studentId);
			}
		
			return theStudent;
			
		}
		
		finally {
			
			//close the JDBC objects 
			
			close(myConn,myStmt,myRs);
		}
	}


	public void updateStudent(Student theStudent) throws Exception{
		
		Connection myConn = null;
		
		PreparedStatement myStmt = null;
		
		try {
			
			//get db connection 
			
			myConn = dataSource.getConnection();
			
			//write sql query for update student
			
			String sql = "update student "
						+ "set first_name=?, last_name=?, email=? "
						+ "where id=?";
			
			//prepare statememt
			
			myStmt = myConn.prepareCall(sql);
			
			//set params 
			
			myStmt.setString(1, theStudent.getFirstName());
			
			myStmt.setString(2, theStudent.getLastName());
			
			myStmt.setString(3, theStudent.getEmail());
			
			myStmt.setInt(4, theStudent.getId());
		
			//execute statement 
			
			myStmt.execute();
			
		}
		
		finally{
			
			//close the JDBC objects 
			
			close(myConn,myStmt,null);
		}
	}


	public void deleteStudent(String theStudentId) throws Exception {
		
		Connection myConn = null;
		
		PreparedStatement myStmt = null;
		
		try {
			
			//convert student id to int
			
			int studentId = Integer.parseInt(theStudentId);
			
			//get connection to databse
			
			myConn = dataSource.getConnection();
			
			//create sql query
			
			String sql = "delete from student where id=?";
			
			//preapre the statement
			
			myStmt = myConn.prepareStatement(sql);
			
			//set the params
			
			myStmt.setInt(1, studentId);
			
			//execute query 
			
			myStmt.execute();
		}
		
		finally {
			
			//close the JDBC objects
			
			close(myConn,myStmt,null);
		}
		
	}
}
