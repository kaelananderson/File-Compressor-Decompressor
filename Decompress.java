import java.io.*;
import java.util.*;

public class Decompress {
    public static void main(String[] args) {
        boolean restart = true;

        do {

            Scanner kb = new Scanner(System.in);

            ObjectInputStream input;
            PrintWriter output;
            PrintWriter log;
            String line;

            if (args.length != 1) {
                System.out.println("Please use a single text file given through the command line");
                return;
            }

            File inFile = new File(args[0]);

            String inFileName = args[0].substring(0, args[0].length()-4);

            String[] map = new String[(int)inFile.length()/2];
            int tDoubleTally = 0;
            int tally = 0;
            for (int ch = ' '; ch<= '~'; ch++) {
                map[tally] = String.valueOf((char)ch);                
                tally++;
            }

            map[tally] = "\n";
            tally++;
            map[tally] = "\t";
            tally++;
            map[tally] = "\r";
            tally++;

            long startTime = System.currentTimeMillis();

            try
            {
                input = new ObjectInputStream(new FileInputStream(args[0]));
                output = new PrintWriter(new FileOutputStream(inFileName + ".txt"));
                log = new PrintWriter(new FileOutputStream(inFileName + ".txt.log"));

                int l = input.readInt();
                output.print(map[l]);
                int k;
                try {

                    while(true) {

                        k = input.readInt();

                        if (map[k] != null) {
                            output.print(map[k]);
                            map[tally] = map[l] + map[k].charAt(0);
                            tally++;
                        } else {
                            output.print(map[l] + map[l].charAt(0));
                            map[k] = map[l] + map[l].charAt(0);
                        }
                        l=k;

                        //doubling function for if the table grows too large

                        if (tally >= map.length) {
                            String[] oldMap = map;
                            map = new String[map.length * 2];
                            for (int i=0; i < oldMap.length; i++) {
                                map[i] = oldMap[i];
                            }
                            tDoubleTally++;
                        }
                    }
                } catch (EOFException e) {
                    long endTime = System.currentTimeMillis();
                    long totTime = endTime - startTime;

                    log.println("Decompression for file " + args[0]);
                    log.println("Decompression took " + (totTime/1000.0)+" seconds");
                    log.println("The table was doubled "+ tDoubleTally + " times");
                    output.close();
                    input.close();
                    log.close();                    
                }

            } catch (FileNotFoundException e) {
                System.out.println("File not found, please try again.");
                continue;

            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }

            System.out.print("Do you want to run the program again (y for yes and n for no): ");

            // If user input 'n' end the program 
            if (kb.next().equalsIgnoreCase("y")) {
                restart = true;
                System.out.println("Enter a new file to be decompressed: ");
		kb.nextLine();
                args[0] = kb.nextLine();
            } else {
                restart = false;
            }

        }
        // Continue program otherwise
        while (restart == true);

    }
}
