package com.asus.ecommerceapp.model.keranjang;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class TotalKeranjang implements Serializable {

	@SerializedName("total")
	private String total;

	public void setTotal(String total){
		this.total = total;
	}

	public String getTotal(){
		return total;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"total = '" + total + '\'' + 
			"}";
		}
}