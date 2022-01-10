package com.petshop.util;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class InputForm {
    
    public static final String TEXT = "string";
    public static final String DECIMAL = "decimal";
    public static final String INTEGER = "integer";
    public static final String EMAIL = "email";
    
    private TextView warning;
    private String typeData;
    private Boolean nullable;
    private List<TextView> cellMates;

    public InputForm(TextView warning, String typeData, Boolean nullable, List<TextView> cellMates) {
        this.warning = warning;
        this.typeData = typeData;
        this.nullable = nullable;
        this.cellMates = cellMates != null? cellMates : new ArrayList<>();
    }

    public void hideWarning(){
        this.warning.setVisibility(View.GONE);
    }

    public void showWarning(){
        this.warning.setVisibility(View.VISIBLE);
    }

    public void showWarning(String message){
        this.warning.setText(message);
        this.warning.setVisibility(View.VISIBLE);
        if(cellMates.size() > 0){
            for(TextView mate : cellMates) mate.setVisibility(View.VISIBLE);
        }
    }


    public TextView getWarning() {
        return warning;
    }

    public void setWarning(TextView warning) {
        this.warning = warning;
    }

    public String getTypeData() {
        return typeData;
    }

    public void setTypeData(String typeData) {
        this.typeData = typeData;
    }

    public Boolean getNullable() {
        return nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public List<TextView> getCellMates() {
        return cellMates;
    }

    public void setCellMates(List<TextView> cellMates) {
        this.cellMates = cellMates;
    }
}
