package edu.sju;

import java.util.Iterator;

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

    public String toString(){
        String result = "";
       

        return result;
    }

}
