import edu.sju.Interpreter;

import java.io.IOException;
import java.lang.InterruptedException;

public class Driver {

    public static void main(String[] args) throws IOException{
        String fpath;
        if (args.length > 0)
            fpath = args[0];
        else
            fpath = "test-hw4.txt";

        try {
            Interpreter itptr = new Interpreter();
            itptr.loadInstructionFile(fpath);
            itptr.run();
        } catch (IOException e){
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            System.exit(0);
        }

    }
}
