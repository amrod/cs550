package edu.sju;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/***
 * Interpreter class facilitates parsing of an instruction file to create an
 * AVL tree.
 */
public class Interpreter {
    private ArrayList<String> instructions = null;

    /***
     * Constructs instance from pre-read instructions array.
     * @param i
     */
    public void Interpreter(ArrayList<String> i){
        this.instructions = i;
    }

    /***
     * Reads an instruction file into internal variable.
     * @param filePath Path to the instruction file to load.
     * @throws IOException if there is an error reading the file.
     */
    public void loadInstructionFile(String filePath) throws IOException{
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<String> instructions = new ArrayList<String>();

        String line;
        while ((line = bufferedReader.readLine()) != null){
            line = line.trim();
            if (line.length() > 0)
                instructions.add(line);
        }

        fileReader.close();
        this.instructions = instructions;
    }

    /***
     * Test if the given instruction parts are valid.
     * @param parts An array of either 1 or 2 elements. Element 0 must be a
     *              supported instruction. Element 1 may be an argument. If
     *              no argument is needed, only element 0 is required.
     * @return true if the instruction appears valid, false if not.
     */
    private boolean isValidInstruction(String[] parts){

        if (parts.length < 1 || parts.length > 2)
            return false;
        else if (!AVLTree.isMethodSupported(parts[0]) || parts[0].equals("quit"))
            return false;

        return true;
    }

    /***
     * Splits a string into command and argument (if an argument is present).
     * @param line Line of text read from an instruction file.
     * @return String array with the command parts identified.
     * @throws Exception if the command appears to be invalid.
     */
    private String[] makeCommandParts(String line) throws Exception{
        String[] parts = line.split("\t");

        if (isValidInstruction(parts))
            return parts;
        throw new Exception(String.format("Instruction \"%1s\" is not valid.", line));
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (String s: this.instructions){
            sb.append(s).append("\n");
        }
        return sb.toString();
    }

    /***
     * Runs the interpreter over the
     * @return The AVL tree created with the loaded instructions.
     * @throws Exception If instruction file has not been loaded or if there
     * is an error interpreting an instruction.
     */
    public AVLTree<Integer> run() throws Exception{
        if (instructions == null)
            throw new Exception(
                    "You must call loadInstructionFile() before running the interpreter.");

        AVLTree<Integer> tree = new AVLTree<Integer>();
        String[] parts;
        for (String str : instructions) {
            parts = makeCommandParts(str);

            if (parts.length > 1)
                try {
                    tree.runMethod(parts[0], Integer.parseInt(parts[1]));
                } catch (NumberFormatException e) {
                    throw new NumberFormatException(parts[1]);
                }
            else
                tree.runMethod(parts[0]);
        }
        return tree;
    }
}
