package ru.ufagkb21;


public class Study {
    private String method = "The result of the study: nCOVID-19 PCR testing results\nSARS-CoV2 RNA - NOT DETECTED";
    private String diagnosis = "Clinical diagnosis: examination Studies of a smear from the pharynx, nose;";
    private String result = "NOT DETECTED";
    private static int counter = 4782;
    private int numberReport;
    private int numberProduction;
    private String dateResultString;
    private String timeSampling = "10:00";
    private String timeResult;


    public Study (String dateResultString, int numberReport, int numberProduction) {
        this.dateResultString = dateResultString;
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


    public String getTimeResult () {
        // Время выдачи результата - заглушка
        switch (numberProduction)  {
            case 1 : timeResult = "15:10";
                break;
            case 2 : timeResult = "15:30";
                break;
            case 3 : timeResult = "16:20";
                break;
            default: timeResult = "16:40";
                break;
        }
        return timeResult;
    }

    @Override
    public String toString() {
        return diagnosis + "\n" + method + ";\n" +
                "No." + numberReport + " production No." + numberProduction +
                "\nDate and time of biomaterial sampling:\n" + getDateTimeSamplingString()  +
                "\nResult ready date and time:\n" + getDateTimeResultString();
    }

    public String getNumberReport() {
        return String.valueOf(numberReport);
    }

    public String getNumberProduction() {
        return String.valueOf(numberProduction);
    }

    public String getDateTimeResultString() {
        return dateResultString + " " + getTimeResult();
    }

    public String getDateTimeSamplingString() {
        return dateResultString + " " + timeSampling;
    }

    public String getResult() {
        return result;
    }
}
