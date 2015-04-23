package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Cipher {

    public static final String SHA = "SHA";

    public static String hashString(String string, String encoding) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(encoding);
        md.update(string.getBytes());
        return Arrays.toString(md.digest());
    }

}
