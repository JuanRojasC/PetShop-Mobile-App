package com.petshop.ui.form.product;

import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.petshop.R;
import com.petshop.persistence.model.Product;
import com.petshop.persistence.service.ProductService;
import com.petshop.ui.util.FragmentPermission;
import com.petshop.ui.util.SuccessFragment;
import com.petshop.util.InputForm;
import com.petshop.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ProductForm extends FragmentPermission {

    protected TextView globalWarning;
    protected TextView inputNameWarning;
    protected TextView inputDescriptionWarning;
    protected TextView inputPriceWarning;
    protected TextView inputQuantityWarning;
    protected Button btnChooseImage;
    protected EditText inputName;
    protected EditText inputDescription;
    protected EditText inputPrice;
    protected EditText inputQuantity;

    protected void startVariables(View root){
        imageSelected = root.findViewById(R.id.imgProduct);
        globalWarning = root.findViewById(R.id.globalWarning);
        inputNameWarning = root.findViewById(R.id.warningName);
        inputDescriptionWarning = root.findViewById(R.id.warningDescription);
        inputPriceWarning = root.findViewById(R.id.warningPrice);
        inputQuantityWarning = root.findViewById(R.id.warningQuantity);
        inputName = root.findViewById(R.id.inputName);
        inputDescription = root.findViewById(R.id.inputDescription);
        inputPrice = root.findViewById(R.id.inputPrice);
        inputQuantity = root.findViewById(R.id.inputQuantity);
        btnChooseImage = root.findViewById(R.id.btnChooseImage);
    }

    protected void assingTextInputs(Product product){
        String priceProduct = product.getPrice().toString();
        imageSelected.setImageBitmap(BitmapFactory.decodeByteArray(product.getImage(),0, product.getImage().length));
        inputName.setText(product.getName());
        inputDescription.setText(product.getDescription());
        inputPrice.setText(priceProduct.substring(0, Math.min(priceProduct.length(), 7)));
        inputQuantity.setText(product.getQuantity() == null? "0" : product.getQuantity().toString());
    }

    protected Product checkInputs(Product productExists){
        Map<EditText, InputForm> inputs = new HashMap<>();
        inputs.put(inputName, new InputForm(inputNameWarning, InputForm.TEXT, false, null));
        inputs.put(inputDescription, new InputForm(inputDescriptionWarning, InputForm.TEXT, false, null));
        inputs.put(inputPrice, new InputForm(inputPriceWarning, InputForm.DECIMAL, false, new ArrayList<>(Collections.singletonList(inputQuantityWarning))));
        inputs.put(inputQuantity, new InputForm(inputQuantityWarning, InputForm.TEXT, true, new ArrayList<>(Collections.singletonList(inputPriceWarning))));

        Map<EditText, String> inputsChecked = Util.checkInputs(inputs);
        Integer inputsRequired = 3;

        if(inputsChecked.keySet().size() == 0){
            Product product = new Product();
            product.setId(productExists == null? null : productExists.getId());
            product.setName(getValue(inputName));
            product.setDescription(getValue(inputDescription));
            product.setPrice(Double.parseDouble(getValue(inputPrice)));
            product.setQuantity(Double.parseDouble(getValue(inputQuantity)));
            product.setImage(Util.imageViewToByte(imageSelected));
            return product;
        }
        else {
            Util.launchWarnings(globalWarning, inputs, inputsChecked, getResources(), R.drawable.bg_borders_blue, R.drawable.bg_borders_red, inputsRequired);
            return null;
        }
    }

    private String getValue(EditText input){
        return input.getText().toString();
    }

}
