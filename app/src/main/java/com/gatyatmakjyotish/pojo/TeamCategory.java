package com.gatyatmakjyotish.pojo;

public class TeamCategory {
    private int imageId;
    private String name;
    private String desc;

public TeamCategory(String desc,int imageId, String name) {
this.imageId = imageId;
this.name = name;
this.desc = desc;
}
public int getImageId() {
return imageId;
}
public void setImageId(int imageId) {
this.imageId = imageId;
}
public String getDesc() {

    return desc;

}

public void setDesc(String desc) {

    this.desc = desc;

}

public String getName() {

    return name;

}

public void setName(String name) {

    this.name = name;

}

 @Override

  public String toString() {

    return name + "\n" + desc;

 }

}
