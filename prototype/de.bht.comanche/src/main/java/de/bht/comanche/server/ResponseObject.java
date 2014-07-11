package de.bht.comanche.server;

import java.util.ArrayList;
import java.util.List;

import de.bht.comanche.logic.DbObject;

public class ResponseObject {
	protected List<DbObject> data;
	protected int responseCode;
	
	public ResponseObject() {
		responseCode = 200;
		data = new ArrayList<DbObject>();
	}
	
	public void addData(DbObject d) {
		data.add(d);
	}

	public List<? extends DbObject> getData() {
		return data;
	}
	
	public void setData(List<DbObject> data) {
		this.data = data;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	
	public boolean hasError() {
		return responseCode != 200;
	}
}
