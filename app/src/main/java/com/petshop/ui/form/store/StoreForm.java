package com.petshop.ui.form.store;

import android.annotation.SuppressLint;
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
import com.petshop.persistence.model.Store;
import com.petshop.ui.util.FragmentPermission;
import com.petshop.ui.util.SuccessFragment;
import com.petshop.util.InputForm;
import com.petshop.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StoreForm extends FragmentPermission {

    protected TextView globalWarning;
    protected TextView inputAddressWarning;
    protected TextView inputDescriptionWarning;
    protected TextView inputLatitudeWarning;
    protected TextView inputLongitudeWarning;
    protected TextView inputCityWarning;
    protected TextView inputStateWarning;
    protected EditText inputAddress;
    protected EditText inputDescription;
    protected EditText inputLatitude;
    protected EditText inputLongitude;
    protected Spinner inputCity;
    protected Spinner inputState;
    protected Button btnChooseImage;
    protected String city;
    protected String state;

    protected void startVariables(View root){
        imageSelected = root.findViewById(R.id.imgStore);
        globalWarning = root.findViewById(R.id.globalWarning);
        inputAddressWarning = root.findViewById(R.id.warningAddress);
        inputDescriptionWarning = root.findViewById(R.id.warningDescription);
        inputLatitudeWarning = root.findViewById(R.id.warningLatitude);
        inputLongitudeWarning = root.findViewById(R.id.warningLongitude);
        inputCityWarning = root.findViewById(R.id.warningCity);
        inputStateWarning = root.findViewById(R.id.warningState);
        inputAddress = root.findViewById(R.id.inputAddress);
        inputDescription = root.findViewById(R.id.inputDescription);
        inputLatitude = root.findViewById(R.id.inputLatitude);
        inputLongitude = root.findViewById(R.id.inputLongitude);
        inputCity = root.findViewById(R.id.inputCity);
        inputState = root.findViewById(R.id.inputState);
        btnChooseImage = root.findViewById(R.id.btnChooseImage);
        startBtnChooseImage(btnChooseImage);
        startSpinners();
    }

    protected void startSpinners(){
        String[] cityOptions = new String[]{"Ciudad", "Bogotá DC", "Medellin", "Cali", "Barranquilla", "Cartagena"};
        inputCity.setAdapter(new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, cityOptions));

        String[] stateOptions = new String[]{"Estado", "Cundinamarca", "Antioquia", "Valle del Cauca", "Atlantico", "Bolivar"};
        inputState.setAdapter(new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, stateOptions));

        inputCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String mode = adapterView.getItemAtPosition(i).toString();
                city = i == 0? "null" : mode;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                city = "null";
            }
        });

        inputState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String mode = adapterView.getItemAtPosition(i).toString();
                state = i == 0? "null" : mode;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                state = "null";
            }
        });
    }

    @SuppressLint("SetTextI18n")
    protected void assignTextInputs(Store store){
        imageSelected.setImageBitmap(BitmapFactory.decodeByteArray(store.getImage(), 0, store.getImage().length));
        inputAddress.setText(store.getAddress());
        inputDescription.setText(store.getDescription());
        inputLatitude.setText(store.getLatitude().toString());
        inputLongitude.setText(store.getLongitude().toString());
        inputCity.setSelection(((ArrayAdapter)inputCity.getAdapter()).getPosition(store.getCity()));
        inputState.setSelection(((ArrayAdapter)inputState.getAdapter()).getPosition(store.getState()));
    }

    protected Store checkInputs(Store storeExists){
        InputForm inputCityForm = new InputForm(inputCityWarning, InputForm.TEXT, false, null);
        InputForm inputStateForm = new InputForm(inputStateWarning, InputForm.TEXT, false, null);

        Map<EditText, InputForm> inputs = new HashMap<>();
        inputs.put(inputAddress, new InputForm(inputAddressWarning, InputForm.TEXT, false, null));
        inputs.put(inputDescription, new InputForm(inputDescriptionWarning, InputForm.TEXT, false, null));
        inputs.put(inputLatitude, new InputForm(inputLatitudeWarning, InputForm.DECIMAL, false, new ArrayList<>(Collections.singletonList(inputLongitudeWarning))));
        inputs.put(inputLongitude, new InputForm(inputLongitudeWarning, InputForm.DECIMAL, false, new ArrayList<>(Collections.singletonList(inputLatitudeWarning))));

        Map<EditText, String> inputsChecked = Util.checkInputs(inputs);
        int inputsRequired = 4;

        if(city.equals("null") && inputsChecked.keySet().size() != inputsRequired){
            inputCityForm.showWarning("campo obligatorio");
        }
        if(state.equals("null") && inputsChecked.keySet().size() != inputsRequired){
            inputStateForm.showWarning("campo obligatorio");
        }

        if(inputsChecked.keySet().size() == 0){
            Store store = new Store();
            store.setId(storeExists == null? null : storeExists.getId());
            store.setAddress(getValue(inputAddress));
            store.setDescription(getValue(inputDescription));
            store.setCity(city);
            store.setState(state);
            store.setLatitude(Double.parseDouble(getValue(inputLatitude)));
            store.setLongitude(Double.parseDouble(getValue(inputLongitude)));
            store.setImage(Util.imageViewToByte(imageSelected));
            return store;
        }
        else{
            Util.launchWarnings(globalWarning, inputs, inputsChecked, getResources(), R.drawable.bg_borders_red, R.drawable.bg_borders_blue, inputsRequired);
            return null;
        }
    }

    private String getValue(EditText input){
        return input.getText().toString();
    }

}
