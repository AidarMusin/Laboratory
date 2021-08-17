package ru.ufagkb21;

public class Person {
    private int number;
    private String lastName;
    private String firstName;
    private String dateOfBirth;
    private String passportNumber;
    private Study study;
    private String hospital = "Ufa City Clinical Hospital No. 21 \nDepartment: Bacteriological laboratory;";


    /** person all param */
    public Person (int number, String lastName,String firstName,String dateOfBirth,String passportNumber, String dateResultString, int numberReport,int numberProduction) {
        this.number = number;
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        if (passportNumber != null)
            this.passportNumber = passportNumber.substring(0,2) + " " + passportNumber.substring(2);
        this.study = new Study(dateResultString, numberReport, numberProduction);
    }

    public int getNumber() {
        return number;
    }

    public String getPatient() {
        return lastName + " " + firstName;
    }

    public String getNameFileQrCode () {
        return number + "." + lastName + "_" + firstName + ".png";
    }

    public String getDateOfBirth() {
        return "Date of Birth: " + dateOfBirth;
    }

    public String getPassportNumber() {
        if (passportNumber != null) {
            return "Passport: " + passportNumber;
        } else
            return "";
    }

    public String getReport() {
        return "Report No." + study.getNumberReport() + " of the molecular biological study by polymerase chain reaction";
    }

    public String getProductionNumber () {
        return "No." + study.getNumberReport() + " production No." + study.getNumberProduction();
    }

    public String getResultReadyDateTime () {
        return study.getDateTimeResultString();
    }
    public String getSamplingDateTime () {
        return study.getDateTimeSamplingString();
    }


    public String getResult () {
        if (passportNumber != null) {
            return lastName + " " + firstName + "\n" +
                    "Date of Birth: " + dateOfBirth + ";" + "\n" +
                    "Passport No: " + passportNumber + ";" + "\n" +
                    "Ufa City Clinical Hospital No. 21"  + "\n" +
                    study.toString();
        } else
            return lastName.toUpperCase() + " " + firstName.toUpperCase() + "\n" +
                    "Date of Birth: " + dateOfBirth + ";" + "\n" +
                    "Ufa City Clinical Hospital No. 21"  + "\n" +
                    study.toString();
    }

    public String getOnlyDateOfBirth () {
        return dateOfBirth;
    }
    public String getOnlyPasNumber () {
        return passportNumber;
    }

    public String getOnlyReportNumber () {
        return study.getNumberReport();
    }

    public String getOnlyProdNumber () {
        return study.getNumberProduction();
    }

    public String getOnlyDateResult () {
        return study.getDateTimeResultString();
    }



    @Override
    public String toString() {
        if (passportNumber == null) {
            return "Patient No." + number + " : " + lastName + " " + firstName + ". № паспорта не указан";
        } else
            return "Patient No." + number + " : " + lastName + " " + firstName + ".";
    }

    /** переопределить equals  - нужен будет при записи пациентов в БД */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        if (getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return lastName.equals(person.lastName) &&
                firstName.equals(person.firstName) &&
                dateOfBirth.equals(person.dateOfBirth) &&
                passportNumber.equals(person.passportNumber);
    }
}