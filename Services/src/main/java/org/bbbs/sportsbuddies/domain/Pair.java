package org.bbbs.sportsbuddies.domain;


public class Pair {

	private int pairId = -1;
	private int bigId = -1;
	private int littleId = -1;
	private String createdAt;

	public Pair() {}

	public Pair(int pairId, 
				int bigId, 
				int littleId,
				String createdAt) {
		
		this.pairId = pairId;
		this.bigId = bigId;
		this.littleId = littleId;
		this.createdAt = createdAt;
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
