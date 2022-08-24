package main;

import java.util.Map;

import javax.websocket.Endpoint;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;

public class WebService {
    static String SERVER_TOKEN = "eoR2sbxVJKJlJAVcGggEa8abXcgo9sn4";
    static String SERVER_URL = "http://localhost:8080/WebService2";

    public static HttpResponse<String> postApiCall(String EndPoint, String Body) throws UnirestException {
	String Url = SERVER_URL + EndPoint;
//	String EndPoint = "/Student";
//	String Body = "username=dik2323&password=breadjamd23&student_name=Dikshant23&college_course=science";
	HttpRequestWithBody request = Unirest.post(Url);
	HttpResponse<String> response = request.header("content-type", "application/x-www-form-urlencoded")
		.header("authorization", SERVER_TOKEN).body(Body).asString();
	return response;
    }

    public static HttpResponse<JsonNode> getApiCall(String EndPoint, Map<String, Object> parameters) {
	String Url = SERVER_URL + EndPoint;
	try {
	    HttpResponse<JsonNode> jsonResponse = Unirest.get(Url).header("authorization", SERVER_TOKEN)
		    .header("accept", "application/json").queryString(parameters).asJson();
	    return jsonResponse;
	} catch (UnirestException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return null;
	}

    }

    public static void deleteApiCall(String Endpint, Map<String, Object> params) {
	String Url = SERVER_URL + Endpint;
	try {
	    HttpResponse<String> response = Unirest.delete(Url).header("authorization", SERVER_TOKEN)
		    .header("accept", "application/json").queryString(params).asString();

	} catch (Exception e) {
	    // TODO: handle exception
	}
    }
    public static void putApiCall(String Endpoint,Map<String,Object> params) {
	String Url = SERVER_URL+Endpoint;
	try {
	    HttpResponse<String> response = Unirest.put(Url).header("authorization", SERVER_TOKEN)
		    .header("accept", "application/json").queryString(params).asString();
	} catch (Exception e) {
	    // TODO: handle exception
	}
    }
}
