package org.opensync;

import java.util.ArrayList;
import java.util.List;

public class Server {
	
	

	private String name;
	private int counter;
	private List<Record> records;

	public Server(String name) {
		super();
		this.name = name;
		this.records = new ArrayList<Record>();
	}

	public Result syncToClient(long serverCounterSyncStart, List<Record> objectsToSync) {

		System.out.println("Server received request to sync to client starting at ServerCounterSyncStart:" + String.valueOf(serverCounterSyncStart));

		//objectsToSync = new ArrayList<Record>();

		for (Record record : records) {

			if (record.getCounter_lastupdate() > serverCounterSyncStart) {
				objectsToSync.add(record);
			}
		}

		Result result = new Result();
		result.setStatuscode(Common.Status.OK.getValue());
		result.setServercounter(counter);

		return result;

	}

	public Result syncFromClient(List<Record> objectsToSync) {

		Result result = new Result();

		for (Record objectToSync : objectsToSync) {

			boolean objectExists = false;
			boolean pkConflict = false;

			for (Record object : records) {
				if (object.getGuid().compareTo(objectToSync.getGuid()) == 0) {
					objectExists = true;
					object.setValue(objectToSync.getValue());
					object.setDelete(objectToSync.isDelete());
					this.counter+=1; // we must increase the counter, because
									// other clients must also receive these
									// updates
					object.setCounter_lastupdate(this.counter);
				} else if (object.getPk().compareTo(objectToSync.getPk()) == 0) {//this means object with other guid but with same PK (primary key)
					//PK conflict: do nothing, because this should not occur on server
                    //client will always sync from server to client first and handle any PK conflicts, before syncing from client to server
					
					objectExists = true;
					pkConflict = true;
					result.setStatuscode(Common.Status.NOOK.getValue());
				}
			}

			if (!objectExists) {
				Record newObject = new Record(objectToSync.getGuid(), objectToSync.getPk(), objectToSync.getName(),
						objectToSync.getValue());
				newObject.setDelete(objectToSync.isDelete());
				this.counter+=1;
				newObject.setCounter_lastupdate(this.counter);
				records.add(newObject);
			}
		}

		if(result.getStatuscode()==Common.Status.NOSET.getValue()){
			result.setStatuscode(Common.Status.OK.getValue());
		}
		result.setServercounter(this.counter);

		return result;

	}

	/*
	 * Create object on server (do not use this function to add object from a
	 * client sync)
	 */
	public void addObject(String pk, String name, String value) { // try to move
																	// all this
																	// into
																	// Object
																	// class
																	// (and
																	// access
																	// parent to
																	// get
																	// counter)
		Record newObject = new Record(null, pk, name, value);
		this.counter+=1;
		newObject.setCounter_lastupdate(this.counter);
		this.records.add(newObject);
	}

	/*
	 * Update object on server (do not use this function to update object from a
	 * client sync)
	 */
	public void updateObject(String pk, String value) {

		for (Record object : records) {
			{

				if (object.getPk().compareTo(pk) == 0) {
					{
						object.update(value);
						this.counter+=1;
						object.setCounter_lastupdate(this.counter);
						// $object->update($newValue);
						// $this->counter++;
						// $object->counter_lastupdate = $this->counter;
					}
				}
			}
		}
	}
	
	 /* Update object on server (do not use this function to update object from a client sync) */
	public void deleteObject(String pk)
	{
		for (Record object : records) 
        {
            if (object.getPk().compareTo(pk)==0)
            {
            	object.delete();
            	this.counter+=1;
            	object.setCounter_lastupdate(this.counter);
            }
        }
	}
	
	public void debugOutput(String text,List<Record> objects){
		for (Record object : objects) {
			object.toString();
		}
	}
	
	public void display(){
		System.out.println(String.format("name: %s", this.name));
		System.out.println(String.format("counter: %s", this.counter));
		
		debugOutput("Objects on server:",this.records);
	}

}
