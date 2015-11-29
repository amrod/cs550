import edu.sju.SpellChecker;
import java.io.IOException;
import java.text.DecimalFormat;

public class RunSpellChecker {

    public static void main(String[] args) {
        SpellChecker sc = new SpellChecker();

        try{
            sc.loadDictionary(args[0]);
            System.out.print(sc.loadInputText(args[1]));
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
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
