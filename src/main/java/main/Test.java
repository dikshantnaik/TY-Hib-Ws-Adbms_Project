package main;

import PojoFiles.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.UniqueConstraint;
import javax.sound.midi.Soundbank;

import org.hibernate.*;
import org.hibernate.cfg.*;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
public class Test {
    public static void main(String[] args) {
    
	Map<String,Object> query = new HashMap<>();
	query.put("username", "dik");
	query.put("password","pass");
//	query.put("student_name","Dikshant heheh");
//	query.put("college_course","Jsp ");
	System.out.println(query);
	HttpResponse<JsonNode> respone =  WebService.getApiCall("/Student",query);
	System.out.println(respone.getBody().toString());
	String res = respone.getBody().toString();
	JSONObject obj = new JSONObject(res);
	String pageName = obj.toString(0);
	System.out.println(pageName);
    }
}
