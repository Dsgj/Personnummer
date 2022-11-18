package support;

public class Luhn {

    public static int calculateChecksum(int num) {
        int cs = 0;
        int multiple = 2;
        while (num > 0) {
            int pos = multiple * (num % 10);
            cs += pos % 10 + pos / 10;
            multiple = (multiple == 1 ? 2 : 1);
            num = num / 10;
        }
        return (10 - (cs % 10)) % 10;
    }
}