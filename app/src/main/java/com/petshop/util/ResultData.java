package com.petshop.util;

import android.view.View;

public class ResultData {

    private final View currentView;
    private final Integer nextViewSuccess;
    private final Integer nextViewError;
    private final String messageSuccess;
    private final String messageError;

    public ResultData(View currentView, Integer nextViewSuccess, Integer nextViewError, String messageSuccess, String messageError) {
        this.currentView = currentView;
        this.nextViewSuccess = nextViewSuccess;
        this.nextViewError = nextViewError;
        this.messageSuccess = messageSuccess;
        this.messageError = messageError;
    }

    public View getCurrentView() {
        return currentView;
    }

    public Integer getNextViewSuccess() {
        return nextViewSuccess;
    }

    public Integer getNextViewError() {
        return nextViewError;
    }

    public String getMessageSuccess() {
        return messageSuccess;
    }

    public String getMessageError() {
        return messageError;
    }
}
