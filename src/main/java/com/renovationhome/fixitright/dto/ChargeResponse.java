package com.renovationhome.fixitright.dto;

public class ChargeResponse {
	
	private boolean status;
	private String details;

	public ChargeResponse() {
		super();
		this.status = true;
	}
	
	public ChargeResponse(boolean status, String details) {
		super();
		this.status = status;
		this.details = details;
	}


	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "ChargeResponse [status=" + status + ", details=" + details + "]";
	}

}
