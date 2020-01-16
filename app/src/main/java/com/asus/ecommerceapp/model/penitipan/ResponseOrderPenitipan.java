package com.asus.ecommerceapp.model.penitipan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseOrderPenitipan{

	@SerializedName("id")
	@Expose
	private String id;

	@SerializedName("Info")
	private String info;

	@SerializedName("Success")
	private boolean success;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setInfo(String info){
		this.info = info;
	}

	public String getInfo(){
		return info;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	@Override
 	public String toString(){
		return 
			"ResponseOrderPenitipan{" +
			"id = '" + id + '\'' +
			"info = '" + info + '\'' +
			",success = '" + success + '\'' +
			"}";
		}
}