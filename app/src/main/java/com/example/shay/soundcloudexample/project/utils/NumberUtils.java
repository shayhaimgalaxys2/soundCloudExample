package com.example.shay.soundcloudexample.project.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class NumberUtils {

    private static final int _MIN_NUMBER_FOR_COMMAS = 1000;

    public static Integer getNumberFromStringInteger(String string) {
        try {
            return Integer.parseInt(string.replaceAll("\\D+", ""));
        } catch (Exception e) {
            FileUtils.saveException(e, LoggingHelper.getMethodName());
            return 0;
        }
    }

    public static double getNumberFromStringDouble(String string) {
        try {
            return Double.parseDouble(string);
        } catch (Exception e) {
            FileUtils.saveException(e, LoggingHelper.getMethodName());
            return -1;
        }
    }

    @SuppressLint("DefaultLocale")
    public static String formatToDecimal(double d) {
        if (d == (int) d) {
            return getWithCommas(String.format("%d", (int) d));
        } else {
            float number = Math.round(d * 2) / 2f;
            if (number == (int) number) {
                return getWithCommas(String.format("%d", (int) number));
            }
            return String.format("%s", number);
        }
    }

    public static String getWithCommas(String number) {
        long numberString = 0;
        try {
            numberString = Long.parseLong(number);
        } catch (Exception e) {
            FileUtils.saveException(e, LoggingHelper.getMethodName());
            return number;
        }
        if (numberString < _MIN_NUMBER_FOR_COMMAS) {
            return String.valueOf(numberString);
        }

        NumberFormat formatter = new DecimalFormat("###,###,###,###");
        return formatter.format(numberString);

    }

    public static boolean isPricePlus(String priceAsString) {
        try {
            double price = Double.parseDouble(priceAsString);
            return price >= 0;
        } catch (NumberFormatException exception) {
            FileUtils.saveException(exception, LoggingHelper.getMethodName());
            return false;
        }
    }

    public static String getPriceTextSolution(String priceAsString,
                                              Context context) {
        // String nis = context.getString(R.string.nis);
        String nis = "";
        try {
            priceAsString = formatStringToDouble(priceAsString);
            if (isPricePlus(priceAsString)) {
                return priceAsString + "+" + nis;
            } else {
                return replacePlaceOfMinus(priceAsString) + nis;
            }
        } catch (NumberFormatException exception) {
            FileUtils.saveException(exception, LoggingHelper.getMethodName());
            return priceAsString;
        }
    }


    public static String getPriceTextSmallerThanOne(String priceAsString,
                                                    Context context) {

        double price = Double.parseDouble(priceAsString);
        DecimalFormat df = new DecimalFormat("0.00");
        priceAsString = df.format(price);
        return priceAsString;
    }

    private static String formatStringToDouble(String priceAsString) {
        double price = Double.parseDouble(priceAsString);
        DecimalFormat df = new DecimalFormat("#.00");
        priceAsString = df.format(price);
        return priceAsString;
    }

    private static String formatStringToDoubleWithZero(String priceAsString) {
        double price = Double.parseDouble(priceAsString);
        DecimalFormat df = new DecimalFormat("0.00");
        priceAsString = df.format(price);
        return priceAsString;
    }

    public static String formatDoubleWithNothingAfterDecimalPoint(double number) {

        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(number);
    }


    private static String formatStringToInteger(String priceAsString) {
        int price = Integer.parseInt(priceAsString);

        return String.valueOf(price);
    }

    private static String replacePlaceOfMinus(String price) {
        if (price.contains("-")) {
            price = price.replace("-", "");
            LoggingHelper.d("price without " + price);
            price = price + "-";
            LoggingHelper.d("price after " + price);
        }
        return price;
    }

    public static boolean priceIsNotZero(String priceAsString) {
        try {
            double price = Double.parseDouble(priceAsString);
            return price > 0 || price < 0;
        } catch (NumberFormatException exception) {
            FileUtils.saveException(exception, LoggingHelper.getMethodName());
            return false;
        }
    }

    public static boolean stringIsnumber(String numberAsString) {
        try {
            Double.parseDouble(numberAsString);
            return true;
        } catch (NumberFormatException e) {
            FileUtils.saveException(e, LoggingHelper.getMethodName());
            return false;
        }
    }

    public static String convertUnixTimeStampToDate(String unixTimeStamp) {
        try {
            long unixSeconds = Long.valueOf(unixTimeStamp);
            Date date = new Date(unixSeconds * 1000L); // *1000 is to convert
            // seconds to
            // milliseconds
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy"); // the
            // format
            // of
            // your
            // date
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));
            String formattedDate = sdf.format(date);
            return formattedDate;
        } catch (Exception e) {
            return "";
        }
    }

    public static long getUnixTimeStampOfRightNow() {
        return System.currentTimeMillis() / 1000L;
    }

}
