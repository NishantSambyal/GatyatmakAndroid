package com.gatyatmakjyotish.ModelClass.days_pkg;

import com.google.gson.annotations.SerializedName;

public class ObjectItem{

	@SerializedName("sequence")
	private String sequence;

	@SerializedName("feeling_hindi")
	private String feelingHindi;

	@SerializedName("planetary")
	private String planetary;

	@SerializedName("description")
	private String description;

	@SerializedName("feeling")
	private String feeling;

	@SerializedName("group_hindi")
	private String groupHindi;

	@SerializedName("group")
	private String group;

	@SerializedName("description_hindi")
	private String descriptionHindi;

	public String getSequence(){
		return sequence;
	}

	public String getFeelingHindi(){
		return feelingHindi;
	}

	public String getPlanetary(){
		return planetary;
	}

	public String getDescription(){
		return description;
	}

	public String getFeeling(){
		return feeling;
	}

	public String getGroupHindi(){
		return groupHindi;
	}

	public String getGroup(){
		return group;
	}

	public String getDescriptionHindi(){
		return descriptionHindi;
	}
}