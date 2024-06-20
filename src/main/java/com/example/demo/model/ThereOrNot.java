package com.example.demo.model;

public class ThereOrNot {
	private String sunTON;
	private String monTON;
	private String tueTON;
	private String wedTON;
	private String thuTON;
	private String friTON;
	private String satTON;
	public ThereOrNot() {
	}
	public ThereOrNot(
			String sunTON,
			String monTON,
			String tueTON,
			String wedTON,
			String thuTON,
			String friTON,
			String satTON) {
		this.sunTON=sunTON;
		this.monTON=monTON;
		this.tueTON=tueTON;
		this.wedTON=wedTON;
		this.thuTON=thuTON;
		this.friTON=friTON;
		this.satTON=satTON;
	}
	public String getSunTON() {
		return sunTON;
	}
	public String getMonTON() {
		return monTON;
	}
	public String getTueTON() {
		return tueTON;
	}
	public String getWedTON() {
		return wedTON;
	}
	public String getThuTON() {
		return thuTON;
	}
	public String getFriTON() {
		return friTON;
	}
	public String getSatTON() {
		return satTON;
	}
	

}
