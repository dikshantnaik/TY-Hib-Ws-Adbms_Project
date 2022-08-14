package PojoFiles;


public class HibUtils {

	public static AvailableCourse insertCourse(String cname, int cprice, String cdiscription, String offerdBy,String course_duaration) {
		AvailableCourse course = new AvailableCourse();
		course.setCourse_name(cname);
		course.setCourse_price(cprice);
		course.setCourse_discription(cdiscription);
		course.setOfferdBy(offerdBy);
		course.setCourse_duaration(course_duaration);

		return course;
	}
}
