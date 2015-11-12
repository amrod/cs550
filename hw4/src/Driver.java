import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import edu.sju.AVLTree;
import edu.sju.Interpreter;

import java.io.IOException;
import java.lang.InterruptedException;

public class Driver {

    public static void main(String[] args) throws IOException{

        try {
            Interpreter itptr = new Interpreter();
            itptr.loadInstructionFile("test-hw4.txt");
            itptr.run();
        } catch (IOException e){
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            System.exit(0);
        }

    }
}
