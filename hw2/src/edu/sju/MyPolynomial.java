package edu.sju;

import java.util.Iterator;
import java.util.ListIterator;

import static java.lang.Math.abs;

/**
 * MyPolynomial class that represents single-variable polynomials
 * (with non-negative exponents) by using a circular doubly linked list.
 *
 * Each term in the polynomial is stored in one node as a two-element
 * array where index 0 represents the coefficient and index 1 the exponent.
 */
public class MyPolynomial implements Iterable<Integer[]>{
    LinkedList <Integer[]> polyList = new LinkedList<>();

    public MyPolynomial(){}

    public MyPolynomial(Integer[][] terms){
        for (Integer[] term: terms){
            polyList.add(term);
        }
        removeZeroes(this);
    }

    /**
     * Constructor builds a new MyPolynomial from a LinkedList.
     * @param terms LinkedList of Integer arrays.
     */
    public MyPolynomial(LinkedList<Integer[]> terms){
        for (Integer[] term: terms){
            polyList.add(term);
        }
        removeZeroes(this);
    }

    /**
     * Copy constructor: copies the given polynomial.
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
     *  Sets the coefficient of the given exponent. If the term with the
     *  given exponent does not exist and coef!=0, it is added to the
     *  polynomial.
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
     * Compares this polynomial with the given polynomial and returns true
     * if they are equal, false if not.
     * @param otherPoly an instance of MyPolynomial to compare with this
     *                  polynomial
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

            if (!thisTerm[0].equals(otherTerm[0]) ||
                !thisTerm[1].equals(otherTerm[1]))
                return false;
        }

        return (!thisIter.hasNext() && !otherIter.hasNext());
    }

    /**
     * Computes the current size of the polynomial.
     * @return integer size of the polynomial
     */
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
            else
                continue;

            result.append(termToString(abs(term[0]), term[1]));
        }

        if (result.indexOf("+") == 1)
            result.delete(0, 3);

        return result.toString().replaceFirst("\\s*", "");

    }

    /**
     * Adds this polynomial and the argument polynomial.
     * @param other the polynomial to add.
     * @return a new MyPolynomial of the resulting addition.
     */
    public MyPolynomial add(MyPolynomial other){
        MyPolynomial copyOther = new MyPolynomial(other);
        return sum(copyOther);
    }

    /**
     * Subtracts argument polynomial from this polynomial.
     * @param other the polynomial to subtract.
     * @return a new MyPolynomial of the resulting subtraction.
     */
    public MyPolynomial subtract(MyPolynomial other){
        MyPolynomial copyOther = new MyPolynomial(other);
        for (Integer[] term: copyOther){
            term[0] *= -1;
        }
        return sum(copyOther);
    }

    /**
     * Adds this polynomial and the argument polynomial.
     * @param other the polynomial to add.
     * @return a new MyPolynomial of the resulting addition.
     */
    private MyPolynomial sum(MyPolynomial other){

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

                    itemResult[0] += itemThis[0];

                    itemThis = (iterThis.hasNext()) ? iterThis.next() : null;
                    itemResult = (iterResult.hasNext()) ? iterResult.next() : null;

                } else if (comp < 0) {  // exponent of itemResult smaller than itemThis
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

        return removeZeroes(result);
    }

    /**
     * Multiplies this polynomial by the argument polynomial.
     * @param other the polynomial to multiply by.
     * @return a new MyPolynomial of the resulting multiplication.
     */
    public MyPolynomial multiply(MyPolynomial other){
        MyPolynomial result = new MyPolynomial(new Integer[][]{{0,0}});

        if (other.getSize() == 0 || polyList.getSize() == 0)
            return result;

        LinkedList<MyPolynomial> polynomials = new LinkedList<>();
        for (Integer[] term : this) {
            LinkedList<Integer[]> currTerms = new LinkedList<>();

            for (Integer[] otherTerm : other) {
                Integer[] newTerm = new Integer[2];
                newTerm[0] = term[0] * otherTerm[0];
                newTerm[1] = term[1] + otherTerm[1];
                currTerms.add(newTerm);
            }

            polynomials.add(new MyPolynomial(currTerms));
        }

        for (MyPolynomial p: polynomials){
            if(result == null)
                result = p;
            else
                result = result.add(p);
        }

        removeZeroes(result);
        return result;
    }

    /**
     * Evaluates the polynomial by plugging the value of x.
     * @param x value of the single variable in the polynomial to plug.
     * @return result of the evaluation.
     */
    public int evaluate(int x){
        int total = 0;
        for (Integer[] term: polyList){
            total += term[0] * Math.pow(x, term[1]);
        }
        return total;
    }

    /**
     * Remove terms with coefficient==0. If the resulting polynomial has no
     * terms, a single term of 0^0 is added.
     * @param poly the polynomial to removed zero-coefficients from.
     * @return the same polynomial received as argument, with
     * zero-coefficients removed, or a single term of 0^0 if all coefficients
     * were removed.
     */
    private MyPolynomial removeZeroes(MyPolynomial poly){
        ListIterator<Integer[]> iter = poly.iterator();
        Integer[] term;

        while (iter.hasNext()){
            term = iter.next();
            if (term[0] == 0){
                iter.remove();
            }
        }

        if (poly.getSize() == 0)
            iter.add(new Integer[]{0,0});

        return poly;
    }

    private String coefTermToString(Integer coef){

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

        strCoeff = coefTermToString(abs(coef));
        strPwr = varTermToString(exp);

        result.append(strCoeff);

        if (strCoeff.equals("") && strPwr.equals(""))
            result.append("1");

        result.append(strPwr);

        return result.toString();
    }
}
