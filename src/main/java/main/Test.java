package main;

import PojoFiles.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.UniqueConstraint;
import javax.sound.midi.Soundbank;

import org.hibernate.*;
import org.hibernate.cfg.*;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Test {
    public static void main(String[] args) {

	String username = "Dikkkkk";
	String password = "passass";
	String student_name = "DikIdikkkkk";
	String college_course = "HEHEHEHE";
	String Body = "username=dik2323&password=breadjamd23&student_name=Dikshant23&college_course=science";
	String Body2 = String.format("username=%s&password=%s&student_name=%s&college_course=%s", username, password,
		student_name, college_course);
	String[] res = util.register(username, password, student_name, college_course);
	System.out.println(res[0]);


    }
}
