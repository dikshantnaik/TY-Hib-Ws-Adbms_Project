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

	Map<String, Object> params = new HashMap<>();
	params.put("username", "dik");
	params.put("cid", "2");
//	String Body2 = String.format("username=%s&password=%s&student_name=%s&college_course=%s", username, password,
//		student_name, college_course);
	String[] res = util.removeItemFromCart(null, null);
	System.out.println(res[0]);


    }
}
