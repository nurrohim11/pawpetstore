package com.asus.ecommerceapp.model.banner;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class BannerResponse implements Serializable {

	@SerializedName("Banner")
	private ArrayList<BannerItem> banner;

	public void setBanner(ArrayList<BannerItem> banner){
		this.banner = banner;
	}

	public ArrayList<BannerItem> getBanner(){
		return banner;
	}

	@Override
 	public String toString(){
		return 
			"BannerResponse{" + 
			"banner = '" + banner + '\'' + 
			"}";
		}
}