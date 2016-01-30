package org.opensync;

import java.util.UUID;

public class Record {

	
	private String guid;
	private String pk;
	private String name;
	private String value;
	private long timeStampCreated;
	private long timeStampLastUpdated;
	private boolean delete;
	private long counter_lastupdate=0;
	
	

	public Record(String guid, String pk, String name, String value) {
		super();
		
		if(guid==null){
			UUID uuid = UUID.randomUUID();
			this.guid = uuid.toString();
		}else{
			this.guid=guid;
		}
		
		this.pk = pk;
		this.name = name;
		this.value = value;
		
		this.timeStampCreated=System.currentTimeMillis();
		this.timeStampLastUpdated=System.currentTimeMillis();
		
	}
	
	public void update(String value){
		this.value=value;
		this.timeStampLastUpdated=System.currentTimeMillis();
	}
	
	public void delete(){
		this.delete=true;
		this.timeStampLastUpdated=System.currentTimeMillis();
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public long getTimeStampCreated() {
		return timeStampCreated;
	}

	public void setTimeStampCreated(long timeStampCreated) {
		this.timeStampCreated = timeStampCreated;
	}

	public long getTimeStampUpdated() {
		return timeStampLastUpdated;
	}

	public void setTimeStampUpdated(long timeStampUpdated) {
		this.timeStampLastUpdated = timeStampUpdated;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("guid:%s", this.guid));
		sb.append(String.format("pk:%s", this.pk));
		sb.append(String.format("name:%s", this.name));
		sb.append(String.format("value:%s", this.value));
		sb.append(String.format("timestamp last update:%s", this.timeStampLastUpdated));
		sb.append(String.format("counter last update:%s", this.counter_lastupdate));
		
		System.out.println(sb.toString());
		
		return sb.toString();
		
		
	}
	
	
	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public long getCounter_lastupdate() {
		return counter_lastupdate;
	}

	public void setCounter_lastupdate(long counter_lastupdate) {
		this.counter_lastupdate = counter_lastupdate;
	}
	
	
	
}
