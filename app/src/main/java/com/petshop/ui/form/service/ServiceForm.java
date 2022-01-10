package com.petshop.ui.form.service;

import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.petshop.R;
import com.petshop.persistence.model.Service;
import com.petshop.ui.util.FragmentPermission;
import com.petshop.ui.util.SuccessFragment;
import com.petshop.util.InputForm;
import com.petshop.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ServiceForm extends FragmentPermission {

    protected TextView globalWarning;
    protected TextView inputNameWarning;
    protected TextView inputDescriptionWarning;
    protected TextView inputPriceWarning;
    protected TextView inputModeWarning;
    protected EditText inputName;
    protected EditText inputDescription;
    protected EditText inputPrice;
    protected Spinner inputMode;
    protected Button btnChooseImage;
    protected String serviceMode;

    protected void startVariables(View root){
        imageSelected = root.findViewById(R.id.imgService);
        globalWarning = root.findViewById(R.id.globalWarning);
        inputNameWarning = root.findViewById(R.id.warningName);
        inputDescriptionWarning = root.findViewById(R.id.warningDescription);
        inputPriceWarning = root.findViewById(R.id.warningPrice);
        inputModeWarning = root.findViewById(R.id.warningMode);
        btnChooseImage = root.findViewById(R.id.btnChooseImage);
        inputName = root.findViewById(R.id.inputName);
        inputDescription = root.findViewById(R.id.inputDescription);
        inputPrice = root.findViewById(R.id.inputPrice);
        inputMode = root.findViewById(R.id.inputMode);
        startBtnChooseImage(btnChooseImage);
        startSpinners();
    }

    protected void startSpinners(){
        String[] modeOptions = new String[]{"Modo", "Hora", "Dia", "Mes", "Consulta", "Año", "Km"};
        inputMode.setAdapter(new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, modeOptions));

        inputMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String mode = adapterView.getItemAtPosition(i).toString();
                serviceMode = i == 0? "null" : mode;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                serviceMode = "null";
            }
        });
    }

    protected void assingTextInputs(Service service){
        String priceService = service.getPrice().toString();
        imageSelected.setImageBitmap(BitmapFactory.decodeByteArray(service.getImage(), 0, service.getImage().length));
        inputName.setText(service.getName());
        inputDescription.setText(service.getDescription());
        inputPrice.setText(priceService.substring(0, Math.min(priceService.length(), 7)));
        inputMode.setSelection(((ArrayAdapter)inputMode.getAdapter()).getPosition(service.getMode()));
    }

    protected Service checkInputs(Service serviceExists){
        Map<EditText, InputForm> inputs = new HashMap<>();
        inputs.put(inputName, new InputForm(inputNameWarning, InputForm.TEXT, false, null));
        inputs.put(inputDescription, new InputForm(inputDescriptionWarning, InputForm.TEXT, false, null));
        inputs.put(inputPrice, new InputForm(inputPriceWarning, InputForm.DECIMAL, false, new ArrayList<>(Collections.singletonList(inputModeWarning))));

        Map<EditText, String> inputsChecked = Util.checkInputs(inputs);
        Integer inputsRequired = 3;

        if(inputsChecked.keySet().size() == 0){
            Service service = new Service();
            service.setId(serviceExists == null? null : serviceExists.getId());
            service.setName(getValue(inputName));
            service.setDescription(getValue(inputDescription));
            service.setPrice(Double.parseDouble(getValue(inputPrice)));
            service.setMode(serviceMode);
            service.setImage(Util.imageViewToByte(imageSelected));
            return service;
        }
        else {
            Util.launchWarnings(globalWarning, inputs, inputsChecked, getResources(), R.drawable.bg_borders_yellow, R.drawable.bg_borders_red, inputsRequired);
            return null;
        }
    }

    private String getValue(EditText input){
        return input.getText().toString();
    }

}
