package com.asus.ecommerceapp.model.keranjang;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ResponseHapusKeranjang implements Serializable {

	@SerializedName("status")
	private boolean status;

	@SerializedName("message")
	private String message;

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"ResponseHapusKeranjang{" + 
			"status = '" + status + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}