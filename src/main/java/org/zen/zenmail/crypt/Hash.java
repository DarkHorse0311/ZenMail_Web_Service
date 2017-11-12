package org.zen.zenmail.crypt;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Hash {
    public static String getSHA256password(String password){
        SecureRandom random = new SecureRandom();
        byte []bytes = new byte[8];
        random.nextBytes(bytes);
        StringBuilder stringBuilder = new StringBuilder("");
        String out = password;
        for(int i = 0; i < 8; ++i){
            stringBuilder.append(
                    String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(' ', '0'));
        }
        BigInteger bigInteger = new BigInteger(stringBuilder.toString(), 2);
        try {
            String text = password;
            String sault = bigInteger.toString(16);

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest((text + sault).getBytes(StandardCharsets.UTF_8));
            byte []test = new byte[hash.length + sault.getBytes().length];

            for(int i = 0; i < hash.length; ++i){
                test[i] = hash[i];
            }
            for(int i = hash.length; i < test.length; ++i){
                test[i] = sault.getBytes()[i - hash.length];
            }

            out = "{SSHA256}" + Base64.getEncoder().encodeToString(test);

        }catch (NoSuchAlgorithmException e){

        }
        return out;
    }
}
