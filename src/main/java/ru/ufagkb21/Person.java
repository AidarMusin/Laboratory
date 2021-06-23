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
    public Person (int number, String lastName,String firstName,String dateOfBirth,String passportNumber, String dateResultString, String timeResult, int numberReport,int numberProduction) {
        this.number = number;
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        if (passportNumber != null)
            this.passportNumber = passportNumber.substring(0,2) + " " + passportNumber.substring(2);
        this.study = new Study(dateResultString, timeResult, numberReport, numberProduction);
    }

    public int getNumber() {
        return number;
    }

    public String getPatient() {
        return lastName + " " + firstName;
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

    public String getResultReadyDate () {
        return "Result ready date: " + study.getDateResultString();
    }

    public String getResult () {
        if (passportNumber != null) {
            return lastName + " " + firstName + "\n" +
                    "Date of Birth: " + dateOfBirth + ";" + "\n" +
                    "Passport: No " + passportNumber + ";" + "\n" +
                    hospital  + "\n" +
                    study.toString();
        } else
            return lastName.toUpperCase() + " " + firstName.toUpperCase() + "\n" +
                    "Date of Birth: " + dateOfBirth + ";" + "\n" +
                    hospital  + "\n" +
                    study.toString();
    }

    public String[] getDataForWriteExcel () {
        String[] result;
        int i;
        if (passportNumber != null) {
            i = 8;
            result = new String[i];
        } else {
            i = 7;
            result = new String[i];
        }
        result[0] = "" + String.valueOf(number);
        result[1] = "Report  No." + study.getNumberReport() + " of  the molecular biological study by polymerase chain reaction";
        result[2] = "Patient: " + lastName + " " + firstName;
        if (passportNumber != null)
            result[i-5] = "Passport: " + passportNumber;
        result[i-4] = "Date of Birth: " + dateOfBirth;
        result[i-3] = "SARS-CoV2 RNA – " + study.getResult();
        result[i-2] = "No." + study.getNumberReport() + " production No." + study.getNumberProduction();
        result[i-1] = "Result ready date: " + study.getDateResultString();
        return result;
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
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return lastName.equals(person.lastName) &&
                firstName.equals(person.firstName) &&
                dateOfBirth.equals(person.dateOfBirth) &&
                passportNumber.equals(person.passportNumber);
    }

    /** переопределить хэш*/
//    @Override
//    public int hashCode() {
//        return Objects.hash(lastName, firstName, dateOfBirth, passportNumber);
//    }

}