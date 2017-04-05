import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;

/**
 * Created by Andrew on 3/28/2017.
 */
public class Commands {

    private static void encrypt(String Password){
        int[] key= {1951, 42, 5647,75,7187};
        int index = 0;
        char outputChar;
        char[] password = Password.toCharArray();
        for(int i = 0; i< Password.length(); i++){
            if (index == 3){
                password[i] = (char)(password[i] + key[index]);
                index = 0;
            }
            password[i] = (char)(password[i] + key[index]);
            index++;
        }
       try{
           BufferedWriter bw = new BufferedWriter(new FileWriter(new File("admin.dat")));

       }
    }
    private static String decrypt(String Password){

    }

}
