package com.gatyatmakjyotish.ModelClass.days_pkg;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DaysPlanetModel{

	@SerializedName("result_date")
	private String resultDate;

	@SerializedName("is_paid")
	private int isPaid;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	@SerializedName("object")
	private List<ObjectItem> object;

	public String getResultDate(){
		return resultDate;
	}

	public int getIsPaid(){
		return isPaid;
	}

	public boolean isError(){
		return error;
	}

	public String getMessage(){
		return message;
	}

	public List<ObjectItem> getObject(){
		return object;
	}
}