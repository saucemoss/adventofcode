package adventofcode2015;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class Day_4 {
	static String puzzleInput = "bgvyzdsv";

	public static void main(String[] args) throws CloneNotSupportedException, NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("MD5");
		boolean found = false;
		Integer x = 0;
		while (true) {
			String secret = puzzleInput + x.toString();
			md.update(secret.getBytes());
			byte[] digest = md.digest();
			String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();

			if (myHash.startsWith("00000", 0) && !found) {
				System.out.println("Part one:\n\tSecret: " + secret + "\n\tHash: " + myHash);
				found = true;
			}
			if (myHash.startsWith("000000", 0)) {
				System.out.println("Part two:\n\tSecret: " + secret + "\n\tHash: " + myHash);
				break;
			}
			x++;
		}
	}

}
