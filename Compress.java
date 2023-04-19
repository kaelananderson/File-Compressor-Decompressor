import java.util.*;
import java.io.*;

public class Compress {
    public static void main(String[] args) {
        boolean restart = true;

        do {

            Scanner kb = new Scanner(System.in);

            BufferedReader input;
            ObjectOutputStream output;
            PrintWriter log;
            String line;

            if (args.length != 1) {
                System.out.println("Please use a single text file given through the command line");
                return;
            }

            File inFile = new File(args[0]);

            HashTableChain<String, Integer> map = new HashTableChain<String, Integer>((int)inFile.length()/2);        
            int tally = 0;
            for (char ch = ' '; ch<= '~'; ch++) {
                map.put(String.valueOf(ch), tally);
                tally++;
            }

            map.put("\n", tally);
            tally++;
            map.put("\t", tally);
            tally++;
            map.put("\r", tally);
            tally++;
            long startTime = System.currentTimeMillis();        

            try {
                input = new BufferedReader(new FileReader(inFile));

                File outFile = new File(args[0] + ".zzz");
                output = new ObjectOutputStream(new FileOutputStream(outFile));

                log = new PrintWriter(new FileOutputStream(args[0] + ".zzz.log"));

                int k;
                line = String.valueOf((char)input.read());
                while ((k = input.read()) != -1) {
                    String prefix = line;
                    line = String.valueOf((char)k);

                    while(map.get(prefix+line) != null) {
                        prefix = prefix + line;
                        line = String.valueOf((char)input.read());
                    }

                    int binCode = (int)map.get(prefix);
                    output.writeInt(binCode);
                    if (line != "-1") {
                        map.put(prefix + line, tally);
                        tally++;
                    }
                }

                long endTime = System.currentTimeMillis();
                long totTime = endTime - startTime;

                log.println("Compression of " + args[0]);
                log.println("Compressed from " + (inFile.length()/1024.0) + " Kilobytes to " + (outFile.length()/1024.0) + "Kilobyte");
                log.println("Compression took " +(totTime/1000.0) + " seconds");
                log.println("The dictionary contains " + map.size() + " entries");
                log.println("The table was rehashed " + map.getTotRehash() + " times");
                log.close();
                input.close();
                output.close();
                
            }
            catch (FileNotFoundException e) {
                System.out.println("File not found, please try again.");
                continue;
            }

            catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }

            System.out.print("Do you want to run the program again (y for yes and n for no): ");

            // If user input 'n' end the program 
            if (kb.next().equalsIgnoreCase("y")) {
                restart = true;
                System.out.println("Enter a new file to be compressed: ");
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
