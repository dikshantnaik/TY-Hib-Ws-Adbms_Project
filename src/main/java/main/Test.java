package main;

import PojoFiles.*;

import javax.persistence.UniqueConstraint;

import org.hibernate.*;
import org.hibernate.cfg.*;
public class Test {
    public static void main(String[] args) {
	Student s1 = new Student();
//	s1.setId(155);
	s1.setStudent_name("DikshantFromHibernate");
	s1.setCollege_course("bsc");	
	s1.setUser_password("hibernate");
	s1.setUsername("Hello");
	s1.setUser_password("pass");
	try {
	    
	    SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	    Session session = factory.openSession();
	    Transaction tx = session.beginTransaction();
	    session.save(s1);
	    tx.commit();
	    
	    session.close();
	    factory.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
    }
}
