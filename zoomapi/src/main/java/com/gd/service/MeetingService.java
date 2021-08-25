package com.gd.service;

import com.gd.dto.MeetingDetailsDTO;

public interface MeetingService 
{
	MeetingDetailsDTO createMeeting();
	MeetingDetailsDTO createMeetingWithDateAndTime(String dateTime);
}
