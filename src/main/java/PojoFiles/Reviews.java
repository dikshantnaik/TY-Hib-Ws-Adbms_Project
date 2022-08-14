package PojoFiles;

import javax.persistence.*;

@Entity
@Table(name = "review")
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    private int id;

    @ManyToOne
    @JoinColumn(name = "studentid")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private AvailableCourse course;

    @Column(name = "students_revive")
    private String studentReview;
    
    @Column(name = "rating")
    private int Rating;
    

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public Student getStudent() {
	return student;
    }

    public void setStudent(Student student) {
	this.student = student;
    }

    public AvailableCourse getCourse() {
	return course;
    }

    public void setCourse(AvailableCourse course) {
	this.course = course;
    }

    public String getStudentReview() {
	return studentReview;
    }

    public void setStudentReview(String studentReview) {
	this.studentReview = studentReview;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }
    
}
