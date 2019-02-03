import java.math.BigInteger;
import java.util.Random;

public class Main {

    public static void main(String args[]) {
        int nbValeur=1000;
        /*for (int i=0;i<nbValeur;i++) {
            BigInteger big=new BigInteger(6, new Random());
            System.out.println("nb="+big);
            decomp(big);
            System.out.println("");
        }*/
        for (int i=0;i<nbValeur;i++) {
            BigInteger n=new BigInteger(6, new Random());
            BigInteger t=new BigInteger(6, new Random());
            BigInteger a=new BigInteger(6, new Random());
            System.out.println("n="+n + " t ="+t + " a="+a); 
            BigInteger res = expMod(n, a, t);
            System.out.println("res="+res);
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
    
    public static BigInteger puissance(BigInteger a, BigInteger t) {
    	BigInteger p = null;
    	BigInteger two = new BigInteger("2");
    	if (t.equals(BigInteger.ONE)) {
    		// retourne a si t = 1
    		p = a;
    	} else if (t.mod(two).equals(BigInteger.ZERO)) {
    		// retourne puissance(a², t/2) si t est pair
    		p = puissance(a.multiply(a), t.divide(two));
    	} else {
    		// retourne a x puissance(a², (t-1)/2) si t est impair
    		p = a.multiply(puissance(a.multiply(a), (t.subtract(BigInteger.ONE).divide(two))));
    	}
    	return p;
    }
    
    public static BigInteger expMod(BigInteger n, BigInteger a, BigInteger t) {
    	return puissance(a, t).mod(n);
    }
}
