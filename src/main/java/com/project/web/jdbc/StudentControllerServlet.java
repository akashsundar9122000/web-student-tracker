package com.project.web.jdbc;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


import jakarta.annotation.Resource;
/**
 * Servlet implementation class StudentControllerServlet
 */
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    private StudentDBUtil studentDbUtil;
    
    @Resource(name="jdbc/web_student_tracker")
    
    private DataSource dataSource;

	@Override
	public void init() throws ServletException { //used when sever is initialized by tomcat overwrites our own custom functionality
		super.init();
		
		//create out db util and pass in the conn pool / datasource
		
		try {
			studentDbUtil = new StudentDBUtil(dataSource); 
		}
		catch(Exception exp) {
			throw new ServletException(exp);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//list students in MVC fashion i.e setAttribute, RequestDispatcher, forward
		
		try {
			
			//get the command
			
			String theCommand = request.getParameter("command");
			
			//if the command is missing default is list
			
			if(theCommand==null) {
			
				theCommand="LIST";
			
			}
			
			//route to appropriate method
			
			switch(theCommand) {
			
			case "LIST":
				
				listStudents(request,response);
				
				break;
				
			case "ADD":
				
				addStudents(request,response);
				
				break;
				
			case "LOAD":
				
				loadStudent(request,response);
				
				break;
				
			case "UPDATE":
				
				updateStudent(request,response);
				
				break;
				
			case "DELETE":
				
				deleteStudent(request,response);
				
				break;
				
			default:
				
				listStudents(request,response);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ServletException(e);
		}
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//read student id from form data
		
		String theStudentId = request.getParameter("studentId");
		
		//delete student from the database
		
		studentDbUtil.deleteStudent(theStudentId);
		
		//send them back to list pages
		
		listStudents(request,response);
		
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read student info from form data 
		
		int id = Integer.parseInt(request.getParameter("studentId"));
		
		String firstName = request.getParameter("firstName");
		
		String lastName = request.getParameter("lastName");
		
		String email = request.getParameter("email");
		
		//create a new student object
		
		Student theStudent = new Student(id,firstName,lastName,email);
		
		//perform update on db util
		
		studentDbUtil.updateStudent(theStudent);
		
		//send back to list page
		
		listStudents(request,response);
		
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//read student id from request 
		
		String theStudentId = request.getParameter("studentId");
		
		//get student from database (Db Util)
		
		Student theStudent = studentDbUtil.getStudent(theStudentId);
		
		//place student in request attribute 
		
		request.setAttribute("THE_STUDENT", theStudent);
		
		//send the attribute to jsp page 
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		
		dispatcher.forward(request, response);
		
	}

	private void addStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read student info
		
		String firstName = request.getParameter("firstName");
		
		String lastName = request.getParameter("lastName");
		
		String email = request.getParameter("email");
		
		//create new student object
		
		Student theStudent = new Student(firstName,lastName,email);
		
		//add new student to the database
		
		studentDbUtil.addStudent(theStudent);
		
		//send back to the main page
		
		listStudents(request,response);
		
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get students from db util
		
		List<Student> students = studentDbUtil.getStudents();
		
		//add students to request
		
		request.setAttribute("STUDENT_LIST", students);
		
		//send to jsp page
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		
		dispatcher.forward(request, response);
		
	}

}
