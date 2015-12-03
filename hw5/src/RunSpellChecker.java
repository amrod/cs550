import edu.sju.SpellChecker;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Spell checker front-end.
 */
public class RunSpellChecker {

    public static void main(String[] args) {
        if (args.length < 2){
            System.out.println("Please provide 2 arguments:\n <dictionary path> <input text path>");
            System.exit(-1);
        }

        SpellChecker sc = new SpellChecker();

        try{
            sc.loadDictionary(args[0]);
            System.out.print(sc.loadInputText(args[1]));
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
            System.exit(-1);
        }

        DecimalFormat df2 = new DecimalFormat("###.##");

        System.out.print("\n");
        System.out.print("no_collisions: ");
        System.out.println(sc.getTotalCollisions());

        System.out.print("average_chain_length: ");

        System.out.println(df2.format(sc.getAverageChainLength()));

        System.out.print("max_chain_length: ");
        System.out.println(sc.getMaxChainLength());

        System.out.print("load_factor: ");
        System.out.println(df2.format(sc.getLoadFactor()));
    }
}
