package main;

import org.hibernate.Session;
import org.hibernate.Transaction;

import PojoFiles.*;
public class Test2 {
    public static void main(String[] args) {
	Session session = util.getSession();
	Transaction t = session.beginTransaction();
	
	t.commit();
	session.close();
    }
}
