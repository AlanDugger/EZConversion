package com.alandugger.ezconversion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    // Dropdown menus
    private Spinner firstSpinner;
    private Spinner secondSpinner;

    //  Adapters for dropdown spinners
    private ArrayAdapter<CharSequence> firstAdapter;
    private ArrayAdapter<CharSequence> weightsAdapter;
    private ArrayAdapter<CharSequence> volumesAdapter;
    private ArrayAdapter<CharSequence> lengthsAdapter;

    //Text fields & Strings
    private TextView convertedText;
    private EditText inputText;

    private String firstUnitType;
    private String secondUnitType;

    private String[] weightsArray;
    private String[] volumesArray;
    private String[] lengthsArray;

    private double inputAmount = 0.0;
    private Double convertedAmount = 0.0;
    private int currentType = 0;
    private final int TYPE_WEIGHT = 1;
    private final int TYPE_VOLUME = 2;
    private final int TYPE_LENGTH = 3;

    @Override
    protected void onSaveInstanceState(Bundle out){
        super.onSaveInstanceState(out);

        out.putDouble("inputString", inputAmount);
        out.putDouble("convertedString", convertedAmount);
        out.putInt("currentTypeKey", currentType);
        out.putInt("firstSpinnerSelection", firstSpinner.getSelectedItemPosition());
        out.putInt("secondSpinnerSelection", secondSpinner.getSelectedItemPosition());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstSpinner = findViewById(R.id.firstSpinner);
        secondSpinner = findViewById(R.id.conversionSpinner);

        //Text fields
        convertedText = findViewById(R.id.convertedText);
        inputText = findViewById(R.id.inputText);

        //  Adapters for dropdown spinners
        firstAdapter = ArrayAdapter.createFromResource(this, R.array.units_array, R.layout.support_simple_spinner_dropdown_item);
        weightsAdapter = ArrayAdapter.createFromResource(this, R.array.weights_array, R.layout.support_simple_spinner_dropdown_item);
        volumesAdapter = ArrayAdapter.createFromResource(this, R.array.volumes_array, R.layout.support_simple_spinner_dropdown_item);
        lengthsAdapter = ArrayAdapter.createFromResource(this, R.array.lengths_array, R.layout.support_simple_spinner_dropdown_item);

        weightsArray = getResources().getStringArray(R.array.weights_array);
        volumesArray = getResources().getStringArray(R.array.volumes_array);
        lengthsArray =  getResources().getStringArray(R.array.lengths_array);

        firstUnitType = "";
        secondUnitType = "";

        // Lock the second spinner and set the adapter for the first spinner to all available units
        firstSpinner.setAdapter(firstAdapter);
        secondSpinner.setAdapter(firstAdapter);
        secondSpinner.setEnabled(false);

        if (savedInstanceState != null)
        {
            Log.d("EZConvert", "SavedStateFound");

            Double value = savedInstanceState.getDouble("inputString");
            inputText.setText(value.toString());

            value = savedInstanceState.getDouble("convertedString");
            convertedText.setText(value.toString());

            currentType = savedInstanceState.getInt("currentTypeKey");
            switch (currentType){
                case TYPE_LENGTH:
                    secondSpinner.setAdapter(lengthsAdapter);
                    secondSpinner.setEnabled(true);
                    break;
                case TYPE_VOLUME:
                    secondSpinner.setAdapter(volumesAdapter);
                    secondSpinner.setEnabled(true);
                    break;
                case TYPE_WEIGHT:
                    secondSpinner.setAdapter(weightsAdapter);
                    secondSpinner.setEnabled(true);
                    break;
            }

            firstSpinner.setSelection(savedInstanceState.getInt("firstSpinnerSelection"));
            secondSpinner.setSelection(savedInstanceState.getInt("secondSpinnerSelection"));
        }

        firstSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                firstUnitType = (String) parent.getItemAtPosition(position);

                for (String s: weightsArray)
                    if (s.equals(firstUnitType)){
                        if (currentType != TYPE_WEIGHT) {
                            secondSpinner.setAdapter(weightsAdapter);
                            secondSpinner.setEnabled(true);
                            currentType = TYPE_WEIGHT;
                        }
                    }
                for (String s: volumesArray)
                    if (s.equals(firstUnitType)){
                        if (currentType != TYPE_VOLUME) {
                            secondSpinner.setAdapter(volumesAdapter);
                            secondSpinner.setEnabled(true);
                            currentType = TYPE_VOLUME;
                        }
                    }

                for (String s: lengthsArray)
                    if (s.equals(firstUnitType)){
                        if (currentType != TYPE_LENGTH) {
                            secondSpinner.setAdapter(lengthsAdapter);
                            secondSpinner.setEnabled(true);
                            currentType = TYPE_LENGTH;
                        }
                    }

                checkConversion(inputAmount, firstUnitType, secondUnitType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    try{
                        inputAmount = Double.valueOf(inputText.getText().toString());
                        checkConversion(inputAmount, firstUnitType, secondUnitType);
                    }  catch (NumberFormatException ex){
                        // Disregard, should only occur if they start a decimal with . instead of 0
                    }


                }else{
                    convertedText.setText("");
                }
            }
        });

        secondSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                secondUnitType = (String) parent.getItemAtPosition(position);
                checkConversion(inputAmount, firstUnitType, secondUnitType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // Function to determine if a conversion is possible and if so make the conversion
    // Function is called upon selecting a Unit, entering an amount, or selecting a convert-to Unit
    public void checkConversion(double amount, String firstUnitType, String secondUnitType){

        if (amount < 0 || firstUnitType.equals("") || secondUnitType.equals(""))
            return;


        convertedAmount = ConversionTable.getInstance().convert(amount, firstUnitType, secondUnitType);
        convertedText.setText(String.format(Locale.getDefault(),"%.3f", convertedAmount));
    }

}
