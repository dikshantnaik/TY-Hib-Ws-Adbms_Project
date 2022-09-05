package main;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public class Hahah {
    public static void main(String[] args) {
	CallableStatement cstmt = null;
	Connection con ;
//	password = digest(password);
	String message;
	try {
	    String SQL = "{call Register3(?, ?, ?,?,?)}";
	    con = Database.initSql();
	    cstmt = con.prepareCall(SQL);
	    cstmt.setString(1, "adadwwd");
	    cstmt.setString(2, "adawdadawd");
	    cstmt.setString(3, "awdawd");
	    cstmt.setString(4, "awdawd");
	    cstmt.registerOutParameter(5, Types.VARCHAR);
	    cstmt.execute();
	    message = cstmt.getString(5);
	    if(message==null) {
		System.out.println("Registered");
	    }
	    else {
		System.out.println("Exists");
	    }
	    
//	    if (message.equals("UsernameExists")) {
//		return new String[] { "UsernameExists", "Username Exists Please Use Another One" };
//	    }
	}catch (Exception e) {
	    e.printStackTrace();
	}
}}
