package com.gatyatmakjyotish;

import com.gatyatmakjyotish.ModelClass.PublishModel;

import java.io.Serializable;

public interface OnClickListener extends Serializable {
    void onClick(PublishModel publishModel, boolean isChecked);
}
