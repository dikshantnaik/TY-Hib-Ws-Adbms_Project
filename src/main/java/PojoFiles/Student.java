package PojoFiles;

import javax.persistence.*;


@Table(name = "students")
@Entity
public class Student {
	@Id
	@Column(name = "studentid")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	@Column(unique = true)
	private String username;
	private String student_name;
	private String college_course;
	private String user_password;
	public int getId() {
	    return id;
	}
	public void setId(int id) {
	    this.id = id;
	}
	public String getUsername() {
	    return username;
	}
	public void setUsername(String username) {
	    this.username = username;
	}
	public String getStudent_name() {
	    return student_name;
	}
	public void setStudent_name(String student_name) {
	    this.student_name = student_name;
	}
	public String getCollege_course() {
	    return college_course;
	}
	public void setCollege_course(String college_course) {
	    this.college_course = college_course;
	}
	public String getUser_password() {
	    return user_password;
	}
	public void setUser_password(String user_password) {
	    this.user_password = user_password;
	}
	
	
	
	
	
}
