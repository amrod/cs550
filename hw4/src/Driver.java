import edu.sju.Interpreter;

import java.io.IOException;
import java.lang.InterruptedException;

public class Driver {
    /***
     * Driver program allows loading an instruction file from the command line.
     * Defaults to test-hw4.txt.
     * @param args Command-line arguments. A single argument is recognized,
     *             must be the path to an existing instruction file.
     */
    public static void main(String[] args) {
        String fpath;
        if (args.length > 0)
            fpath = args[0];
        else
            fpath = "test-hw4.txt";

        try {
            Interpreter itptr = new Interpreter();
            itptr.loadInstructionFile(fpath);
            itptr.run();
        }catch (NumberFormatException e){
            System.out.println(String.format(
                    "Argument \"%1s\" is not a valid integer.",
                    e.getMessage()));
        } catch (IOException e){
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            System.exit(0);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
