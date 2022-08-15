package main;

import PojoFiles.*;

import java.util.List;

import javax.persistence.UniqueConstraint;
import javax.sound.midi.Soundbank;

import org.hibernate.*;
import org.hibernate.cfg.*;
public class Test {
    public static void main(String[] args) {
	try {
	    
	    SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	    Session session = factory.openSession();
	    Transaction tx = session.beginTransaction();
            String Selecting_Query = 	"SELECT new list(S.id,C.course.id)"+
        	    			" FROM Cart C,Student S "
        	    			+ "WHERE S.id=C.student "
        	    			+ "AND S.username = :user_name "
        	    			+ "GROUP BY C.course.id";
//            	+ "WHERE sid=students.studentid and username = \"" + "d"+ "\"";
            Query query = session.createQuery(Selecting_Query);
            query.setParameter("user_name", "dik");
            List<List> ResultList = query.list();
            for (List list: ResultList) {
        	EnrolledCourse enrolledCourse = new EnrolledCourse();
        	enrolledCourse.setStudent((Student)session.load(Student.class, (int)list.get(0)));
        	enrolledCourse.setCourse((AvailableCourse)session.load(AvailableCourse.class, (int)list.get(1)));
        	
        	session.save(enrolledCourse);
	    }
            String hql = "Delete from Cart Where sid = (SELECT S.id FROM Student S WHERE username = :user_name) ";
            Query query2 = session.createQuery(hql);
            query2.setParameter("user_name", "dik")
            tx.commit();
            session.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
    }
}
