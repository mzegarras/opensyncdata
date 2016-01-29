package org.opensync;

import java.util.ArrayList;
import java.util.List;

public class Client {

	private final int TIMESTAMPPRIORITY = 1;// Conflict handling: version with
											// most recent timestamp_lastupdate
											// is used
	private final int SERVERPRIORITY = 2;// Conflict handling: version from
											// server is used
	private final int CLIENTPRIORITY = 3;// Conflict handling: version from
											// client is used

	private final Server server;

	private String name;
	private int counter;
	private List<Record> records;
	private int counter_lastsync = 0;// value of $this->counter when last sync
										// to server was done
										// ($this->syncToServer)
	private long servercounter_lastsync = 0; // value of server counter when
												// last sync from server was
												// done ($this->syncFromServer)
	private int conflictHandling;

	public Client(String name,Server server) {
		super();
		this.name = name;
		this.server = server;
		this.records = new ArrayList<Record>();
	}

	private void syncFromServer() {
		List<Record> objectsToSync = new ArrayList<Record>();

		Result result = server.syncToClient(this.servercounter_lastsync, objectsToSync); // in
																							// reality
																							// server
																							// will
																							// be
																							// called
																							// using
																							// an
																							// HTTPS
																							// call
																							// to
																							// a
																							// REST
																							// API

		this.debugOutput("Sync from server to client:" + this.name + " - received objects:", objectsToSync);

		for (Record objectToSync : objectsToSync) {
			boolean objectExists = false;

			for (Record object : records) {

				if (object.getGuid().compareTo(objectToSync.getGuid()) == 0
						|| object.getPk().compareTo(object.getPk()) == 0) {
					objectExists = true;

					// handle PK conflict
					if (object.getPk().compareToIgnoreCase(objectToSync.getPk()) == 0) {
						object.setGuid(objectToSync.getGuid()); // merge objects
					}

					// check for conflict (object updated locally since last
					// sync to server)
					if (object.getCounter_lastupdate() > this.counter_lastsync) {
						if (conflictHandling == SERVERPRIORITY) {
							object.setValue(objectToSync.getValue());
							object.setDelete(objectToSync.isDelete());
						} else if (conflictHandling == CLIENTPRIORITY) {
							// no change to local object
						} else if (conflictHandling == TIMESTAMPPRIORITY) {

							if (objectToSync.getTimeStampUpdated() > object.getTimeStampUpdated()) {
								object.setValue(objectToSync.getValue());
								object.setDelete(objectToSync.isDelete());
								object.setTimeStampUpdated(System.currentTimeMillis());
							}
						}
					} else { // no conflict: update object locally
						object.setValue(objectToSync.getValue());
						object.setDelete(objectToSync.isDelete());
					}
				}

				if (!objectExists) {
					Record newObject = new Record(objectToSync.getGuid(), objectToSync.getPk(), objectToSync.getName(),
							objectToSync.getValue());
					newObject.setDelete(objectToSync.isDelete());
					newObject.setCounter_lastupdate(this.counter);
					records.add(newObject);
				}

			}

			if (result.getStatuscode() == 1) {
				this.servercounter_lastsync = result.getServercounter();
			}

		}
	}

	public void debugOutput(String text, List<Record> objects) {
		for (Record object : objects) {
			object.toString();
		}
	}

	public void display() {
		System.out.println(String.format("name: %s", this.name));
		System.out.println(String.format("counter: %s", this.counter));

		debugOutput("Objects on server:", this.records);
	}

	private void syncToServer() {

		

		List<Record> objectsToSync = new ArrayList<Record>();

		for (Record object : this.records) {

			// object changed on client since last sync to server ?
			if (object.getCounter_lastupdate() > this.counter_lastsync) {
				objectsToSync.add(object);
			}

		}

		debugOutput(" Sync to server - objects to send to server:", objectsToSync);

		Result result = server.syncFromClient(objectsToSync); // in reality
																// server will
																// be called
																// using an
																// HTTPS call to
																// a REST API
		if (result.getStatuscode() == 1) {
			this.counter_lastsync = this.counter; // better to store
													// this->counter in
													// $currentCounter at
													// beginning of sync
			this.servercounter_lastsync = result.getServercounter(); // because
																		// this
																		// sync
																		// will
																		// have
																		// increased
																		// the
																		// server
																		// counter
																		// and
																		// otherwise
																		// client
																		// will
																		// receive
																		// its
																		// own
																		// update
																		// on
																		// next
																		// sync

			// optionally you can add a check here: client can only sync to
			// server when client has done sync from server first:
			// this is to avoid that client would miss out on server updates
			// because of the above line
		}
	}

	public void doSync() {
		// first sync from server to client, then from client to server,
		// because only client can handle conflicts (server cannot handle
		// conflicts because server does not know state of client)
		this.syncFromServer();
		this.syncToServer();
	}

	public void doFullSync() {
		this.counter_lastsync = 0; // force full sync to server
		this.servercounter_lastsync = 0; // force full sync from server
		this.doSync();
	}

	/*
	 * Create object on client (do not use this function to add object from a
	 * server sync)
	 */
	public void addObject(String pk, String name, String value) {
		// check if PK (primary key) not in use yet
		for (Record object : this.records) {
			if (object.getPk().compareTo(pk) == 0) {
				System.out.println("Error creating new object on client " + this.name + ": primary key " + pk
						+ " already in use!");
			}

			Record newObject = new Record(null, pk, name, value);
			this.counter++;
			newObject.setCounter_lastupdate(this.counter);
			this.records.add(newObject);
		}
	}

	/*
	 * Update object on client (do not use this function to add object from a
	 * server sync)
	 */
	public void updateObject(String pk, String newValue) {
		for (Record object : this.records) {
			{
				if (object.getPk().compareTo(pk) == 0) {
					object.update(newValue);
					this.counter++;
					object.setCounter_lastupdate(this.counter);
				}
			}
		}
	}

	/*
	 * Delete object on client (do not use this function to delete object from a
	 * server sync)
	 */
	public void deleteObject(String pk) {
		for (Record object : this.records) {
			{
				if (object.getPk().compareTo(pk) == 0) {
					{
						object.delete();
						this.counter++;
						object.setCounter_lastupdate(this.counter);
					}
				}
			}
		}
	}
	
	
	
}
