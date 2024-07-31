package ngordnet.main;

import edu.princeton.cs.algs4.In;

/**
 * Created by hug.
 */
public class FileReaderDemo {
    public static void main(String[] args) {
        In in = new In("ngordnet/main/example_input_file.txt");
        String[] x = in.readString().split(",");

        /* Every time you call a read method from the In class,
         * it reads the next token from the file, assuming it is
         * of the specified type. The In class thinks of the "next"
         * token as whatever follows whitespace. That whitespace
         * may be spaces, tabs, and/or newlines. */

        /* Compare the calls below to the contents of ./ngordnet/main/example_input_file.txt */
    }
}
