package com.gd.service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

import com.gd.dto.MeetingDetailsDTO;
import com.gd.dto.MeetingSettingsDTO;
import com.gd.dto.ZoomMeetingObjectDTO;
import com.gd.dto.ZoomMeetingSettingsDTO;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
@RequestScope
public class MeetingServiceImpl implements MeetingService {
	@Autowired
	MeetingSettingsDTO meetingSettingsDTO;

	private String dateAndTime;

	private void setDateAndTime(final String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	@Override
	public MeetingDetailsDTO createMeeting() {
		MeetingDetailsDTO result = new MeetingDetailsDTO();

		try {
			ZoomMeetingObjectDTO zoomMeetingObjectDTO = new ZoomMeetingObjectDTO();
			String apiUrl = meetingSettingsDTO.getApiUrl() + meetingSettingsDTO.getUserid() + "/meetings";
			zoomMeetingObjectDTO.setTopic(meetingSettingsDTO.getTopic());
			zoomMeetingObjectDTO.setPassword(generateRandomValue());
			zoomMeetingObjectDTO.setHost_email(meetingSettingsDTO.getEmail());

			ZoomMeetingSettingsDTO settingsDTO = new ZoomMeetingSettingsDTO();
			settingsDTO.setJoin_before_host(false);
			settingsDTO.setParticipant_video(true);
			settingsDTO.setHost_video(false);
			settingsDTO.setAuto_recording("cloud");
			settingsDTO.setMute_upon_entry(true);
			zoomMeetingObjectDTO.setSettings(settingsDTO);
			zoomMeetingObjectDTO.setTimezone(meetingSettingsDTO.getTimeZone());
			if (null != dateAndTime) {
				zoomMeetingObjectDTO.setStart_time(dateAndTime);
			}

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + generateZoomJWTTOken());
			headers.add("content-type", "application/json");
			HttpEntity<ZoomMeetingObjectDTO> httpEntity = new HttpEntity<ZoomMeetingObjectDTO>(zoomMeetingObjectDTO,
					headers);
			ResponseEntity<ZoomMeetingObjectDTO> zEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity,
					ZoomMeetingObjectDTO.class);
			if (zEntity.getStatusCodeValue() == 201) {
				zoomMeetingObjectDTO = zEntity.getBody();
				result.setOrganizerUrl(zoomMeetingObjectDTO.getStart_url());
				result.setParticipantsUrl(zoomMeetingObjectDTO.getJoin_url());
				result.setJoiningId(zoomMeetingObjectDTO.getId().toString());
				result.setJoiningPassword(zoomMeetingObjectDTO.getPassword());
			} else {
				result.setOrganizerUrl(zEntity.getStatusCode().getReasonPhrase());
				System.out.println("Error while creating zoom meeting  :  " + zEntity.getStatusCode());
			}
		} catch (Exception e) {
			result.setOrganizerUrl(e.toString());
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public MeetingDetailsDTO createMeetingWithDateAndTime(String dateTime) {
		MeetingDetailsDTO result = new MeetingDetailsDTO();
		setDateAndTime(dateTime);
		result = createMeeting();
		return result;
	}

	/**
	 * Generate JWT token for Zoom using api credentials
	 * 
	 * @return JWT Token String
	 */
	private String generateZoomJWTTOken() {
		String id = UUID.randomUUID().toString().replace("-", "");
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		Date creation = new Date(System.currentTimeMillis());
		Date tokenExpiry = new Date(System.currentTimeMillis() + (1000 * 60));

		SecretKey key = Keys.hmacShaKeyFor(meetingSettingsDTO.getApiSecret().getBytes());
		return Jwts.builder().setId(id).setIssuer(meetingSettingsDTO.getApiKey()).setIssuedAt(creation).setSubject("")
				.setExpiration(tokenExpiry).signWith(signatureAlgorithm, key).compact();
	}

	private String generateRandomValue() {
		String lower = "abcdefghijklmnopqrstuvwxyz";
		String upper = lower.toUpperCase();
		String number = "0123456789";

		String mergeData = lower + upper + number;
		SecureRandom random = new SecureRandom();

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 6; i++) {
			int rndCharAt = random.nextInt(mergeData.length());
			char rndChar = mergeData.charAt(rndCharAt);
			sb.append(rndChar);
		}
		return sb.toString();
	}

}
