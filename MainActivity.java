package com.alandugger.ezconversion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    Spinner firstSpinner = (Spinner) findViewById(R.id.firstSpinner);
    Spinner secondSpinner = (Spinner) findViewById(R.id.conversionSpinner);
    ArrayAdapter<CharSequence> firstAdapter = ArrayAdapter.createFromResource(this, R.array.units_array, R.layout.support_simple_spinner_dropdown_item);
    ArrayAdapter<CharSequence> weightsAdapter = ArrayAdapter.createFromResource(this, R.array.weights_array, R.layout.support_simple_spinner_dropdown_item);
    ArrayAdapter<CharSequence> volumesAdapter = ArrayAdapter.createFromResource(this, R.array.volumes_array, R.layout.support_simple_spinner_dropdown_item);
    ArrayAdapter<CharSequence> lengthsAdapter = ArrayAdapter.createFromResource(this, R.array.lengths_array, R.layout.support_simple_spinner_dropdown_item);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lock the second spinner and set the adapter for the first spinner to all available units
        firstSpinner.setAdapter(firstAdapter);
        secondSpinner.setEnabled(false);


    }
}
