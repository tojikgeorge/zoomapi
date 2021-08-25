package com.gd.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MeetingSettingsDTOImpl implements MeetingSettingsDTO
{
	@Value("${zoom.meeting.topic}")
	private String topic;
	
	@Value("${zoom.meeting.timezone}")
	private String timeZone;
	
	@Value("${zoom.meeting.userid}")
	private String userid;
	
	@Value("${zoom.meeting.apiurl}")
	private String apiUrl;
	
	@Value("${zoom.meeting.email}")
	private String email;
	
	@Value("${zoom.meeting.api.secret}")
	private String apiSecret;
	
	@Value("${zoom.meeting.api.key}")
	private String apiKey;
	
		
	public String getTopic() {
		return topic;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public String getUserid() {
		return userid;
	}	
	public String getApiUrl() {
		return apiUrl;
	}
	public String getEmail() {
		return email;
	}
	public String getApiSecret() {
		return apiSecret;
	}
	public String getApiKey() {
		return apiKey;
	}		

}
