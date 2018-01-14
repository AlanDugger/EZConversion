package com.alandugger.ezconversion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adugg on 1/14/2018.
 * Table for converting various units, singleton class only used for convert method
 */

class ConversionTable {

    private static ConversionTable instanceOf;
    private HashMap<String, Double> conversionTable = new HashMap<>();

    private ConversionTable(){

        // Volumes as measured in fluid oz
        conversionTable.put("cup", 8.0);
        conversionTable.put("fl oz", 1.0);
        conversionTable.put("gal", 128.0);
        conversionTable.put("L", 33.814);
        conversionTable.put("mL", 0.033814);
        conversionTable.put("pint", 16.0);
        conversionTable.put("qt", 32.0);
        conversionTable.put("Tbsp", 0.5);
        conversionTable.put("tsp", 0.166667);

        // Weights as measured in ounces
        conversionTable.put("g", 0.035274);
        conversionTable.put("kg", 35.274);
        conversionTable.put("lb", 16.0);
        conversionTable.put("mg", .000035274);
        conversionTable.put("oz", 1.0);

        // Lengths as measured in millimeters
        conversionTable.put("cm", 10.0);
        conversionTable.put("in", 25.4);
        conversionTable.put("m", 1000.0);
        conversionTable.put("mm", 1.0);
        conversionTable.put("ft", 304.8);
    }

    static ConversionTable getInstance(){
        if (instanceOf == null)
            instanceOf = new ConversionTable();
        return instanceOf;
    }

    Double convert(Double amount, String firstUnitType, String secondUnitType){

        return (conversionTable.get(firstUnitType) * amount) / (conversionTable.get(secondUnitType));
    }
}
