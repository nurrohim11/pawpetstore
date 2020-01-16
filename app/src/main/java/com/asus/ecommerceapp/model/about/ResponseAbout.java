package com.asus.ecommerceapp.model.about;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ResponseAbout implements Serializable {

	@SerializedName("id")
	private String id;

	@SerializedName("judul")
	private String judul;

	@SerializedName("deskripsi")
	private String deskripsi;

	@SerializedName("version")
	private String version;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setJudul(String judul){
		this.judul = judul;
	}

	public String getJudul(){
		return judul;
	}

	public void setDeskripsi(String deskripsi){
		this.deskripsi = deskripsi;
	}

	public String getDeskripsi(){
		return deskripsi;
	}

	public void setVersion(String version){
		this.version = version;
	}

	public String getVersion(){
		return version;
	}

	@Override
 	public String toString(){
		return 
			"ResponseAbout{" + 
			"id = '" + id + '\'' + 
			",judul = '" + judul + '\'' + 
			",deskripsi = '" + deskripsi + '\'' + 
			",version = '" + version + '\'' + 
			"}";
		}
}