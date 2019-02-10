import java.math.BigInteger;
import java.util.Random;

public class Main {

    public static void main(String args[]) {
        int nbValeur=1000;
        //testDecomp(nbValeur);
        //testExpMod(nbValeur);
        System.out.println(millerRabin(new BigInteger("13"),4));
    }

    public static void testDecomp (int nbValeur) {
        for (int i=0;i<nbValeur;i++) {
            BigInteger big = new BigInteger(1024, new Random());
            System.out.println("i="+(i+1));
            System.out.println("nb=" + big);
            BigInteger[] resultat = decomp(big);
            afficheDecomp(resultat);
            System.out.println("");
        }
    }

    public static void testExpMod(int nbValeur) {
        for (int i=0;i<nbValeur;i++) {
            BigInteger n=new BigInteger(256, new Random());
            while (n.equals(BigInteger.ZERO)) {
                n=new BigInteger(256, new Random());
            }
            BigInteger t=new BigInteger(15, new Random());
            while (t.equals(BigInteger.ZERO)) {
                t=new BigInteger(15, new Random());
            }
            BigInteger a=new BigInteger(256, new Random());
            System.out.println("i="+(i+1));
            System.out.println("n="+n);
            System.out.println("t="+t);
            System.out.println("a="+a);
            BigInteger res = expMod(n, a, t);
            System.out.println("res="+res);
            System.out.println("");
        }
    }

    public static void afficheDecomp(BigInteger[] resultat) {
        if (resultat != null) {
            BigInteger s=resultat[0];
            BigInteger d=resultat[1];
            System.out.println("s="+s);
            System.out.println("d="+d);
        } else {
            System.out.println("Impossible");
        }
    }

    public static BigInteger[] decomp(BigInteger n) {
        BigInteger[] resultat=new BigInteger[2];
        BigInteger two=new BigInteger("2");

        BigInteger s=new BigInteger("0");
        BigInteger d=new BigInteger(n.toString());
        d=d.subtract(BigInteger.ONE);

        //Si d est égal à 0, on retourne un tableau vide car c'est impossible
        if (d.equals(BigInteger.ZERO)) {
            return null;
        }

        //divisiblepar2=d mod 2 (0 true, 1 false)
        boolean divisiblepar2=d.mod(two).equals(BigInteger.ZERO);
        while (divisiblepar2) {
            s=s.add(BigInteger.ONE);
            d=d.divide(two);
            divisiblepar2=d.mod(two).equals(BigInteger.ZERO);
        }

        resultat[0]=s;
        resultat[1]=d;
        return resultat;

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

    /**
     *
     * @param n Un BigInteger > 3
     * @param cpt Un entier positif
     * @return 0 le BigInteger est composé / 1 le BigInteger est probablement premier / -1 entrée invalide
     */
    public static int millerRabin(BigInteger n, int cpt) {
        BigInteger trois=new BigInteger("3");
        if (n.compareTo(trois) <= 0) {
            return -1;
        }
    	BigInteger moinsUn = n.subtract(BigInteger.ONE);
    	BigInteger two = new BigInteger("2");
    	BigInteger minus = new BigInteger("-1");
    	BigInteger [] dcp;
    	BigInteger s,d,a,j,res;



    	for (int i= 0; i < cpt; i++) {
	    	dcp = decomp(n);
	    	s = dcp[0];
	    	d = dcp[1];
	    	Random rand = new Random();	    	
	        a = new BigInteger(moinsUn.bitLength(), rand);
	        // 5.compareTo(3) = 1
            // 5.compareTo(3) = 1
            System.out.println("aO="+a);
            System.out.println(a.compareTo(moinsUn));
	        while( !(a.compareTo(moinsUn) < 0 && a.compareTo(BigInteger.ONE) > 0) ) {
	            a = new BigInteger(n.bitLength(), rand);
                System.out.println("a="+a);
	        }
	        System.out.println("OK");

	        BigInteger eMod = expMod(n, a, d);
	        if (eMod.compareTo(BigInteger.ONE) == 0 || eMod.compareTo(minus) == 0) {
	        	break;
	        } else {
	        	j = BigInteger.ONE;
	        	while (j.compareTo(s) != 0) {
	        		// a^d(2^i) mod n
	        		res = expMod(n, a, d.multiply(puissance(two, new BigInteger(""+i))));
	        		if (res.compareTo(minus) == 0) {
	        			break;
	        		} else if (res.compareTo(minus) == 0) {
	        			return 0;
	        		}
	        	}
	        	res = expMod(n, a, d.multiply(puissance(two, s)));
	        	if (res.compareTo(BigInteger.ONE) != 0) {
	        		return 0;
	        	}
	        }
    	}
		return 1;    	
    }
    
}
