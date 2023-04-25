package tn.enicarthage.biblio.utils;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Biblio {

	private Biblio() {
		// TODO Auto-generated constructor stub
	}

	public static ResponseEntity<String> getResponseEntity( String responseMessage, HttpStatus httpStatus){
	return new ResponseEntity<String>("{\"message\":\""+responseMessage+"\"}", httpStatus);
	}
	
	
	public static String getUid() {
		Date date =new Date();
		long time=date.getTime();
		return "Emprunt" + time;
	}
	
	public static JSONArray getJsonArrayFromString(String data) throws JSONException {
		JSONArray json= new JSONArray(data);
		return json;
	}
	
	
}
