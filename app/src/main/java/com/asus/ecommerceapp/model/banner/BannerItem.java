package com.asus.ecommerceapp.model.banner;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BannerItem implements Serializable {

	@SerializedName("nama")
	private String nama;

	@SerializedName("id")
	private String id;

	@SerializedName("gambar")
	private String gambar;

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setGambar(String gambar){
		this.gambar = gambar;
	}

	public String getGambar(){
		return gambar;
	}

	@Override
 	public String toString(){
		return 
			"BannerItem{" + 
			"nama = '" + nama + '\'' + 
			",id = '" + id + '\'' + 
			",gambar = '" + gambar + '\'' + 
			"}";
		}
}