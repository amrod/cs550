package edu.sju;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Interpreter {
    ArrayList<String> instructions;

    public void Interpreter(){}
    public void Interpreter(ArrayList<String> i){
        this.instructions = i;
    }

    public void loadInstructionFile(String filePath) throws IOException{
        this.instructions = readInstructons(filePath);
    }

    private ArrayList<String> readInstructons(String path) throws IOException {
        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<String> instructions = new ArrayList<String>();

        String line;
        while ((line = bufferedReader.readLine()) != null){
            line = line.trim();
            if (line.length() > 0)
                instructions.add(line);
        }

        fileReader.close();
        return instructions;
    }

    private boolean isValidInstruction(String[] parts){

        if (parts.length < 1 || parts.length > 2)
            return false;
        else if (!AVLTree.isMethodSupported(parts[0]) || parts[0].equals("quit"))
            return false;

        return true;
    }

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

    public AVLTree<String> run() throws InterruptedException{
        AVLTree<String> tree = new AVLTree<String>();
        try {
            String[] parts;
            for (String str : instructions) {
                parts = makeCommandParts(str);

                if (parts.length > 1)
                    tree.runMethod(parts[0], parts[1]);
                else
                    tree.runMethod(parts[0]);
            }
            return tree;
        } catch (InterruptedException e){
            throw e;
        } catch (Exception e){
            System.out.println(e.toString());
            return null;
        }
    }
}
