package com.asus.ecommerceapp.model.localnotification;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseNotif implements Serializable {

	@SerializedName("notif")
	private List<NotifItem> notif;

	public void setNotif(List<NotifItem> notif){
		this.notif = notif;
	}

	public List<NotifItem> getNotif(){
		return notif;
	}

	@Override
 	public String toString(){
		return 
			"ResponseNotif{" + 
			"notif = '" + notif + '\'' + 
			"}";
		}
}