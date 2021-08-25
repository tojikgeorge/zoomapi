package com.gd.controller;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gd.dto.MeetingDetailsDTO;
import com.gd.service.MeetingService;

@RestController
public class MeetingController 
{
	@Autowired
	MeetingService service;

	@GetMapping(value="/startmeeting")
	public ResponseEntity<MeetingDetailsDTO> startMeeting()
	{
		MeetingDetailsDTO details = service.createMeeting();
		return ResponseEntity.ok(details);
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping(value="/startmeetingwithtime")
	public ResponseEntity<MeetingDetailsDTO> startMeeting(@RequestParam(name="dateandtime", required = false) String dateAndTime)
	{
		
		MeetingDetailsDTO details = new MeetingDetailsDTO();
		if(!isValidDataAndTime(dateAndTime))
		{
			details.setOrganizerUrl("Wrong param !");
			return ResponseEntity.ok(details);
		}
		details = service.createMeetingWithDateAndTime(dateAndTime);
		return ResponseEntity.ok(details);
	}
	
	public boolean isValidDataAndTime(String data)
	{
		try
		{
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		 formatter.parse(data);
		}
		catch(Exception e)
		{e.printStackTrace();
			return false;
		}
		return true;
	}
}
