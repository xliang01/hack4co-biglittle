package org.bbbs.sportsbuddies.domain;

public class Pair {

	private int bigId;
	private int littleId;
	
	public Pair() {}
	
	public Pair(int bigId, int littleId) {
		this.bigId = bigId;
		this.littleId = littleId;
	}
	
	public int getBigId() {
		return bigId;
	}
	public void setBigId(int bigId) {
		this.bigId = bigId;
	}
	public int getLittleId() {
		return littleId;
	}
	public void setLittleId(int littleId) {
		this.littleId = littleId;
	}	
}
