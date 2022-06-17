package com.test.udp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;

import com.fasterxml.jackson.databind.ObjectMapper;
import static io.restassured.RestAssured.given;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.specification.RequestSpecification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

public class getAuthTokenCode {
	
	public static String respJWT_TokenID;
	public static String final_respJWT_TokenID;
	public static String store_TokenID_Value;
	
	static Logger logger = Logger.getLogger(XmlClass.class);

	public static void main(String[] args) {
		System.out.println("Bismillah");
		try {
			logger.info("Start of the test");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String store_TokenID_Value = getJWTToken();
		System.out.println("This is the string value of TokenID : " + store_TokenID_Value);
		getReferralOnBoardGetAdvCode();
		
		logger.info("End of the test");
	}
	
	
	final static String getJWTToken() throws JSONException {
		String CONTEXT_PATH="https://login.microsoftonline.com/aa827ac4-4665-4dbb-98fa-fa4931a84709/oauth2/v2.0/token";
		
		Map<String, Object> bodyContent = new HashMap<String, Object>();
		bodyContent.put("client_id", "07c0e1ac-9bcd-442f-b927-4b587ed346e9");
		bodyContent.put("client_secret", "wsFJOp.wX8721f~c_5d3SXzMY-.3M3E2h~");
		bodyContent.put("scope", "87963bba-d6c0-41af-ba93-ad00401e9f13/.default");
		bodyContent.put("grant_type", "client_credentials");
						  
		Response response = given().
		contentType("application/x-www-form-urlencoded").
		formParams(bodyContent).
		when().
		post(CONTEXT_PATH).
		then().
		statusCode(200).
		contentType("application/json;charset=utf-8").
		extract().
		response();
		
		JSONObject jsonObj = new JSONObject(response.prettyPrint());
        String access_tk = jsonObj.getString("access_token");
 		
		response.prettyPrint();
		
		String responseData = response.asString();
		
		int code=response.getStatusCode();
		Assert.assertEquals(code, 200);
		
		respJWT_TokenID=responseData;
		
        String[] dataStr = respJWT_TokenID.split(":");
        String[] dataFinal = dataStr[1].split(",");
        final_respJWT_TokenID=dataFinal[0];
        final_respJWT_TokenID=access_tk;
        return final_respJWT_TokenID;
		
	}
	

	final static void getReferralOnBoardGetAdvCode() throws JSONException {
		String CONTEXT_PATH="https://openfaas.udp1non.aws.nml.com/api/v1/service/domains/teenyverse/services/electricity_nightly";
		String Token;
		Token = final_respJWT_TokenID;
						  
		Response response = 
				given()
				  //.proxy("http://z_elnd_auto_proxy:Fj0zWBGJ%23F9%7D2ur61%25dn@vsproxynp2:8080")
				  //.proxy("http://z_eaglelending_proxy:H8s8d%3D%23spL@vsproxynp2:8080 ")
				  .relaxedHTTPSValidation()
		          .headers("Authorization", "Bearer " + Token)	          
		          .when()
		          .get(CONTEXT_PATH)
		          .then()
		          .statusCode(200)
		          .contentType("application/json")
		          .extract()
		          .response();
		
		JSONObject jsonObj = new JSONObject(response.prettyPrint());		
		response.prettyPrint();
		
		String responseData = response.asString();
		int code=response.getStatusCode();

		try {
			//Assert.assertEquals(false, null);
			Assert.assertEquals(code, 200);
		} catch (Exception e) {
			//System.out.println("Exception of Test1 getReferralOnBoardGetAdvCode : " + e);
			
		}		
	
	}
	
}
