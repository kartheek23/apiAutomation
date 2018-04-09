package com.restassured.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;
import com.jayway.restassured.response.Response;

public class GenericMethods {
	
	public static String GenerateStringFromResponse(String path) throws IOException{
		return new String(Files.readAllBytes(Paths.get(path)));
	}
	
	public static XmlPath rawToXML(Response r) {
		String respon = r.asString();
		XmlPath x = new XmlPath(respon);
		return x;
	}
	
	public static JsonPath rawToJSON(Response r) {
		String respon = r.asString();
		return new JsonPath(respon);
	}

}
