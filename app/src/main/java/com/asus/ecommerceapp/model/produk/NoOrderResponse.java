package com.asus.ecommerceapp.model.produk;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class NoOrderResponse implements Serializable {

	@SerializedName("no_order")
	private String noOrder;

	public void setNoOrder(String noOrder){
		this.noOrder = noOrder;
	}

	public String getNoOrder(){
		return noOrder;
	}

	@Override
 	public String toString(){
		return 
			"NoOrderResponse{" + 
			"no_order = '" + noOrder + '\'' + 
			"}";
		}
}