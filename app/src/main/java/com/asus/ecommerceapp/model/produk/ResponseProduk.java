package com.asus.ecommerceapp.model.produk;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseProduk implements Serializable {

	@SerializedName("Produk")
	private List<ProdukItem> produk;

	public void setProduk(List<ProdukItem> produk){
		this.produk = produk;
	}

	public List<ProdukItem> getProduk(){
		return produk;
	}

	@Override
 	public String toString(){
		return 
			"ResponseProduk{" + 
			"produk = '" + produk + '\'' + 
			"}";
		}
}