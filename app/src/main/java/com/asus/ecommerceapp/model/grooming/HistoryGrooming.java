package com.asus.ecommerceapp.model.grooming;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class HistoryGrooming implements Serializable {

	@SerializedName("nama")
	private String nama;

	@SerializedName("id_pelanggan")
	private String idPelanggan;

	@SerializedName("no_order")
	private String noOrder;

	@SerializedName("tgl")
	private String tgl;

	@SerializedName("tgl_booking")
	private String tglBooking;

	@SerializedName("jenis")
	private String jenis;

	@SerializedName("jenis_hewan")
	private String jenisHewan;

	@SerializedName("ukuran")
	private String ukuran;

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

	public void setTglBooking(String tglBooking){
		this.tglBooking = tglBooking;
	}

	public String getTglBooking(){
		return tglBooking;
	}

	public void setJenis(String jenis){
		this.jenis = jenis;
	}

	public String getJenis(){
		return jenis;
	}

	public void setJenisHewan(String jenisHewan){
		this.jenisHewan = jenisHewan;
	}

	public String getJenisHewan(){
		return jenisHewan;
	}

	public void setUkuran(String ukuran){
		this.ukuran = ukuran;
	}

	public String getUkuran(){
		return ukuran;
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
			"HistoryGrooming{" +
			"nama = '" + nama + '\'' + 
			",id_pelanggan = '" + idPelanggan + '\'' + 
			",no_order = '" + noOrder + '\'' + 
			",tgl = '" + tgl + '\'' + 
			",tgl_booking = '" + tglBooking + '\'' + 
			",jenis = '" + jenis + '\'' + 
			",jenis_hewan = '" + jenisHewan + '\'' + 
			",ukuran = '" + ukuran + '\'' + 
			",total = '" + total + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}