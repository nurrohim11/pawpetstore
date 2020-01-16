package com.asus.ecommerceapp.model.produkdetail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProdukResponse implements Serializable {

	@SerializedName("Details")
	private Details details;

	public void setDetails(Details details){
		this.details = details;
	}

	public Details getDetails(){
		return details;
	}

	@Override
 	public String toString(){
		return 
			"ProdukResponse{" + 
			"details = '" + details + '\'' + 
			"}";
		}
}