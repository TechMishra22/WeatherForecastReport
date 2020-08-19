package com.faire.lab;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import com.faire.lab.controller.WeatherReportController;
import com.faire.lab.dto.Response;
import com.faire.lab.service.impl.WeatherForecastServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


//@RunWith(SpringRunner.class)
//@SpringBootTest
@WebMvcTest(value=WeatherReportController.class)
class WeatherForecast8ApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	WeatherForecastServiceImpl weatherService;
	
	private static final String URI = "http://localhost:8080/weather-forecast/pune/report-type/1";
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	   LocalDateTime now = LocalDateTime.now();   
	   
	   RestTemplate restTemplate;
	   String city="pune";

	   
	   final String uri = "http://api.openweathermap.org/data/2.5/forecast?q=" + city
				+ "&appid=7da2a0b68395c618ef1310f00e68ded3";
		
	@Test
	public void testApiCallWithTimeApi() throws Exception {
				
		  Response mockResponse = new Response();
		  mockResponse.setReport_type("1");
		  mockResponse.setCity("pune");
		  mockResponse.setTemp_min(300.93);
		  mockResponse.setTemp_max(301.23);
		  mockResponse.setHumidity(89L);
		  mockResponse.setDate(dtf.format(now));
		  
		  String inputInJson = this.mapToJson(mockResponse);
		  
		  Mockito.when(restTemplate.getForObject(uri, String.class));
		  RequestBuilder requestBuilder = MockMvcRequestBuilders
				  .get(uri).accept(MediaType.APPLICATION_JSON).contentType(inputInJson)
				  .contentType(MediaType.APPLICATION_JSON);
		  
		  MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		  MockHttpServletResponse response = result.getResponse();
		  
		  String outputInJson=response.getContentAsString();
		  
		  assertThat(outputInJson).isEqualTo(inputInJson);
		  assertEquals(HttpStatus.OK.value(),response.getStatus());
	}             

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
}
