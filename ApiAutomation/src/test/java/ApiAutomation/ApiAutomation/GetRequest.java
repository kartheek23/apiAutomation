package ApiAutomation.ApiAutomation;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;
import com.jayway.restassured.response.Response;
import com.restassured.files.GenericMethods;
import com.restassured.files.PayLoad;
import com.restassured.files.Resource;

import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;




public class GetRequest {
	Properties props = new Properties();
	@BeforeTest
	public void fetchPropData() throws IOException {
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/env.properties");
		props.load(fis);
	}
	
	
	@Test
	public static void demoGetRequest() {
		RestAssured.baseURI =  "https://maps.googleapis.com";
		given().
		param("location","-33.8670522,151.1957362").
		param("radius","500").
		param("key","AIzaSyDyk8hNkJYEj9zPJbQwyVYLtYXnsmAqJ9c").
		when().
		get("/maps/api/place/nearbysearch/json").
		then().assertThat().statusCode(200).
		and().
		contentType(ContentType.JSON).
		and()
		.body("results[0].name",equalTo("Sydney")).and().
		body("results[0].place_id",equalTo("ChIJP3Sa8ziYEmsRUKgyFmh9AQM"))
		.and().header("Server", "scaffolding on HTTPServer2");
		
		
	}
	
	@Test
	public static void postRequest() {
		RestAssured.baseURI="https://maps.googleapis.com";
		given().
		queryParam("key","AIzaSyDyk8hNkJYEj9zPJbQwyVYLtYXnsmAqJ9c").
		body("{\n" + 
				"  \"location\": {\n" + 
				"    \"lat\": -33.8669710,\n" + 
				"    \"lng\": 151.1958750\n" + 
				"  },\n" + 
				"  \"accuracy\": 50,\n" + 
				"  \"name\": \"Google Shoes!\",\n" + 
				"  \"phone_number\": \"(02) 9374 4000\",\n" + 
				"  \"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\",\n" + 
				"  \"types\": [\"shoe_store\"],\n" + 
				"  \"website\": \"http://www.google.com.au/\",\n" + 
				"  \"language\": \"en-AU\"\n" + 
				"}").when()
		.post("/maps/api/place/add/json").
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
		body("status",equalTo("OK"));
	}
	
	@Test
	public void postAndDeleteRequest() {
		
		RestAssured.baseURI=props.getProperty("HOST");
		Response response=given().
		queryParam("key",props.getProperty("KEY")).
		body(PayLoad.getPostData()).when()
		.post(Resource.placePostData()).
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
		body("status",equalTo("OK"))
		.extract().response();
		//Task 2 - Grab the place ID from the response
		
		String responseString = response.asString();
		System.out.println(responseString);
		JsonPath js = new JsonPath(responseString);
		String placeid= js.get("place_id");
		System.out.println(placeid);
		
		// Task 3 place this place id in the delete request.
		given().
		queryParam("key","AIzaSyDyk8hNkJYEj9zPJbQwyVYLtYXnsmAqJ9c")
		.body("{"+
		"\"place_id\":\""+placeid+"\""+
				"}")
		.when()
		.post("/maps/api/place/delete/json")
		.then().assertThat().statusCode(200).and().contentType(ContentType.JSON)
        .and().body("status", equalTo("OK"));		
	
		
	}
	
  @Test
  public void validateXMLResponse() throws IOException {
	  System.out.println(System.getProperty("user.dir")+"/postResponse.xml");
	  RestAssured.baseURI=props.getProperty("HOST");
		Response res=given().
		queryParam("key",props.getProperty("KEY")).
		body(GenericMethods.GenerateStringFromResponse(System.getProperty("user.dir")+"/postResponse.xml"))
		.when()
		.post("/maps/api/place/add/xml").
		then().assertThat().statusCode(200).and().contentType(ContentType.XML)
		.extract().response();
		XmlPath x = GenericMethods.rawToXML(res);
		String s=x.get("PlaceAddResponse.place_id");
		System.out.println(s);
	  
  }
  
  @Test
	public static void printAllNamesFromJSON() {
		RestAssured.baseURI =  "https://maps.googleapis.com";
		Response res=given().
		param("location","-33.8670522,151.1957362").
		param("radius","500").
		param("key","AIzaSyDyk8hNkJYEj9zPJbQwyVYLtYXnsmAqJ9c").
		when().
		get("/maps/api/place/nearbysearch/json").
		then().assertThat().statusCode(200).
		and().
		contentType(ContentType.JSON).
		and()
		.body("results[0].name",equalTo("Sydney")).and().
		body("results[0].place_id",equalTo("ChIJP3Sa8ziYEmsRUKgyFmh9AQM"))
		.and().header("Server", "scaffolding on HTTPServer2")
		.extract().response();
		JsonPath js = GenericMethods.rawToJSON(res);
		int count = js.get("results.size()");
	 	for(int i =0;i<count;i++) {
	 	String s =js.get("results["+i+"].name" );
		System.out.println(s);	
		}
		
		
	}
  
  @Test
	public static void printLogsForParamsAndBody() {
		RestAssured.baseURI =  "https://maps.googleapis.com";
		Response res=given().
		param("location","-33.8670522,151.1957362").
		param("radius","500").
		param("key","AIzaSyDyk8hNkJYEj9zPJbQwyVYLtYXnsmAqJ9c").log().all().
		when().
		get("/maps/api/place/nearbysearch/json").
		then().assertThat().statusCode(200).
		and().
		contentType(ContentType.JSON).
		and()
		.body("results[0].name",equalTo("Sydney")).and().
		body("results[0].place_id",equalTo("ChIJP3Sa8ziYEmsRUKgyFmh9AQM"))
		.and().header("Server", "scaffolding on HTTPServer2").log().body()
		.extract().response();
		JsonPath js = GenericMethods.rawToJSON(res);
		int count = js.get("results.size()");
	 	for(int i =0;i<count;i++) {
	 	String s =js.get("results["+i+"].name" );
		System.out.println(s);	
		}
		
		
	}

}
