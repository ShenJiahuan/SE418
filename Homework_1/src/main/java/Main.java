import java.util.ArrayList;
import java.util.Scanner;
import com.shenjiahuan.WordLadder.*;

public class Main {
    private static WordLadder wordLadder;
    private static void initDict() throws java.io.IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter filename containing source text:");
        String filename = scanner.next();
        filename = "src/main/resources/" + filename;
        wordLadder = new WordLadder(filename);
    }

    private static String[] inputWords() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter start word (enter ':q' to quit):");
        String src = scanner.next();
        if (src.equals(":q")) {
            return null;
        }
        System.out.println("Enter destination word:");
        String dest = scanner.next();
        return new String[]{src, dest};
    }

    public static void main(String[] args) {
        try {
            initDict();
        } catch (java.io.IOException e) {
            System.err.println(e.getMessage());
            return;
        }

        while (true) {
            String src, dest;
            String[] tmp = inputWords();
            if (tmp == null) {
                return;
            }
            src = tmp[0];
            dest = tmp[1];

            ArrayList<String> ladder = wordLadder.get(src, dest);
            if (ladder == null) {
                System.out.println("No ladders found!");
            } else {
                System.out.print("Found ladder: ");
                boolean first = true;
                for (String word : ladder) {
                    if (first) {
                        System.out.print(word);
                        first = false;
                    } else {
                        System.out.print(" -> " + word);
                    }
                }
                System.out.println();
            }
        }

    }
}
