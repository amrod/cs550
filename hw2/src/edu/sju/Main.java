package edu.sju;

public class Main {

    public static void main(String[] args){
        MyPolynomial poly1 = new MyPolynomial(new Integer[][] {{10, 100}, {1, 14}, {1, 0}});
        System.out.println(poly1.degree());
        System.out.println(poly1.getCoef(100));
        poly1.setCoef(2, 14);
        System.out.println(poly1.getCoef(14));
    }

}
