package com.asus.ecommerceapp.model.keranjang;

import java.util.List;
import java.io.Serializable;

public class KeranjangResponse implements Serializable {
	private List<KeranjangItem> keranjang;
	private String total;
	private boolean error;


	private String result;
	private String message;

	public void setKeranjang(List<KeranjangItem> keranjang){
		this.keranjang = keranjang;
	}

	public List<KeranjangItem> getKeranjang(){
		return keranjang;
	}

	public void setTotal(String total){
		this.total = total;
	}

	public String getTotal(){
		return total;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	@Override
 	public String toString(){
		return 
			"KeranjangResponseItem{" + 
			"keranjang = '" + keranjang + '\'' + 
			",total = '" + total + '\'' + 
			",error = '" + error + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}