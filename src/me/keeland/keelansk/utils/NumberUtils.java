package me.keeland.keelansk.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtils {
	
	public static String formatDouble(final double number) {
        NumberFormat formater = new DecimalFormat("##.##");

        return formater.format(number).replaceAll(",", ".");
    }

    public static String formatPercentDouble(final double number) {
        DecimalFormat formater = new DecimalFormat("0.00");

        return formater.format(number).replaceAll(",", ".");
    }

    public static String formatInt(final int number) {
        final NumberFormat formater = NumberFormat.getInstance(Locale.UK);

        return formater.format(number);
    }
}