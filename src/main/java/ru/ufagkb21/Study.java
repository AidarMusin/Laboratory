package ru.ufagkb21;


public class Study {
    private String method = "The result of the study: COVID-19 PCR testing results\n" + "SARS-CoV2 RNA";
    private String diagnosis = "Clinical diagnosis: examination Studies of a smear from the pharynx, nose;";
    private String result = "NOT DETECTED";
    private static int counter = 4782;
    private int numberReport;
    private int numberProduction;
    private String dateResultString;
    private String timeResult;


    public Study (String dateResultString, String timeResult, int numberReport, int numberProduction) {

        this.dateResultString = dateResultString;
        this.timeResult = timeResult;
        if (numberReport == 0) {
            this.numberReport = counter++;
        } else {
            this.numberReport = numberReport;
        }
        if (numberProduction == 0) {
            this.numberProduction = 2;
        } else {
            this.numberProduction = numberProduction;
        }

    }



    @Override
    public String toString() {
        return diagnosis + "\n" + method + " - " + result + ";\n" +
                "No." + numberReport + " production No." + numberProduction +
                "\nResult ready date: " + dateResultString + " " + timeResult;
    }

    public String getNumberReport() {
        return String.valueOf(numberReport);
    }

    public String getNumberProduction() {
        return String.valueOf(numberProduction);
    }

    public String getDateResultString() {
        return dateResultString + " " + timeResult;
    }

    public String getResult() {
        return result;
    }
}
