package com.asus.ecommerceapp.model.penitipan;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class HistoryPenitipanItem implements Serializable {

	@SerializedName("nama")
	private String nama;

	@SerializedName("id_pelanggan")
	private String idPelanggan;

	@SerializedName("no_order")
	private String noOrder;

	@SerializedName("tgl")
	private String tgl;

	@SerializedName("tgl_dari")
	private String tglDari;

	@SerializedName("tgl_sampai")
	private String tglSampai;

	@SerializedName("total")
	private String total;

	@SerializedName("status")
	private String status;

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setIdPelanggan(String idPelanggan){
		this.idPelanggan = idPelanggan;
	}

	public String getIdPelanggan(){
		return idPelanggan;
	}

	public void setNoOrder(String noOrder){
		this.noOrder = noOrder;
	}

	public String getNoOrder(){
		return noOrder;
	}

	public void setTgl(String tgl){
		this.tgl = tgl;
	}

	public String getTgl(){
		return tgl;
	}

	public void setTglDari(String tglDari){
		this.tglDari = tglDari;
	}

	public String getTglDari(){
		return tglDari;
	}

	public void setTglSampai(String tglSampai){
		this.tglSampai = tglSampai;
	}

	public String getTglSampai(){
		return tglSampai;
	}

	public void setTotal(String total){
		this.total = total;
	}

	public String getTotal(){
		return total;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"HistoryPenitipanItem{" + 
			"nama = '" + nama + '\'' + 
			",id_pelanggan = '" + idPelanggan + '\'' + 
			",no_order = '" + noOrder + '\'' + 
			",tgl = '" + tgl + '\'' + 
			",tgl_dari = '" + tglDari + '\'' + 
			",tgl_sampai = '" + tglSampai + '\'' + 
			",total = '" + total + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}