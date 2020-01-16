package com.asus.ecommerceapp.model.produk;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class HistoryProdukItem implements Serializable {

	@SerializedName("nama")
	private String nama;

	@SerializedName("id_pelanggan")
	private String idPelanggan;

	@SerializedName("no_order")
	private String noOrder;

	@SerializedName("tgl")
	private String tgl;

	@SerializedName("alamat_Pengiriman")
	private String alamatPengiriman;

	@SerializedName("ongkir")
	private String ongkir;

	@SerializedName("total")
	private String total;

	@SerializedName("status")
	private String status;

	@SerializedName("kurir")
	private String kurir;

	@SerializedName("no_resi")
	private String noResi;

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

	public void setAlamatPengiriman(String alamatPengiriman){
		this.alamatPengiriman = alamatPengiriman;
	}

	public String getAlamatPengiriman(){
		return alamatPengiriman;
	}

	public void setOngkir(String ongkir){
		this.ongkir = ongkir;
	}

	public String getOngkir(){
		return ongkir;
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

	public void setKurir(String kurir){
		this.kurir = kurir;
	}

	public String getKurir(){
		return kurir;
	}

	public void setNoResi(String noResi){
		this.noResi = noResi;
	}

	public String getNoResi(){
		return noResi;
	}

	@Override
 	public String toString(){
		return 
			"HistoryProdukItem{" + 
			"nama = '" + nama + '\'' + 
			",id_pelanggan = '" + idPelanggan + '\'' + 
			",no_order = '" + noOrder + '\'' + 
			",tgl = '" + tgl + '\'' + 
			",alamat_Pengiriman = '" + alamatPengiriman + '\'' + 
			",ongkir = '" + ongkir + '\'' + 
			",total = '" + total + '\'' + 
			",status = '" + status + '\'' + 
			",kurir = '" + kurir + '\'' + 
			",no_resi = '" + noResi + '\'' + 
			"}";
		}
}