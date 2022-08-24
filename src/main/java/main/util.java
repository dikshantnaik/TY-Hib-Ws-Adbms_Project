/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.security.*;
import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.*;
import javax.servlet.jsp.JspWriter;

import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

import PojoFiles.*;

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
	Map<String,Object> parameter = new HashMap<>();
	parameter.put("username", username);
	parameter.put("password",password);
	String respone =  WebService.getApiCall("/Student",parameter).getBody().toString();
	
    }

    public static String[] register(String username, String password, String student_name, String college_course) {
	password = digest(password);
	try {
	    Session session = getSession();
	    Transaction t = session.beginTransaction();
	    Student s1 = new Student();
//			s1.setId(155);
	    s1.setStudent_name(student_name);
	    s1.setCollege_course(college_course);
	    s1.setUser_password(password);
	    s1.setUsername(username);

	    session.save(s1);
	    t.commit();

	    session.close();

	    return new String[] { "registered", "Registered Success" };

	} catch (JDBCException e) {
	    SQLException cause = (SQLException) e.getCause();

	    if (cause.getErrorCode() == 1062) {
		return new String[] { "usernameExists", "Username Taken Please use Different One" };
	    }
	    return new String[] { "error", e.toString() };

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
	Session session = getSession();
	Transaction t = session.beginTransaction();
	Reviews r = new Reviews();
	Query query = session.createQuery("from Student where username = :user_name");
	query.setParameter("user_name", username);
	List<Student> result = query.list();
	r.setCourse((AvailableCourse) session.get(AvailableCourse.class, Integer.parseInt(course_id)));
	r.setStudent((Student) session.get(Student.class, result.get(0).getId()));
	r.setStudentReview(review);
	session.save(r);
	t.commit();
	session.close();

	return "Done";
    }

    public static void removeItemFromCart(String cid, String username) {
	Session session = getSession();
	Transaction t = session.beginTransaction();
	String hql = "Delete from Cart Where cid = :c_id AND sid = (SELECT S.id FROM Student S WHERE username = :user_name) ";
	Query query = session.createQuery(hql);
	query.setParameter("c_id", cid);
	query.setParameter("user_name", username);
	query.executeUpdate();
	t.commit();
	session.close();

    }

    public static void addItemToCart(String username, String course_id) {
	Session session = getSession();
	Transaction t = session.beginTransaction();
	Cart cart = new Cart();
	cart.setCourse((AvailableCourse) session.load(AvailableCourse.class, Integer.parseInt(course_id)));

	Query query = session.createQuery("from Student where username = :user_name");
	query.setParameter("user_name", username);
	List<Student> result = query.list();
	cart.setStudent((Student) session.load(Student.class, result.get(0).getId()));
	session.save(cart);
	t.commit();
	session.close();

    }

    public static void EnrollCourse(String username) {
	Session session = getSession();
	    Transaction tx = session.beginTransaction();
        String Selecting_Query = 	"SELECT new list(S.id,C.course.id)"+
    	    			" FROM Cart C,Student S "
    	    			+ "WHERE S.id=C.student "
    	    			+ "AND S.username = :user_name "
    	    			+ "GROUP BY C.course.id";
//        	+ "WHERE sid=students.studentid and username = \"" + "d"+ "\"";
        Query query = session.createQuery(Selecting_Query);
        query.setParameter("user_name", username);
        List<List> ResultList = query.list();
        for (List list: ResultList) {
    	EnrolledCourse enrolledCourse = new EnrolledCourse();
    	enrolledCourse.setStudent((Student)session.load(Student.class, (int)list.get(0)));
    	enrolledCourse.setCourse((AvailableCourse)session.load(AvailableCourse.class, (int)list.get(1)));
    	
    	session.save(enrolledCourse);
	    }
        String hql = "Delete from Cart Where sid = (SELECT S.id FROM Student S WHERE username = :user_name) ";
        Query query2 = session.createQuery(hql);
        query2.setParameter("user_name", username);
        query2.executeUpdate();
        tx.commit();
        session.close();
    }

    public static void Payment(String username, String name, String card_no, String card_edate, String cvv,
	    String course_price, String trans_fee, String total) {
	Session session = getSession();
	Transaction t = session.beginTransaction();
	Query query = session.createQuery("from Student where username = :user_name");
	query.setParameter("user_name", username);
	List<Student> result = query.list();

	Payment payment = new Payment((Student) session.load(Student.class, result.get(0).getId()), name,
		Integer.parseInt(card_no), card_edate, Integer.parseInt(cvv), Integer.parseInt(course_price),
		Math.round(Float.parseFloat(trans_fee)), Math.round(Float.parseFloat(total)));
	session.save(payment);
	t.commit();
	session.close();
	util.EnrollCourse(username);

    }
}
