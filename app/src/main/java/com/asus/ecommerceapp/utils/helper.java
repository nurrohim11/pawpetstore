package com.asus.ecommerceapp.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class helper {
    private String toRupiah(String nominal){
        String hasil = "";
        DecimalFormat toRupiah = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatAngka = new DecimalFormatSymbols();
        formatAngka.setCurrencySymbol("Rp. ");
        formatAngka.setMonetaryDecimalSeparator(',');
        formatAngka.setMonetaryDecimalSeparator('.');
        toRupiah.setDecimalFormatSymbols(formatAngka);
        hasil = toRupiah.format(Double.valueOf(nominal));
        return hasil;
    }
}
