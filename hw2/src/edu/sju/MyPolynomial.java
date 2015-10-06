package edu.sju;

import java.util.Iterator;
import java.util.ListIterator;

import static java.lang.Math.abs;

public class MyPolynomial implements Iterable<Integer[]>{
    private int SUM = 0;
    private int SUB = 1;
    private int MUL = 2;

    LinkedList <Integer[]> polyList = new LinkedList<>();

    public MyPolynomial(){}

    public MyPolynomial(Integer[][] terms){
        for (Integer[] term: terms){
            polyList.add(term);
        }
    }

    /**
     * COpy constructor: copies the given polynomial.
     * @param p a polynomial to copy.
     */
    public MyPolynomial(MyPolynomial p){

        for(Integer[] term: p){
            polyList.add(term.clone());
        }
    }

    public ListIterator<Integer[]> iterator(){
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

    public int getSize(){
        return polyList.getSize();
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

    public MyPolynomial add(MyPolynomial other){
        MyPolynomial copyOther = new MyPolynomial(other);
        return operate(copyOther, SUM);
    }

    public MyPolynomial subtract(MyPolynomial other){
        MyPolynomial copyOther = new MyPolynomial(other);
        for (Integer[] term: copyOther){
            term[0] *= -1;
        }
        return operate(copyOther, SUM);
    }

    private MyPolynomial operate(MyPolynomial other, int operation){

        if (other.getSize() == 0)
            return new MyPolynomial(this);

        if (this.getSize() == 0)
            return other;

        MyPolynomial result = new MyPolynomial(other);

        ListIterator<Integer[]> iterThis = this.iterator();
        ListIterator<Integer[]> iterResult = result.iterator();

        Integer[] itemThis = iterThis.next();
        Integer[] itemResult = iterResult.next();

        while (true) {
            int comp;

            if (itemThis == null && itemResult == null)
                break;

            if (itemThis != null && itemResult != null) {
                comp = itemResult[1].compareTo(itemThis[1]);
                if (comp == 0) {  // exponents are equal

                    if (operation == SUM)
                        itemResult[0] += itemThis[0];
                    else if (operation == MUL)
                        itemResult[0] *= itemThis[0];
                    else if (operation == SUB)
                        itemResult[0] -= itemThis[0];

                    itemThis = (iterThis.hasNext()) ? iterThis.next() : null;
                    itemResult = (iterResult.hasNext()) ? iterResult.next() : null;

                } else if (comp < 0) {  // exponent of itemResult is smaller than itemThis
                    iterResult.previous();
                    iterResult.add(itemThis);
                    iterResult.next();
                    itemThis = (iterThis.hasNext()) ? iterThis.next() : null;
                } else {  // exponent of itemResult is bigger than itemThis
                    itemResult = (iterResult.hasNext()) ? iterResult.next() : null;
                }

            } else if (itemResult == null) {
                iterResult.add(itemThis);
                itemThis = (iterThis.hasNext()) ? iterThis.next() : null;
            }
            else {  // itemThis == null
                itemResult = (iterResult.hasNext()) ? iterResult.next() : null;
            }
        }

        return result;
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
