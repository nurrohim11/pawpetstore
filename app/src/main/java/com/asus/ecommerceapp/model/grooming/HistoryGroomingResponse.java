package com.asus.ecommerceapp.model.grooming;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class HistoryGroomingResponse implements Serializable {

	@SerializedName("HistoryGrooming")
	private List<HistoryGrooming> historyGrooming;

	public void setHistoryGrooming(List<HistoryGrooming> historyGrooming){
		this.historyGrooming = historyGrooming;
	}

	public List<HistoryGrooming> getHistoryGrooming(){
		return historyGrooming;
	}

	@Override
 	public String toString(){
		return 
			"HistoryGroomingResponse{" +
			"historyGrooming = '" + historyGrooming + '\'' + 
			"}";
		}
}