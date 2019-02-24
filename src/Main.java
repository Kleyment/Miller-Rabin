import java.math.BigInteger;
import java.util.Random;

public class Main {

    public static void main(String args[]) {
        int nbValeur=10000;
        //testDecomp(nbValeur);
        /*testExpMod(nbValeur);
        /*for (int i=1;i<=1000;i++) {
            System.out.println("i="+i);
            System.out.println(millerRabin(new BigInteger(""+i),20));
        }*/
        //System.out.println(millerRabin(new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE65381FFFFFFFFFFFFFFFF",16),20));
        //System.out.println(eval(128,20));
        //System.out.println(millerRabin(new BigInteger("7919"),20));
        System.out.println(testEval(100, 2048, 20));
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
            BigInteger n=new BigInteger(1024, new Random());
            while (n.equals(BigInteger.ZERO)) {
                n=new BigInteger(1024, new Random());
            }
            BigInteger t=new BigInteger(1024, new Random());
            while (t.equals(BigInteger.ZERO)) {
                t=new BigInteger(1024, new Random());
            }
            BigInteger a=new BigInteger(1024, new Random());
            System.out.println("i="+(i+1));
            System.out.println("n="+n);
            System.out.println("t="+t);
            System.out.println("a="+a);
            BigInteger res = expMod(n, a, t);
            System.out.println("res="+res);
            System.out.println("");
        }
    }
    
    public static int testEval (int nbValeur, int b, int cpt) {
    	int somme = 0;
        for (int i=0;i<nbValeur;i++) {
            somme += eval(b, cpt);
        }
        return somme/nbValeur;
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
    
    public static BigInteger expMod(BigInteger n, BigInteger a, BigInteger t) {
        BigInteger p = null;
        BigInteger two = new BigInteger("2");
        if  (t.equals(BigInteger.ZERO)) {
            return BigInteger.ONE;
        }
        if (t.equals(BigInteger.ONE)) {
            // retourne a si t = 1
            p = a.mod(n);
        } else if (t.mod(two).equals(BigInteger.ZERO)) {
            // retourne puissance(a², t/2) si t est pair
            p = expMod(n,a.multiply(a).mod(n), t.divide(two)).mod(n);
        } else {
            // retourne a x puissance(a², (t-1)/2) si t est impair
            p = a.multiply(expMod(n,a.multiply(a).mod(n), (t.subtract(BigInteger.ONE).divide(two)))).mod(n);
        }
        return p;
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
            if (s.compareTo(BigInteger.ZERO) == 0) {
            	return 0;
            }
	        while( !(a.compareTo(moinsUn) < 0 && a.compareTo(BigInteger.ONE) > 0) ) {
	            a = new BigInteger(n.bitLength(), rand);
	        }
	        BigInteger eMod = expMod(n, a, d);
	        if (eMod.compareTo(BigInteger.ONE) == 0 || eMod.compareTo(n.subtract(BigInteger.ONE)) == 0) {
	        	break;
	        } else {
	        	j = BigInteger.ONE;
	        	while (j.compareTo(s) != 0) {
	        		// a^d(2^i) mod n	   
	        		res = expMod(n, a, d.multiply(new BigInteger(""+j).pow(2)));
	        		j = j.add(BigInteger.ONE);
	        		if (res.compareTo(n.subtract(BigInteger.ONE)) == 0) {
	        			break;
	        		} else if (res.compareTo(BigInteger.ONE) == 0) {
	        			return 0;
	        		}
	        	}
	        	res = expMod(n, a, d.multiply(s.pow(2)));
	        	if (res.compareTo(BigInteger.ONE) != 0) {
	        		return 0;
	        	}
	        }
    	}
		return 1;    	
    }
    
    public static int eval(int b, int cpt) {
    	int compteur = 0;
    	BigInteger n = new BigInteger(b, new Random());
    	while (millerRabin(n, 20) == 0) {
    		compteur++;
    		n = new BigInteger(b, new Random());
    	}
    	return compteur;
    }
    
}
