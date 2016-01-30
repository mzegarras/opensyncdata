package org.opensync;

import java.util.List;

public class Result {
	
	private int statuscode = Common.Status.NOSET.getValue();
	private long servercounter;
	private List<Record> recors;
	
	public List<Record> getRecors() {
		return recors;
	}
	public void setRecors(List<Record> recors) {
		this.recors = recors;
	}
	public int getStatuscode() {
		return statuscode;
	}
	public void setStatuscode(int statuscode) {
		this.statuscode = statuscode;
	}
	public long getServercounter() {
		return servercounter;
	}
	public void setServercounter(long servercounter) {
		this.servercounter = servercounter;
	}
	
	

}
