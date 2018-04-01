import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Hash {
	
	public static int B;
	public static Random random;
	public static PrintWriter writer;
	
	private static int getRandomNumberInRange(Random random, int min, int max) {
		
		if (min >= max) {
			throw new IllegalArgumentException("Max must be greater than min");
		}
		
		return random.nextInt((max - min) + 1) + min;
		
	}

	public static void main(String[] args) {
		
		Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
            		System.out.println("Closing");
                writer.close();
            }
        });
		
		String filename = "results" + System.currentTimeMillis() + ".txt";
		try {
			writer = new PrintWriter(filename, "UTF-8");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		
		random = new Random();
		int numTests = 10;
		
		for (B = 1; B < 32; B++) {
			
			writer.println("Beginning test for B = " + B);
			
			long startTime = System.currentTimeMillis();
			performMultipleTests(numTests);
			long stopTime = System.currentTimeMillis();
			
			long elapsedTime = stopTime - startTime;
			
			writer.println("Tests completed in " + (double)elapsedTime/1000.0 + " seconds");
			writer.println("Average execution time is " + (double)(elapsedTime/1000.0)/(double)numTests + " seconds");
			writer.println();
			
		}
		
		writer.close();
	}
	
	public static void performSingleTest() {
		byte[] P = new byte[B];
		random.nextBytes(P);
		
		long i = 0;
		while (true) {
			System.out.println("Attempt # " + i);
			i++;
			
			String message = new RandomString(getRandomNumberInRange(random, 1, 100), random).nextString();
			System.out.println("Message M: " + message);
			byte[] h = hash(message);
			byte[] temp = Arrays.copyOfRange(h,  h.length - B, h.length);
			
			System.out.print("h(M): "); printBytes(h);
			System.out.print("Last B bytes of h(M):"); printBytes(temp);
			System.out.print("P: "); printBytes(P);
			System.out.println();
			
			if (Arrays.equals(temp, P)) {
				System.out.println("FOUND IT");
				break;
			}
		}
	}
	
	public static void performMultipleTests(int numTests) {
		
		for (int i = 0; i < numTests; i++) {
			long startTime = System.currentTimeMillis();
			performSingleTest();
			long stopTime = System.currentTimeMillis();
			
			long elapsedTime = stopTime - startTime;
			
			writer.println("Test # " + i + " completed in " + (double)elapsedTime/1000.0 + " seconds");
		}
	}
	
	public static void printBytes(byte[] bytes) {
		
		for (int i = 0; i < bytes.length; i++) {
			System.out.format("%02X ", bytes[i]);
		}
		System.out.println();
	}
	
	public static byte[] hash(String text) {
		
		MessageDigest md;
		byte[] digest = null;
		
		try {
			md = MessageDigest.getInstance("SHA-256");
			digest = md.digest(text.getBytes(StandardCharsets.UTF_8));
		}
		catch (Exception e) {
			// do nothing
		}
		
		return digest;
	}

}
