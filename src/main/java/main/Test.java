package main;

import PojoFiles.*;


import javax.persistence.UniqueConstraint;

import org.hibernate.*;
import org.hibernate.cfg.*;
public class Test {
    public static void main(String[] args) {
	try {
	    
	    SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	    Session session = factory.openSession();
	    Transaction tx = session.beginTransaction();
	    
	    util.Review("BESTTT", "9", "dik");
//	    
//	    tx.commit();
//	    
//	    session.close();
//	    factory.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
    }
}
