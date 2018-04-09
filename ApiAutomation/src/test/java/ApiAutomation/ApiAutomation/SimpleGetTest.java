//package ApiAutomation.ApiAutomation;
//
//import org.testng.annotations.Test;
//import org.testng.AssertJUnit;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import io.restassured.RestAssured;
//import io.restassured.http.Method;
//import io.restassured.response.Response;
//import io.restassured.specification.RequestSpecification;
//
//public class SimpleGetTest {
//@Test(description="Api status code and body test")
//public void getWeatherDetails() {
//	RestAssured.baseURI ="http://restapi.demoqa.com/utilities/weather/city";
//	RequestSpecification httpRequest = RestAssured.given();
//	// Make a request to the server by specifying the method Type and the method URL.
//			// This will return the Response from the server. Store the response in a variable.
//			Response response = httpRequest.request(Method.GET, "/Hyderabad");
//	 
//			// Now let us print the body of the message to see what response
//			// we have recieved from the server
//			String responseBody = response.getBody().asString();
//			System.out.println("Response Body is =>  " + responseBody);
//			int statusCode = response.getStatusCode();
//			 System.out.println(statusCode);
//			// Assert that correct status code is returned.
//			Assert.assertEquals(statusCode , 200,"Correct status code is returned.");
//		}
//
//@Test
//public void GetWeatherDetailsInvalidCity()
//{
//	RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
//	RequestSpecification httpRequest = RestAssured.given();
//	Response response = httpRequest.get("/78789798798");
//	int statusCode = response.getStatusCode();
//	
//	Assert.assertEquals(statusCode , 200 , "Correct status code is not returned");
//}
//
//}
//
