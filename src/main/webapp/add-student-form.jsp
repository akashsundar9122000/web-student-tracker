<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Student</title>
<link type="text/css" rel="stylesheet" href="CSS/style.css">
<link type="text/css" rel="stylesheet" href="CSS/add-student-style.css">
</head>
<body>
<div id="wrapper">
		<div id="header">
			<h2>AKASH UNIVERSITY</h2>
		</div>
	</div>

	<div id="container">
		<h3>Add Student</h3>
		<form action="StudentControllerServlet" method="GET">
			<input type="hidden" name="command" value="ADD"/>
			<table>
				<tbody>
					<tr>
						<td><label>First Name:</label></td>
						<td><input type="text" name="firstName"/>
					</tr>
					<tr>
						<td><label>Last Name:</label></td>
						<td><input type="text" name="lastName"/>
					</tr>
					<tr>
						<td><label>Email:</label></td>
						<td><input type="email" name="email"/>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Save" class="save"/>
					</tr>
				</tbody>
			</table>
		</form>
		<div style="clear:both;"></div>
		<p>
			<a href="StudentControllerServlet">Back to the list</a>
		</p>
	</div>
</body>
</html>