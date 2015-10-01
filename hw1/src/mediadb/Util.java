package mediadb;

public class Util {

    /**
     * Joins the elements of a String array into single String, with elements
     * separated by a new line.
     * @param arr A String array to be joined.
     * @return A string with the joined elements.
     */
    public static String join(String[] arr) {
        return Util.join(arr, "\n");
    }

    /**
     * Joins the elements of a String array into single String, with elements
     * separated by the given separator.
     * @param arr A String array to be joined.
     * @param separator A string to separate each element of arr.
     * @return A string with the joined elements.
     */
    public static String join(String[] arr, String separator) {
        if (separator == null) separator = "\n";

        StringBuilder ret = new StringBuilder();
        for (String s: arr){
            ret.append(s.trim()).append(separator);
        }

        return ret.toString();
    }

    /**
     * Splits a comma-separated string of values into an String array.
     * @param csv A string with values/substrings separated by comma.
     * @return A String array where each element represents a value from csv.
     */
    public static String[] parseCSV(String csv){
        String[] strings;
        strings = csv.split("[,]");
        return strings;
    }

    /**
     * Splits a comma-separated string of values into an String array, or
     * returns an empty String array if the contents of input match
     * emptyKeyword.
     * @param input A string with values/substrings separated by comma.
     * @param emptyKeyword A keyword to indicate that an empty array should be
     *                     returned.
     * @return A String array where each element represents a value from csv,
     * or an empty array if input matches emptyKeyword.
     */
    public static String[] parseCSV(String input, String emptyKeyword){
        String[] inputArr;

        if (emptyKeyword == null) emptyKeyword = "NONE";

        if (input.toUpperCase().equals(emptyKeyword))
            inputArr = new String[]{};
        else
            inputArr = Util.parseCSV(input);

        return inputArr;
    }

}
