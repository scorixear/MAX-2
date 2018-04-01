package gui;/*
 * class Fraction
 *
 * @author Katharina Will, Jannik Niedermayer, Luca G�ttle
 * @version 1.0
 *
 * liefert Klasse f�r Br�che. Z�hler und Nenner werden separat gespeichert.
 *
 */

import java.math.BigDecimal;
import java.math.BigInteger;

final class Fraction extends Number implements Comparable<Fraction> {
    public static final Fraction NaN = new Fraction("0", "0");
    public static final Fraction TEN = new Fraction("10", "1");


    BigInteger zaehler;
    BigInteger nenner;

    public Fraction(String zaehler, String nenner) {
        this.zaehler = new BigInteger(zaehler);
        this.nenner = new BigInteger(nenner);
        this.normalisieren();
        if (!this.nenner.equals(BigInteger.ZERO)) {
            this.kuerzen();
        } else {
            this.zaehler = BigInteger.ZERO;
        }
    }

    public Fraction(BigInteger zaehler, BigInteger nenner) {
        this.zaehler = zaehler;
        this.nenner = nenner;
        this.normalisieren();
        if (!this.nenner.equals(BigInteger.ZERO)) {
            this.kuerzen();
        } else {
            this.zaehler = BigInteger.ZERO;
        }
    }

    @Override
    public double doubleValue() {
        if (nenner.equals(BigInteger.ZERO)) {
            return Double.NaN;
        }
        BigDecimal z = new BigDecimal(zaehler);
        BigDecimal n = new BigDecimal(nenner);
        return zaehler.divide(nenner).doubleValue();
    }

    @Override
    public float floatValue() {
        if (nenner.equals(BigInteger.ZERO)) {
            return Float.NaN;
        }
        return zaehler.divide(nenner).floatValue();
    }

    @Override
    public long longValue() {
        return zaehler.divide(nenner).longValue();
    }

    @Override
    public int intValue() {
        return zaehler.divide(nenner).intValue();
    }

    public int compareTo(Fraction bigInt) {
        if (this.equals(NaN) && bigInt.equals(NaN)) {
            return 0;
        }
        if (this.equals(NaN)) {
            return 1;
        }
        if (bigInt.equals(NaN)) {
            return -1;
        }
        Fraction fr = bigInt;
        BigInteger big = this.zaehler.multiply(fr.nenner);
        BigInteger bigfr = fr.zaehler.multiply(this.nenner);
        return big.compareTo(bigfr);
    }

    @Override
    public boolean equals(Object arg0) {
        if (!(arg0 instanceof Fraction)) {
            return false;
        }
        Fraction frac = (Fraction) arg0;
        return this.zaehler.equals(frac.zaehler) && this.nenner.equals(frac.nenner);
    }

    public Fraction add(Fraction r) {
        if (this.equals(NaN) || r.equals(NaN))
            return NaN;
        return new Fraction((this.zaehler.multiply(r.nenner)).add((r.zaehler).multiply(this.nenner)),
                this.nenner.multiply(r.nenner));
    }

    public Fraction subtract(Fraction r) {
        if (this.equals(NaN) || r.equals(NaN))
            return NaN;
        return new Fraction(this.zaehler.multiply(r.nenner).subtract(r.zaehler).multiply(this.nenner),
                this.nenner.multiply(r.nenner));
    }

    public Fraction multiply(Fraction r) {
        BigInteger z = this.zaehler.multiply(r.zaehler);
        BigInteger n = this.nenner.multiply(r.nenner);
        return new Fraction(z, n);
    }

    public Fraction divide(Fraction r) {

        BigInteger z = this.zaehler.multiply(r.nenner);
        BigInteger n = this.nenner.multiply(r.zaehler);
        return new Fraction(z, n);
    }

    public String toString() {
        if (this.nenner.equals(BigInteger.ONE)) {
            return this.zaehler + "";
        } else {
            return this.zaehler.toString() + "/" + this.nenner.toString();
        }
    }

    public String toStringUnified() {
        //only to use for Strings whose nenner /zaehler is a)positive and b) less than or equal to 99
        //unifies representation so it looks fancy in console in an array
        if (this.isInteger()) {
            if (this.intValue() == 10) {
                return "[  10 ]";
            } else {
                return "[  " + this.toString() + "  ]";
            }
        }
        String s = "[";
        if (this.zaehler.compareTo(BigInteger.TEN) >= 0) {
            s += zaehler.toString() + "/"; //Bsp.: [11/
        } else {
            s += " " + zaehler.toString() + "/"; //Bsp.: [ 7/
        }
        if (this.nenner.compareTo(BigInteger.TEN) >= 0) {
            s += nenner.toString(); //[19/16
        } else {
            s += nenner.toString() + " "; //[19/6
        }
        return s + "]";
    }

    public boolean isInteger() {
        if (this.nenner.equals(BigInteger.ONE)) {
            return true;
        } else {
            return false;
        }
    }

    public void kuerzen() {
        // System.out.println("kuerzen-Werte "+this.zaehler +" "+this.nenner);
        BigInteger ggT = zaehler.gcd(nenner);
        zaehler = zaehler.divide(ggT);
        nenner = nenner.divide(ggT);
    }

    public void normalisieren() {
        if (this.nenner.compareTo(BigInteger.ZERO) < 0) {
            this.zaehler = this.zaehler.negate();
            this.nenner = this.nenner.negate();
        }
    }

    public boolean isPrime(BigInteger bigInt) {
        long n = bigInt.longValue();
        if (n < 2) {
            return false;
        }
        int x = 2;
        while (x < n) {
            if (n % x == 0) {
                return false;
            }
            x++;
        }
        return true;
    }

    public boolean isBothPrime() {
        if (isPrime(this.zaehler) && isPrime(this.nenner)) {
            return true;
        }
        return false;
    }

}