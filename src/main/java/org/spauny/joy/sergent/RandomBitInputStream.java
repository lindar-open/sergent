/*
 * Will read bytes from a file which consist of skew corrected
 * bits.  This means that the should be an equal probability of
 * any byte being returned.  This should be very useful for seeding
 * PRNGs.
 */
package org.spauny.joy.sergent;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class RandomBitInputStream extends RandomAccessFile {

    public static final int BYTESIZE = 8;
    public static final byte ONEMASK = 1;
    private int unusedBits;
    private byte currentByte;
    // no need for this to be secure as we are relying on the
    // file for entropy all this needs to give is a random dist
    // of 0s and 1s when used in the skew correction algorithm
    // and random ints for when we seek out positions in the file.
    // Could use SecureRandom here if we were really paranoid.
    private final Random rnd = new Random();
    private final int fileLength;

    /*
     * We need a file for entropy - typically a large random.  We are
     * only interested in reading it so we set the mode to read only.
     */
    public RandomBitInputStream(String fileName) throws IOException{
	super(fileName, "r"); // read only
	fileLength = (int)super.length();
    }

    /*
     * Reads a random byte from the file.
     */
    private byte readRandomByte() throws IOException{
	super.seek(rnd.nextInt(fileLength));
	return super.readByte();
    }

    /*
     * Reads a bit from the file by reading a byte from the file
     * then returning the bits from the byte in a sequential order.
     * Once a byte has been read the next byte is read from a random
     * position in the file.
     */
    private boolean readRandomBit() throws IOException{
	if (unusedBits <= 0) {
	    currentByte = readRandomByte();
	    unusedBits = BYTESIZE;
	}
	// is sign bit set?
	boolean bit = (currentByte < 0);
	// shift left by 1 to move next bit in
	currentByte = (byte)(currentByte << 1);
	unusedBits--;
	return bit;
    }

    /*
     * A very simple algorithm to ensure that we have an equal chance of
     * getting a 0 or a 1.  Bits are read two at a time and if they differ
     * (0,1 or 1,0) we randomly select one of them otherwise they are the
     * same (0,0 or 1,1) so we read the next 2 bits and try again.  The only
     * problem here is if we have a file which is all 1s or all 0s or has large
     * clusters of each as in this case the method may either take a long time
     * to find 2 dif bits or else get stuck in an infinite loop.  However if
     * this condition was fulfilled it would be unlikely that the source file
     * is random.
     */
    private boolean readSkewCorrectedRandomBit() throws IOException{
	boolean skewCorrectedBit = true;
	boolean done = false;
	boolean[] bits = new boolean[2];
	while(!done){
	    bits[0] = readRandomBit();
	    bits[1] = readRandomBit();
	    if(bits[0] != bits[1]){
		skewCorrectedBit = bits[rnd.nextInt(2)];
		done = true;
	    }
	}
	return skewCorrectedBit;
    }

    /*
     * Creates a byte by calling readSkewCorrectedBit() 8 times.
     * Because the bits are not skewed there should be an equal
     * chance of any byte value being returned.
     */
    public byte readSkewCorrectedRandomByte() throws IOException{
	byte skewCorrectedRandomByte = 0;
	for(int i=0; i<BYTESIZE; i++){
	    boolean bit = readSkewCorrectedRandomBit();
	    // shift left by 1 to move next bit in
	    skewCorrectedRandomByte = (byte)(skewCorrectedRandomByte << 1);
	    if(bit) { // set low bit
                skewCorrectedRandomByte = (byte)(skewCorrectedRandomByte | ONEMASK);
            }
	}
	return skewCorrectedRandomByte;
    }

    /*
     * Tests to see if the class provides a equal chance of producing
     * a 0 or a 1.
     */
    private static void testUnskewedBits(String file, int size)
	throws IOException{

        try (RandomBitInputStream rbis = new RandomBitInputStream(file)) {
            double bits = 0;
            double unskewedBits = 0;
            for(int i=0; i<size; i++){
                boolean bit = rbis.readRandomBit();
                if(bit) {
                    bits++;
                }
                boolean unskewedBit = rbis.readSkewCorrectedRandomBit();
                if(unskewedBit) {
                    unskewedBits++;
                }
            }
            System.out.println("% +ve skewed   bits\t"+bits/size*100+
                    "\n% +ve unskewed bits\t"+unskewedBits/size*100);
        }
    }

    /*
     * Tests to see if the class provides an equal chance of producing
     * any byte value.
     */
    private static void testUnskewedBytes(String file, int size)
	throws IOException{
        try (RandomBitInputStream rbis = new RandomBitInputStream(file)) {
            double[] bytes = new double[256];
            double[] unskewedBytes = new double[256];
            for(int i=0; i<size; i++){
                bytes[rbis.readRandomByte()+128]++;
                unskewedBytes[rbis.readSkewCorrectedRandomByte()+128]++;
            }
            System.out.println("BYTE#, NORMAL, %UNSKEWED");
            for(int i=0; i<bytes.length; i++) {
                System.out.println(i-128 +", "+bytes[i]/size+", "+
                        unskewedBytes[i]/size);
            }
        }
    }

    public static void main(String[] args){
	if (args.length != 1){
	    System.out.println("Usage: java RandomBitInputStream 1000000 :where 1000000 is the number of iterations to test");
	    return;
	}
	try {
	    String file = "C:\\mark\\random";
	    int size = Integer.parseInt(args[0]);
	    // delete / uncomment following lines as appropriate
	    //testUnskewedBits(file, size);
	    testUnskewedBytes(file, size);
	} catch(NumberFormatException | IOException e){
	    System.out.println("I hate java: "+e);
	}
    }
}