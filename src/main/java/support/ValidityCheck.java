package support;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidityCheck {

    private static final Pattern regexPattern = Pattern.compile("^(" +
            "(\\d{2})?" + // Group 2 - Århundrade
            "(\\d{2})" + // Group 3 - Årtal/Decennium
            "(\\d{2})" + // Group 4 - Månad
            "(\\d{2})" + // Group 5 - Dag
            ")" + // Group 1 - Födelsedatum
            "([-|+]?)?" + // Group 6 - Symbol
            "((?!000)\\d{3})" + // Group 7 - Slutnummer
            "(\\d?)$"); // Group 8 - Kontrollsiffra
    private static Matcher matches;

    //Kontrollerar ifall ett födelsedatum är ett giltigt datum.
    public static boolean valDate(String date) throws Exception {
        DateFormat df = new SimpleDateFormat("yyMMdd");
        df.setLenient(false);
        try {
            df.parse(date);
            return true;
        } catch (ParseException e) {
            throw new Exception("Ogiltigt datum");
        }
    }

    //Kontrollerar ifall indata är ett personnummer
    public boolean checkIfPerson(String num) throws Exception {
        matches = regexPattern.matcher(num);

        if (!matches.find()) {
            throw new Exception("Ogiltig indata: " + num);
        }

        String birthdate = matches.group(1);
        String checkDate = (matches.group(3) + matches.group(4) + matches.group(5));

        if (birthdate.length() == 6) {
            return valDate(checkDate);
        } else if (birthdate.length() == 8) {
            String century = matches.group(2);
            return ((century.equals("18") || century.equals("19") || century.equals("20")) && valDate(checkDate));
        }
        return false;
    }

    //Kontrollerar ifall indata är ett samordningsnummer
    public boolean checkIfSam(String num) throws Exception {
        matches = regexPattern.matcher(num);

        if (!matches.find()) {
            throw new Exception("Ogiltig indata: " + num);
        }

        String decade = matches.group(3);
        String month = matches.group(4);
        int samNum = Integer.parseInt(matches.group(5));
        String numbers = matches.group(6);
        String control = matches.group(7);

        if (samNum > 60) {
            String realDate = String.valueOf(samNum - 60);
            return checkIfPerson(decade + month + realDate + numbers + control);
        }
        return false;
    }

    //Kontrollerar ifall indata är ett organisationsnummer
    public boolean checkIfOrg(String num) throws Exception {
        matches = regexPattern.matcher(num);

        if (!matches.find()) {
            throw new Exception("Ogiltig indata: " + num);
        }

        String fullNum = matches.group(1);
        int orgNr = Integer.parseInt(matches.group(4));

        if (fullNum.length() == 6) {
            return (orgNr >= 20);
        } else if (fullNum.length() == 8) {
            String header = matches.group(2);
            return ((header.equals("16")) && orgNr >= 20);
        }
        return false;
    }

    //Returnerar true ifall indata är ett personnummer men inte samordningsnummer
    public boolean isPersonnummer(String num) throws Exception {
        return (checkIfPerson(num) && !checkIfSam(num));
    }

    //Returnerar true ifall indata är ett personnummer OCH ett samordningsnummer
    public boolean isSamordningsnummer(String num) throws Exception {
        return (checkIfSam(num));
    }

    //Returnerar true ifall indata är ett organisationsnummer
    public boolean isOrganisationsnummer(String num) throws Exception {
        return checkIfOrg(num);
    }

    public String getLuhnCode(String num) throws Exception {
        matches = regexPattern.matcher(num);

        if (!matches.find()) {
            throw new Exception("Invalid input.");
        }
        return matches.group(3) + matches.group(4) + matches.group(5) + matches.group(7);
    }

    public boolean invalidID(String num) throws Exception {
        matches = regexPattern.matcher(num);
        if (!matches.find()) {
            throw new Exception("Invalid input");
        }
        int checkSum = Integer.parseInt(matches.group(8));
        return !(checkSum == Luhn.calculateChecksum(Integer.parseInt(getLuhnCode(num))));
    }

    public void valid(String num) throws Exception {
        if (isOrganisationsnummer(num)) {
            if (invalidID(num)) {
                throw new Exception("Ogiltigt organisationsnummer: " + num);
            }
            System.out.println("Giltigt organisationsnummer: " + num);
            return;
        } else if (isSamordningsnummer(num)) {
            if (invalidID(num)) {
                throw new Exception("Ogiltigt samordningsnummer: " + num);
            }
            System.out.println("Giltigt samordningsnummer: " + num);
            return;
        } else if (isPersonnummer(num)) {
            if (invalidID(num)) {
                throw new Exception("Ogiltigt personnummer: " + num);
            }
            System.out.println("Giltigt personnummer: " + num);
            return;
        }
        throw new Exception("Ogiltigt nummer: " + num);
    }
}