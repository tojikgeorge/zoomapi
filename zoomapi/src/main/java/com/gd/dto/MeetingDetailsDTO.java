package com.gd.dto;

public class MeetingDetailsDTO 
{
	private String organizerUrl;
	private String participantsUrl;
	private String joiningPassword;
	private String joiningId;
	
	public String getOrganizerUrl() {
		return organizerUrl;
	}
	public void setOrganizerUrl(String organizerUrl) {
		this.organizerUrl = organizerUrl;
	}
	public String getParticipantsUrl() {
		return participantsUrl;
	}
	public void setParticipantsUrl(String participantsUrl) {
		this.participantsUrl = participantsUrl;
	}
	public String getJoiningPassword() {
		return joiningPassword;
	}
	public void setJoiningPassword(String joiningPassword) {
		this.joiningPassword = joiningPassword;
	}
	public String getJoiningId() {
		return joiningId;
	}
	public void setJoiningId(String joiningId) {
		this.joiningId = joiningId;
	}
	
	
}
