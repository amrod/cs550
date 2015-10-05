package edu.sju;

import javax.management.timer.TimerNotification;
import java.util.Iterator;
import static java.lang.Math.abs;

public class MyPolynomial implements Iterable<Integer[]>{
    LinkedList <Integer[]> polyList = new LinkedList<>();

    public MyPolynomial(Integer[][] terms){
        for (Integer[] term: terms){
            polyList.add(term);
        }
    }

    public MyPolynomial(MyPolynomial p){

        for(Integer[] term: p){
            polyList.add(term);
        }
    }

    public Iterator<Integer[]> iterator(){
        return polyList.iterator();
    }

    /**
     * Get the degree of the polynomial.
     * @return the polynomial's degree
     */
    public int degree(){
        return polyList.get(0)[1];
    }

    /**
     * Get the coefficient corresponding to the given exponent/
     * @param exp the exponent
     * @return the coefficient
     */
    public int getCoef(int exp){
        for (Integer[] term: polyList){
            if (term[1] == exp){
                return term[0];
            }
        }
        return 0;
    }

    /**
     *  Sets the coefficient of the given exponent. If the term with the given exponent does not exist
     *  and coef!=0, it is added to the polynomial.
     * @param coef Coeficient value to set
     * @param exp Exponent for which the coefficient will be set
     */
    public void setCoef(int coef, int exp){
        Integer[] newTerm = {coef, exp};
        int i = 0;

        for(Integer[] term: polyList){
            if (term[1] == exp){
                term[0] = coef;
                return;
            } else if (term[1] < exp)
                break;
            i++;
        }

        if (coef != 0)
            polyList.add(i, newTerm);
    }

    /**
     * Compares this polynomial with the given polynomial and returns true if they are equal, false if not.
     * @param otherPoly an instance of MyPolynomial to compare with this polynomial
     * @return true if the polynomials are equal, false if not
     */
    public boolean equals(MyPolynomial otherPoly){
        Iterator<Integer[]> thisIter = polyList.iterator();
        Iterator<Integer[]> otherIter = otherPoly.iterator();
        Integer[] thisTerm;
        Integer[] otherTerm;

        while (thisIter.hasNext() && otherIter.hasNext()){
            thisTerm = thisIter.next();
            otherTerm = otherIter.next();

            if (!thisTerm[0].equals(otherTerm[0]) || !thisTerm[1].equals(otherTerm[1]))
                return false;
        }

        return (!thisIter.hasNext() && !otherIter.hasNext());
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();

        for (Integer[] term: polyList){
            if (term[0] > 0)
                result.append(" + ");
            else if (term[0] < 0)
                result.append(" - ");

            result.append(termToString(abs(term[0]), term[1]));
        }

        if (result.indexOf("+") == 1)
            result.delete(0, 3);

        return result.toString();
    }

    private String coeffTermToString(Integer coef){

        if (coef == 1)
            return "";
        else
            return Integer.toString(coef);
    }

    private String varTermToString(Integer exp){

        StringBuilder result = new StringBuilder();

        if (exp == 1)
            result.append("x");
        else if (exp > 1)
            result.append("x^").append(exp);
        // else result is empty string

        return result.toString();
    }

    private String termToString(Integer coef, Integer exp){
        String strCoeff, strPwr;
        StringBuilder result = new StringBuilder();

        strCoeff = coeffTermToString(abs(coef));
        strPwr = varTermToString(exp);

        result.append(strCoeff);

        if (strCoeff.equals("") && strPwr.equals(""))
            result.append("1");

        result.append(strPwr);

        return result.toString();
    }
}
