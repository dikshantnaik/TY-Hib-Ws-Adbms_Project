<%@page import="java.util.List"%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="main.*" %>
<%@page import="java.sql.*" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Enrol for a Course</title>
    <link rel="stylesheet" href="style.css" />
  </head>
  <body>
    <div class="bg-image home-bg"></div>
    <div class="container form">
      <!-- <div style="font-size: 1.5em"> -->
        <h2>REGISTRATION</h2><br><br>
      <form action="Register.jsp" method="post">
          <input name= "username" type="text" class="field" placeholder="Username" /> <br /><br>
        <input name= "name" type="text" class="field" placeholder="Name" /> <br /><br>
       <input name = "email" type="email" class="field" placeholder="Email" > <br><br>
       <input type="password" name="password" id="password " class="field" placeholder="password" /><br>
        <br />
        <input name="college" type="text" class="field" placeholder="ENTER YOUR COLLEGE" /> <br>
      
   
         <span>STREAM :</span>
        <select name="stream" id="degree_year">
          <option value="Computer Science">Computer Science</option>
          <option value="Mecanical">Mecanical</option>
          <option value="Electrical">Electrical</option>
          <option value="Aeronotical">Aeronotical</option>
        </select><br>
        <br />
       
       
        <button type = "submit" id="login" href="index.php" class="button5" value="register" name = "register">Register </button>
      </form>
      <!-- </div> -->
    </div>
  </body>
</html>
