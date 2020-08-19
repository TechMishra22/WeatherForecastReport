package com.faire.lab.service.impl;

import static com.faire.lab.util.CommonUtils.haveToAdd;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.faire.lab.dto.Response;
import com.faire.lab.service.WeatherForecastService;
// WeatherService Implementation class to call the api and calculate the working and non working time reportType
@Service
public class WeatherForecastServiceImpl implements WeatherForecastService {
	@Autowired
	RestTemplate restTemplate;

	@Override
	public List<Response> getWeatherForecast(String city, Long reportType) {
		List<Response> responses = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		try {
			//Creating JSON object and JSON Array
			JSONObject root = callWeatherApi(city);
			JSONArray list = root.getJSONArray("list");
			JSONObject obj = null;
			int daysCount = 0;
			Date date = null;
			Calendar cal = Calendar.getInstance();
			Date lastDate = null;
			// checking length of list which is return by API if return will come then only enter inside if block
			if (list.length() > 0) {
				responses = new LinkedList<Response>();
				Response response = null;
				for (int i = 0; i < list.length(); i++) {
					date = sdf.parse(list.getJSONObject(i).getString("dt_txt"));

					obj = list.getJSONObject(i).getJSONObject("main");
					response = new Response();
					response.setCity(city.toUpperCase());
					response.setDate(list.getJSONObject(i).getString("dt_txt"));
					response.setReport_type(
							reportType == 1 ? "Working Hours" : (reportType == 2 ? "Non Working Hours" : "Undefined"));
					response.setTemp_min(obj.getDouble("temp_min"));
					response.setTemp_max(obj.getDouble("temp_max"));
					response.setHumidity(obj.getLong("humidity"));
					if (lastDate == null
							|| lastDate.compareTo(sdfDate.parse(list.getJSONObject(i).getString("dt_txt"))) != 0) {
						daysCount += 1;
						lastDate = sdfDate.parse(list.getJSONObject(i).getString("dt_txt"));
						if (daysCount > 3)
							break;
					}
					if (daysCount <= 3 && haveToAdd(cal, reportType))
						responses.add(response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responses;
	}

	private JSONObject callWeatherApi(String city) {
		String uri = "http://api.openweathermap.org/data/2.5/forecast?q=" + city
				+ "&appid=7da2a0b68395c618ef1310f00e68ded3";
		String result = restTemplate.getForObject(uri, String.class);
		return new JSONObject(result);
	}

}
