# File-Compressor-Decompressor

Compress is a Java program that takes an ascii file from the command line and shrinks it using the 
methods of HashTableChain and its separate chaining hashmap. It produces a binary file of the compressed 
file. It also makes a log file that keeps track of the file being compressed, the time elapsed, and the 
total times rehashed. Likewise, Decompress takes the compressed binary file and reproduces the originally 
given ascii file by perfect hashing. It also makes a log file that keeps track of the file being compressed, 
the time elapsed, and the total times rehashed. I implement the KWHashMap interface in the HashTableChain class 
that performs separate chaining and hashmapping.

*INSTRUCTIONS*

In the command line, run the file Compress.java followed by an ascii file you would like to compress. 

Provided are 3 separate test files.

The program will prompt you if you'd like to run the compression again or not.

To decompress, run the file Decompress.java followed by the original ascii file with the extension '.zzz' that signifies that file is the one that was previously compressed.

Again, the program will prompt you if you'd like to run the compression again or not.

Produced is the original ascii file with the '.txt' extension and log files for both the Compress and Decompress programs. 

Enjoy!