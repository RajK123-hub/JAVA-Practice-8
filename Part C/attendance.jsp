<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Student Attendance Portal</title>
</head>
<body>
  <h2>Record Attendance</h2>
  <form method="post" action="attendance">
    Student ID: <input type="text" name="studentId" required><br><br>
    Date: <input type="date" name="attDate" required><br><br>
    Status:
    <select name="status">
      <option value="Present">Present</option>
      <option value="Absent">Absent</option>
      <option value="Late">Late</option>
    </select><br><br>
    <input type="submit" value="Submit Attendance">
  </form>
</body>
</html>
