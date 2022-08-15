package PojoFiles;


import javax.persistence.*;

@Entity
@Table(name = "payment")
public class Payment {

    public Payment( Student student, String card_holder_name, int card_no, String card_edate, int card_cvv,
	    int course_price, int transaction_fee, int total) {
	super();
	this.student = student;
	this.card_holder_name = card_holder_name;
	this.card_no = card_no;
	this.card_edate = card_edate;
	this.card_cvv = card_cvv;
	this.course_price = course_price;
	this.transaction_fee = transaction_fee;
	this.total = total;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private String card_holder_name;
    private int card_no;

    @Column(name = "card_edate")
    private String card_edate;
    private int card_cvv;
    private int course_price;
    private int transaction_fee;
    private int total;

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

    public String getCard_holder_name() {
	return card_holder_name;
    }

    public void setCard_holder_name(String card_holder_name) {
	this.card_holder_name = card_holder_name;
    }

    public int getCard_no() {
	return card_no;
    }

    public void setCard_no(int card_no) {
	this.card_no = card_no;
    }

    public String getCard_edate() {
	return card_edate;
    }

    public void setCard_edate(String card_edate) {
	this.card_edate = card_edate;
    }

    public int getCard_cvv() {
	return card_cvv;
    }

    public void setCard_cvv(int card_cvv) {
	this.card_cvv = card_cvv;
    }

    public int getCourse_price() {
	return course_price;
    }

    public void setCourse_price(int course_price) {
	this.course_price = course_price;
    }

    public int getTransaction_fee() {
	return transaction_fee;
    }

    public void setTransaction_fee(int transaction_fee) {
	this.transaction_fee = transaction_fee;
    }

    public int getTotal() {
	return total;
    }

    public void setTotal(int total) {
	this.total = total;
    }

}
