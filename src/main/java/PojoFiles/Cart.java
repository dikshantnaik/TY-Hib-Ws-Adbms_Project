package PojoFiles;

import javax.persistence.*;

@Entity
@Table(name="cart")

public class Cart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "sid")
    private Student student;
    
    @ManyToOne
    @JoinColumn(name = "cid")
    private AvailableCourse course;

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
    
    
}
