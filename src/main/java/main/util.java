/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.security.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.*;
import javax.servlet.jsp.JspWriter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.json.JSONException;
import org.json.JSONObject;



/**
 *
 * @author dikshant
 */

@SuppressWarnings("deprecation")
public class util {
    static Connection con;
    static PreparedStatement stmt;
    static ResultSet rs;
//    static String query;
    static String return_result[];

    public static void alert(JspWriter out, String msg) throws Exception {

	out.println("<script>alert(\"" + msg + "\")</script>");
    }

    public static Session getSession() {
	SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	Session session = factory.openSession();
	return session;
    }

    public static String[] login(String username, String password) {
	Map<String, Object> parameter = new HashMap<>();
	parameter.put("username", username);
	parameter.put("password", password);
	String respone = WebService.getApiCall("/Student", parameter).getBody().toString();
	JSONObject obj = new JSONObject(respone);
	try {
	    String name = obj.getString("logedin");
	    return new String[] { "logedin", "Sucessfully Logedin", name };
	} catch (JSONException e) {
	    return new String[] { "Wrong creds", "Invalid Credantials" };

	    // TODO: handle exception
	}

    }

    public static String[] register(String username, String password, String student_name, String college_course) {
	password = digest(password);
	String Body2 = String.format("username=%s&password=%s&student_name=%s&college_course=%s", username, password,
		student_name, college_course);
	try {
	    String response = WebService.postApiCall("/Student", Body2).getBody().toString();
	    JSONObject obj = new JSONObject(response);
	    obj.getString("registered");
	    return new String[] { "registered", "Registered Success" };

	} catch (JSONException e) {
		return new String[] { "usernameExists", "Username Taken Please use Different One" };

	} catch (Exception e) {
	    return new String[] { "error", e.toString() };
	}
    }

    public static void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

	HttpSession session = request.getSession();
	session.invalidate();
	Cookie[] ck = request.getCookies();
	for (int i = 0; i < ck.length; i++) {
	    if (ck[i].getName().equals("name") || ck[i].getName().equals("username")) {
		ck[i].setMaxAge(0);
		response.addCookie(ck[i]);
		response.sendRedirect("index.jsp");
	    }
	}
    }

    public static void rememberMe(HttpServletRequest request, HttpServletResponse response) {
	HttpSession session = request.getSession();
	Cookie ck = new Cookie("username", session.getAttribute("username").toString());
	response.addCookie(ck);
    }

    public static String digest(String msg) {
	final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	MessageDigest md;
	try {
	    md = MessageDigest.getInstance("SHA-256");
	    md.update(msg.getBytes());
	    byte bytes[] = md.digest();

	    char[] hexChars = new char[bytes.length * 2];
	    for (int j = 0; j < bytes.length; j++) {
		int v = bytes[j] & 0xFF;
		hexChars[j * 2] = HEX_ARRAY[v >>> 4];
		hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
	    }
	    return new String(hexChars);
	} catch (NoSuchAlgorithmException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return null;
	}

    }
    public static String Review(String review, String course_id, String username) {
   	try {
   	    String query = "INSERT INTO review VALUES(NULL,?,(SELECT studentid FROM students WHERE username = ?),NULL,?)";
   	    Connection con = Database.initSql();
   	    PreparedStatement stmt = con.prepareStatement(query);
   	    stmt.setString(1, course_id);
   	    stmt.setString(2, username);
   	    stmt.setString(3, review);
   	    stmt.executeUpdate();
   	    return "Done";
   	} catch (Exception e) {
   	    System.out.println(e);
   	    return e.toString();
   	}

       }

       public static void removeItemFromCart(String cid, String username) {
   	try {
   	    String query = "DELETE FROM cart WHERE sid = (SELECT studentid from students WHERE username=\"" + username
   		    + "\") and  cid= " + cid;

   	    Connection con = Database.initSql();
   	    PreparedStatement stmt = con.prepareStatement(query);
   	    stmt.execute();

   	} catch (SQLException e) {
   	    System.out.println(e);
   	}
       }

       public static void addItemToCart(String username, String course_id) {
   	String query = "INSERT INTO cart VALUES((Select studentid FROM students WHERE username = ?),?)";
   	try {
   	    Connection con = Database.initSql();
   	    PreparedStatement stmt = con.prepareStatement(query);
   	    stmt.setString(1, username);
   	    stmt.setInt(2, Integer.parseInt(course_id));
   	    stmt.executeUpdate();
   	    System.out.println(stmt.toString());
   	} catch (SQLException e) {
   	    System.out.println(e);
   	}
       }

       public static void EnrollCourse(String username) {
   	try {
   	    String Selecting_Query = "SELECT sid,cid FROM cart,students WHERE sid=students.studentid and username = \""
   		    + username + "\"";
   	    String Insrting_query;
   	    String sid;
   	    Connection con = Database.initSql();
   	    PreparedStatement Select_stmt = con.prepareStatement(Selecting_Query);
   	    ResultSet rs = Select_stmt.executeQuery();
   	    while (rs.next()) {
   		sid = rs.getString("sid");
   		Insrting_query = "INSERT INTO `enrolled_course`(id,student_id,course_id) VALUES (NULL,'" + sid + "' , '"
   			+ rs.getString("cid") + "')";
   		PreparedStatement Insert_stmt = con.prepareCall(Insrting_query);
   		Insert_stmt.executeUpdate();
   	    }
   	    String Delete_Query = "DELETE FROM `cart` WHERE sid=(Select studentid FROM students where username = '"
   		    + username + "')";
   	    PreparedStatement Delete_stmt = con.prepareStatement(Delete_Query);
   	    Delete_stmt.executeUpdate();

   	} catch (SQLException e) {
   	    System.out.println(e);
   	}
       }

       public static void Payment(String username, String name, String card_no, String card_edate, String cvv,
   	    String course_price, String trans_fee, String total) {

   	String query = "Insert Into payment VALUES(NULL,(SELECT students.studentid FROM students WHERE students.username = ? ),?,?,?,?,?,?,?)";

   	try {
   	    con = Database.initSql();
   	    stmt = con.prepareStatement(query);
   	    stmt.setString(1, username);
   	    stmt.setString(2, name);
   	    stmt.setString(3, card_no);
   	    stmt.setString(4, card_edate);
   	    stmt.setString(5, cvv);
   	    stmt.setString(6, course_price);
   	    stmt.setString(7, trans_fee);
   	    stmt.setString(8, total);
   	    stmt.executeUpdate();
   	    util.EnrollCourse(username);
   	}
   	  catch (Exception e) {
   	      e.printStackTrace();
   	  }

//           Why return an Array ? the First element indicated error code and second represent Message 
   	}}

