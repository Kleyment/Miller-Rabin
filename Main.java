import java.math.BigInteger;
import java.util.Random;

public class Main {

    public static void main(String args[]) {
        int nbValeur=1000;
        for (int i=0;i<nbValeur;i++) {
            BigInteger big=new BigInteger(6, new Random());
            System.out.println("nb="+big);
            decomp(big);
            System.out.println("");
        }
    }

    public static void decomp(BigInteger n) {
        BigInteger two=new BigInteger("2");

        BigInteger s=new BigInteger("0");
        BigInteger d=new BigInteger(n.toString());
        d=d.subtract(BigInteger.ONE);

        boolean divisiblepar2=d.mod(two).equals(BigInteger.ZERO);
        while (divisiblepar2) {
            s=s.add(BigInteger.ONE);
            d=d.divide(two);
            divisiblepar2=d.mod(two).equals(BigInteger.ZERO);
        }

        System.out.println("s="+s);
        System.out.println("d="+d);
    }
}
