package com.herokuapp.newsfeedapp.configuration;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class provides custom bean instances to Spring container.
 * 
 * @since 2020-04-30
 * @author Sujan Kumar Mitra
 */
@Configuration
@EnableScheduling
public class CustomConfiguration {

	/**
	 * This method returns instance of Jackson Object Mapper, used in JSON
	 * serialization and de-serialization.<br>
	 * It has an property of FAIL_ON_UNKNOWN_PROPERTIES which ignores properties
	 * which aren't defined in POJO.
	 * 
	 * @return {@link ObjectMapper}
	 * @since 2020-04-30
	 * @author Sujan Kumar Mitra
	 */
	@Bean
	public ObjectMapper getJacksonMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		return mapper;
	}

	/**
	 * This method returns instance of RestTemplate, which is used for RESTful
	 * communication purposes.
	 * <ul>
	 * <li>Sets ConnectTimeout to 5 seconds</li>
	 * <li>Sets ReadTimeout to 5 seconds</li>
	 * </ul>
	 * 
	 * @return {@link RestTemplate}
	 * @since 2020-04-30
	 * @author Sujan Kumar Mitra
	 */
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplateBuilder()
				.setConnectTimeout(Duration.ofSeconds(5))
				.setReadTimeout(Duration.ofSeconds(5))
				.build();
	}

}
