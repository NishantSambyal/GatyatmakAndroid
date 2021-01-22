package com.gatyatmakjyotish.ModelClass;

public class PublishModel {
    private Integer publish;
    private int title;
    private int image;
    private int price;
    private Boolean value;


    public PublishModel(Integer publish, Integer image, Boolean value, int title, int price ) {
        this.publish = publish;
        if (image != null)
            this.image = image;
        this.value = value;
        this.title = title;
        this.price = price;
    }


    public Integer getPublish() {

        return publish;
    }

    public int getImage() {

        return image;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public int getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }
}
