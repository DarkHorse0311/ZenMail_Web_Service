package org.zen.zenmail.crypt;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zen.zenmail.model.user.User;
import org.zen.zenmail.repository.UserRepository;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Collections;


public class Hash {

    public static String hashWithRandomSalt(String password){
        return getSHA256password(password, getRandomHex8Byte());
    }

    public static String hashWitDBSalt(String usualPassword, String hashPassword){
        if (hashPassword == null || hashPassword == ""){
            return usualPassword;
        }
        else{

            String rest = hashPassword.substring(9, hashPassword.length());
            byte [] t = Base64.getDecoder().decode(rest);
            byte [] s = new byte[16];
            for(int i = 32; i < 48; ++i){
                s[i - 32] = t[i];
            }

            byte []restorePassword = new byte[usualPassword.getBytes().length + s.length];
            for(int i = 0; i < usualPassword.getBytes().length; ++i){
                restorePassword[i] = usualPassword.getBytes()[i];
            }

            for(int i = usualPassword.getBytes().length; i < restorePassword.length; ++i){
                restorePassword[i] = s[i - usualPassword.getBytes().length];
            }

            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] h = digest.digest(restorePassword);

                byte[] rr = new byte[h.length + s.length];
                for (int i = 0; i < h.length; ++i) {
                    rr[i] = h[i];
                }
                for (int i = h.length; i < rr.length; ++i) {
                    rr[i] = s[i - h.length];
                }
                return "{SSHA256}" + Base64.getEncoder().encodeToString(rr);
            }catch (NoSuchAlgorithmException e){

            }
        }
        return usualPassword;
    }

    public static String getSHA256password(String password, String sault){
        String out = password;
        try {
            String text = password;
            //String sault = getRandomHex8Byte();
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

    public static String getRandomHex8Byte(){
        SecureRandom random = new SecureRandom();
        byte []bytes = new byte[8];
        random.nextBytes(bytes);
        StringBuilder stringBuilder = new StringBuilder("");

        for(int i = 0; i < 8; ++i){
            stringBuilder.append(
                    String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(' ', '0'));
        }
        BigInteger bigInteger = new BigInteger(stringBuilder.toString(), 2);
        return bigInteger.toString(16);
    }

    public static String getSaltFromDB(String password){
        StringBuilder salt = new StringBuilder("");
        if(password != null){
            String hashPassword = password;
            for(int i = 52; i < hashPassword.length(); ++i){
                salt.append(hashPassword.charAt(i));
            }
        }
        return Base64.getDecoder().decode(salt.toString()).toString();
    }



}
