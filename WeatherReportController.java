package com.faire.lab.controller;

import java.util.List;

import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faire.lab.dto.Response;
import com.faire.lab.service.WeatherForecastService;

// Controller Class to control the weather services
@RestController
@RequestMapping("/weather-forecast")
public class WeatherReportController {

	// Createed WeatherForecastService instance
	@Autowired
	WeatherForecastService weatherForecastService;

	@GetMapping("/{city}/report-type/{reportType}")
	public List<Response> getResult(@PathVariable String city, @PathVariable Long reportType)
			throws Exception, BadRequestException {
		List<Response> responses = null;
		if (reportType >= 1 && reportType <= 2) {
			try {
				// call method to get weather Api forecast
				responses = weatherForecastService.getWeatherForecast(city, reportType);
			} catch (Exception e) {

			}
		} else {
			throw new BadRequestException();
		}
		return responses;
	}
}