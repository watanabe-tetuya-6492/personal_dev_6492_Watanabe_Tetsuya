package com.example.demo.model;

public class ToWeek {
	private Calender sun;
	private Calender mon;
	private Calender tue;
	private Calender wed;
	private Calender thu;
	private Calender fri;
	private Calender sat;
	private Integer sunTON;
	private Integer monTON;
	private Integer tueTON;
	private Integer wedTON;
	private Integer thuTON;
	private Integer friTON;
	private Integer satTON;
	
	public ToWeek(
			Calender sun,
			Calender mon,
			Calender tue,
			Calender wed,
			Calender thu,
			Calender fri,
			Calender sat,
			Integer sunTON,
			Integer monTON,
			Integer tueTON,
			Integer wedTON,
			Integer thuTON,
			Integer friTON,
			Integer satTON) {
		this.sunTON=sunTON;
		this.monTON=monTON;
		this.tueTON=tueTON;
		this.wedTON=wedTON;
		this.thuTON=thuTON;
		this.friTON=friTON;
		this.satTON=sunTON;
		this.sun=sun;
		this.mon=mon;
		this.tue=tue;
		this.wed=wed;
		this.thu=thu;
		this.fri=fri;
		this.sat=sat;
	}

	public Calender getSun() {
		return sun;
	}

	public Calender getMon() {
		return mon;
	}

	public Calender getTue() {
		return tue;
	}

	public Calender getWed() {
		return wed;
	}

	public Calender getThu() {
		return thu;
	}

	public Calender getFri() {
		return fri;
	}

	public Calender getSat() {
		return sat;
	}
	
	
	
	public Integer getSunTON() {
		return sunTON;
	}

	public Integer getMonTON() {
		return monTON;
	}

	public Integer getTueTON() {
		return tueTON;
	}

	public Integer getWedTON() {
		return wedTON;
	}

	public Integer getThuTON() {
		return thuTON;
	}

	public Integer getFriTON() {
		return friTON;
	}

	public Integer getSatTON() {
		return satTON;
	}

	public int getDay(Calender day) {
		int days=day.getDay();
		return days;
	}
	
	
}
