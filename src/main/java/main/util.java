/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.security.*;
import java.sql.*;
import javax.servlet.http.*;
import javax.servlet.jsp.JspWriter;

/**
 *
 * @author dikshant
 */
public class util {
    static Connection con;
    static PreparedStatement stmt;
    static ResultSet rs;
    static String query;
    static String return_result[];

    public static void alert(JspWriter out, String msg) throws Exception {

	out.println("<script>alert(\"" + msg + "\")</script>");
    }

    public static String[] login(String username, String password) throws SQLException {
	Connection con = null;
	password = digest(password);
	CallableStatement cstmt = null;
	try {
	    String SQL = "{? = call login2(?,?)}";

	    con = Database.initSql();
	    cstmt = con.prepareCall(SQL);
	    cstmt.registerOutParameter(1, Types.VARCHAR);
//		  System.out.println(username);
//		  System.out.println(password);
	    cstmt.setString(2, username);
	    cstmt.setString(3, password);
	    cstmt.execute();
	    String result = cstmt.getString(1);
	    System.out.println(result);
	    if(result.equals("noUser")) {
		return new String[] { "NoUser", "No User Found"};
	    }
	    else if (result.equals("WrongPass")) {
		return new String[] { "wrongPass", "Wrong Creds"};
	    } else {
		return new String[] { "logedin", "Logedin Sucess",result };
	    }
	} catch (SQLException e) {
	    return new String[] { "error", e.toString() };
	}

	finally {
	    cstmt.close();
	    con.close();

	}

    }

    public static String[] register(String username, String password, String student_name, String college_course) {
	CallableStatement cstmt = null;
	password = digest(password);
	String message;
	try {
	    String SQL = "{call Register3(?, ?, ?,?,?)}";
	    con = Database.initSql();
	    cstmt = con.prepareCall(SQL);
	    cstmt.setString(1, username);
	    cstmt.setString(2, student_name);
	    cstmt.setString(3, college_course);
	    cstmt.setString(4, password);
	    cstmt.registerOutParameter(5, Types.VARCHAR);
	    cstmt.execute();
	    message = cstmt.getString(5);
	    if (message==null) {
		return new String[] { "registered", "Registered Success" };
		
	    }
	    else {
		return new String[] { "UsernameExists", "Username Exists Please Use Another One" };
	    }
	 
	}

	catch (Exception e) {
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
	System.out.println(username + course_id);
	String query = "INSERT INTO cart VALUES(NULL,(Select studentid FROM students WHERE username = ?),?)";
	try {
	    Connection con = Database.initSql();
	    PreparedStatement stmt = con.prepareStatement(query);
	    stmt.setString(1, username);
	    stmt.setInt(2, Integer.parseInt(course_id));
	    stmt.executeUpdate();
	    System.out.println(stmt.toString());
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    public static void EnrollCourse(String username) {
	try {
	    CallableStatement cstmt = null;
	    con = Database.initSql();
		    String SQL = "{call EnrollCourse(?)}";
		    cstmt = con.prepareCall(SQL);
		    cstmt.setString(1, username);
		    cstmt.execute();
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

//        Why return an Array ? the First element indicated error code and second represent Message 
	}

	catch (Exception e) {
	    System.out.println(e);
	}

    }

}
