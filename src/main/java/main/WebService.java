package main;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;

public class WebService {
    static String SERVER_TOKEN = "eoR2sbxVJKJlJAVcGggEa8abXcgo9sn4";
    static String SERVER_URL = "http://localhost:8080/WebService2";

    public static HttpResponse<String> postApiCall(String EndPoint, String Body, String method)
	    throws UnirestException {
	String Url = SERVER_URL+EndPoint;
//	String EndPoint = "/Student";
//	String Body = "username=dik2323&password=breadjamd23&student_name=Dikshant23&college_course=science";
	if (method.equals("POST")) {
	    HttpRequestWithBody request = Unirest.post(Url);
	} else if (method.equals("GET")) {
	    GetRequest request = Unirest.get(Url);
	}

	HttpResponse<String> response = request.header("content-type", "application/x-www-form-urlencoded").header("authorization", SERVER_TOKEN)
		.body(Body).asString();
	return response;
    }

    public static HttpResponse<String> getApiCall() {

    }
}
