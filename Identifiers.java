import TreePackage.*;
import java.io.*;
import java.util.*;

    
public class Identifiers {

    public static void main(String args[]) {
        String fileName = getFileName();
        System.out.println();

        BinarySearchTree<String> unique = getPossibleIds(fileName);

        // step 32: print identifiers in sorted order using in-order iterator
        Iterator<String> iter = unique.getInorderIterator();
        while (iter.hasNext())
            System.out.println(iter.next());
    }
    
   /**
     * Get the possible identifiers from the file.
     *
     * @return    A tree of possible identifiers from the file.
     */
    private static BinarySearchTree<String> getPossibleIds(String theFileName) {
        Scanner input;
        BinarySearchTree<String> possible = new BinarySearchTree<String>();
        
        try {
            input = new Scanner(new File(theFileName));

            // step 29: loop over lines
            while (input.hasNextLine()) {
                String line = input.nextLine();
                // step 30: tokenize each line; delimiters mark end of an identifier
                StringTokenizer st = new StringTokenizer(line, " \t+\\-*/;=#@!{}");
                // step 31: add each token to the BST
                while (st.hasMoreTokens()) {
                    String token = st.nextToken();
                    possible.add(token);
                }
            }
        } catch (IOException e) {
            System.out.println("There was an error with System.in");
            System.out.println(e.getMessage());
        }
        return possible;
    }
       
    private static String getFileName() {
        Scanner input;
        String inString = "data.txt";
        
        try {
            input = new Scanner(System.in);
            System.out.println("Please enter the name of the file:");
            inString = input.next();            
        } catch (Exception e) {
            System.out.println("There was an error with System.in");
            System.out.println(e.getMessage());
            System.out.println("Will try the default file name data.txt");
        }
        return inString;
    }
}
