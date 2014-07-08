package de.bht.comanche.server;

import java.util.ArrayList;
import java.util.List;

import de.bht.comanche.logic.DbObject;

public class ResponseObject {
	protected List<DbObject> data;
	protected boolean success;
	protected List<String> serverMessages;
	
	
	public ResponseObject() {
		serverMessages = new ArrayList<String>();
		data = new ArrayList<DbObject>();
	}

	public void addServerMessage(String m) {
		serverMessages.add(m);
	}
	
	public void addData(DbObject d) {
		data.add(d);
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<String> getServerMessages() {
		return serverMessages;
	}
	public void setServerMessages(ArrayList<String> serverMessages) {
		this.serverMessages = serverMessages;
	}
	public List<? extends DbObject> getData() {
		return data;
	}
	public void setData(List<DbObject> data) {
		this.data = data;
	}
}
