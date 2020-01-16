package com.asus.ecommerceapp.model.penitipan;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class HistoryPenitipanResponse implements Serializable {

	@SerializedName("HistoryPenitipan")
	private List<HistoryPenitipanItem> historyPenitipan;

	public void setHistoryPenitipan(List<HistoryPenitipanItem> historyPenitipan){
		this.historyPenitipan = historyPenitipan;
	}

	public List<HistoryPenitipanItem> getHistoryPenitipan(){
		return historyPenitipan;
	}

	@Override
 	public String toString(){
		return 
			"HistoryPenitipanResponse{" + 
			"historyPenitipan = '" + historyPenitipan + '\'' + 
			"}";
		}
}