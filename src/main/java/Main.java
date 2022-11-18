import support.ValidityCheck;
import support.log.SysLog;

import java.util.Scanner;
import java.util.logging.Level;

import static support.log.SysLog.logger;

public class Main {
    public static void main(String[] args) {
        SysLog.init();
        ValidityCheck validNum = new ValidityCheck();
        Scanner myObj = new Scanner(System.in);
        String input;
        System.out.print("Skriv in det nummer som ska valideras. \n" +
                "För att avsluta skriv 'None'\n");
        while (true) {
            System.out.print("Nummer att validera: ");
            input = myObj.nextLine();
            if (input.equals("None")) {
                break;
            }
            try {
                validNum.valid(input);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                logger.log(Level.WARNING, "", e);
            }
        }
        myObj.close();
        logger.info("end of program");

    }

}