import mediadb.*;

import java.util.Iterator;

public class test {

    public static void main(String args[]){
        SingleLinkedList<String> sll = new SingleLinkedList<>();
        sll.add("1");
        sll.add("2");
        sll.add("3");
        sll.add("4");

        Iterator<String> it = sll.iterator();


        System.out.println(it.next());  // 1
        it.remove();
        System.out.println(it.next());  // 2
        //it.remove();
        System.out.println(it.next());  // 3
        //it.remove();
        System.out.println(it.next());  // 4
        //it.remove();
        System.out.println(it.next());  // Error

        System.out.println("Loop: ");
        for (String s: sll) {
            System.out.println(s);
        }
    }
}
