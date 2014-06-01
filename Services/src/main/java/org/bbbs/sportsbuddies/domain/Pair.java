package org.bbbs.sportsbuddies.domain;

import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

public class Pair {

	private int pairId;
	private int bigId;
	private int littleId;
	private String createdAt;

	public Pair() {
	}

	public Pair(int bigId, int littleId) {
		this.bigId = bigId;
		this.littleId = littleId;
	}

	public int getPairId() {
		return pairId;
	}

	public void setPairId(int pairId) {
		this.pairId = pairId;
	}

	public int getBigUserId() {
		return bigId;
	}

	public void setBigUserId(int bigId) {
		this.bigId = bigId;
	}

	public int getLittleUserId() {
		return littleId;
	}

	public void setLittleUserId(int littleId) {
		this.littleId = littleId;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
}
