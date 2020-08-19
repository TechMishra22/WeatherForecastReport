package com.faire.lab.service;

import java.util.List;

import com.faire.lab.dto.Response;

public interface WeatherForecastService {
	public List<Response> getWeatherForecast(String city, Long reportType);
}