package edu.sju;

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

    public int degree(){
        return polyList.get(0)[1];
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

    private String coeffTermToString(Integer coeff){

        if (coeff == 1)
            return "";
        else
            return Integer.toString(coeff);
    }

    private String varTermToString(Integer power){

        StringBuilder result = new StringBuilder();

        if (power == 1)
            result.append("x");
        else if (power > 1)
            result.append("x^").append(power);
        // else result is empty string

        return result.toString();
    }

    private String termToString(Integer coeff, Integer power){
        String strCoeff, strPwr;
        StringBuilder result = new StringBuilder();

        strCoeff = coeffTermToString(abs(coeff));
        strPwr = varTermToString(power);

        result.append(strCoeff);

        if (strCoeff.equals("") && strPwr.equals(""))
            result.append("1");

        result.append(strPwr);

        return result.toString();
    }
}
