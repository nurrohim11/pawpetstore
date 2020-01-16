package com.asus.ecommerceapp.model.produk;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ItemOrderResponse implements Serializable {

	@SerializedName("Item")
	private List<ItemItem> item;

	public void setItem(List<ItemItem> item){
		this.item = item;
	}

	public List<ItemItem> getItem(){
		return item;
	}

	@Override
 	public String toString(){
		return 
			"ItemOrderResponse{" + 
			"item = '" + item + '\'' + 
			"}";
		}
}