package com.rratchet.spring.wechat.open.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

public class RestOperationsFactory {

	public RestOperations get() {
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		List<MediaType> originalSupportedMediaTypes = mappingJackson2HttpMessageConverter.getSupportedMediaTypes();
		ArrayList<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
		supportedMediaTypes.addAll(originalSupportedMediaTypes);
		supportedMediaTypes.add(MediaType.TEXT_PLAIN);
		mappingJackson2HttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);

		RestTemplate restTemplate = new RestTemplate(
				Arrays.<HttpMessageConverter<?>> asList(mappingJackson2HttpMessageConverter));

		return restTemplate;
	}
}
