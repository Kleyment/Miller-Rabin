import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Random;

public class Main {

    public static void main(String args[]) {
        if (args.length > 0 ) {
            switch (args[0]) {
                case "-testDecomp":
                    if (args.length == 2) {
                        int nbDecomp=Integer.parseInt(args[1]);
                        testDecomp(nbDecomp);
                    } else {
                        System.out.println("Utilisation : ");
                        System.out.println("-testDecomp <valeur>");
                    }
                    break;
                case "-testExpMod":
                    if (args.length == 2) {
                        int nbExpMod=Integer.parseInt(args[1]);
                        testExpMod(nbExpMod);
                    } else {
                        System.out.println("Utilisation : ");
                        System.out.println("-testExpMod <valeur>");
                    }
                    break;
                case "-testEval":
                    if (args.length == 4) {
                        String sortie="";
                        int nbValeurs=Integer.parseInt(args[1]);
                        int nbBits=Integer.parseInt(args[2]);
                        int nbIterations=Integer.parseInt(args[3]);
                        sortie+="On obtient en moyenne "+testEval(nbValeurs,nbBits,nbIterations)+" itérations pour obtenir un nombre probablement premier.";
                        writeTestTxt(sortie);
                    } else {
                        System.out.println("Utilisation : ");
                        System.out.println("-testEval <nombre de valeurs> <nombre de bits> <nombre d'itérations de miller-rabin>");
                    }
                    break;
                case "-mr":
                    if (args.length == 3) {
                        String sortie="";
                        BigInteger nbHexa=new BigInteger(args[1],16);
                        int nbIterations=Integer.parseInt(args[2]);
                        int ml=millerRabin(nbHexa,nbIterations);
                        if (ml == 0) {
                            sortie+="Le nombre "+nbHexa+" est composé.";
                        } else if (ml == 1) {
                            sortie+="Le nombre "+nbHexa+" est probablement premier.";
                        }
                        writeTestTxt(sortie);
                    } else {
                        System.out.println("Utilisation : ");
                        System.out.println("-mr <nombre en hexa> <nombre d'itérations>");
                    }
                    break;
                case "-eval":
                    if (args.length == 3) {
                        String sortie="";
                        int nbBits=Integer.parseInt(args[1]);
                        int nbIterations=Integer.parseInt(args[2]);
                        sortie+="Il a fallu "+eval(nbBits,nbIterations)+" itérations pour obtenir un nombre probablement premier.";
                        writeTestTxt(sortie);
                    } else {
                        System.out.println("Utilisation : ");
                        System.out.println("-eval <nombre de bits> <nombre d'itérations de miller-rabin>");
                    }
                    break;
                default:
                    System.out.println("Utilisation : ");
                    System.out.println("-testDecomp <valeur>");
                    System.out.println("-testExpMod <valeur>");
                    System.out.println("-testEval <nombre de valeurs> <nombre de bits> <nombre d'itérations de miller-rabin>");
                    System.out.println("-mr <nombre en hexa> <nombre d'itérations>");
                    System.out.println("-eval <nombre de bits> <nombre d'itérations de miller-rabin>");
            }
        } else {
            System.out.println("Utilisation : ");
            System.out.println("-testDecomp <valeur>");
            System.out.println("-testExpMod <valeur>");
            System.out.println("-testEval <nombre de valeurs> <nombre de bits> <nombre d'itérations de miller-rabin>");
            System.out.println("-mr <nombre en hexa> <nombre d'itérations>");
            System.out.println("-eval <nombre de bits> <nombre d'itérations de miller-rabin>");
        }
    }

    public static void testDecomp (int nbValeur) {
        String sortie="";
        for (int i=0;i<nbValeur;i++) {
            BigInteger big = new BigInteger(1024, new Random());
            sortie+="i="+(i+1)+"\n";
            sortie+="nb=" + big+"\n";
            BigInteger[] resultat = decomp(big);
            sortie+=afficheDecomp(resultat)+"\n";
        }
        writeTestTxt(sortie);
    }

    public static void testExpMod(int nbValeur) {
        String sortie="";
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
            sortie+="i="+(i+1)+"\n";
            sortie+="n="+n+"\n";
            sortie+="t="+t+"\n";
            sortie+="a="+a+"\n";
            BigInteger res = expMod(n, a, t);
            sortie+="res="+res+"\n";
            sortie+="\n";
        }
        writeTestTxt(sortie);
    }
    
    public static int testEval (int nbValeur, int b, int cpt) {
    	int somme = 0;
        for (int i=0;i<nbValeur;i++) {
            somme += eval(b, cpt);
        }
        return somme/nbValeur;
    }

    public static String afficheDecomp(BigInteger[] resultat) {
        String sortie="";
        if (resultat != null) {
            BigInteger s=resultat[0];
            BigInteger d=resultat[1];
            sortie+="s="+s+"\n";
            sortie+="d="+d+"\n";
        } else {
            sortie+="Impossible\n";
        }
        return sortie;
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

    public static void writeTestTxt(String text) {
        try {
            PrintWriter pw=new PrintWriter("test.txt","UTF-8");
            pw.println(text);
            pw.close();
        } catch (Throwable t){
            t.printStackTrace(System.err) ;
            return;
        }
    }

}
