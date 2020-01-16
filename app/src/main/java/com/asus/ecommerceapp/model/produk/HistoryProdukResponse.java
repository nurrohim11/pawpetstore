package com.asus.ecommerceapp.model.produk;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class HistoryProdukResponse implements Serializable {

	@SerializedName("HistoryProduk")
	private List<HistoryProdukItem> historyProduk;

	public void setHistoryProduk(List<HistoryProdukItem> historyProduk){
		this.historyProduk = historyProduk;
	}

	public List<HistoryProdukItem> getHistoryProduk(){
		return historyProduk;
	}

	@Override
 	public String toString(){
		return 
			"HistoryProdukResponse{" + 
			"historyProduk = '" + historyProduk + '\'' + 
			"}";
		}
}