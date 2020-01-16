package com.asus.ecommerceapp.model.localnotification;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotifItem implements Serializable {

	@SerializedName("id")
	private String id;

	@SerializedName("id_pelanggan")
	private String idPelanggan;

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("kategori")
	private String kategori;

	@SerializedName("no_order")
	private String noOrder;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setIdPelanggan(String idPelanggan){
		this.idPelanggan = idPelanggan;
	}

	public String getIdPelanggan(){
		return idPelanggan;
	}

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setKategori(String kategori){
		this.kategori = kategori;
	}

	public String getKategori(){
		return kategori;
	}

	public void setNoOrder(String noOrder){
		this.noOrder = noOrder;
	}

	public String getNoOrder(){
		return noOrder;
	}

	@Override
 	public String toString(){
		return 
			"NotifItem{" + 
			"id = '" + id + '\'' + 
			",id_pelanggan = '" + idPelanggan + '\'' + 
			",pesan = '" + pesan + '\'' + 
			",kategori = '" + kategori + '\'' + 
			",no_order = '" + noOrder + '\'' + 
			"}";
		}
}