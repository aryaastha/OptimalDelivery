package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by astha.a on 15/02/18.
 */
public class FileUtils {
    public static String readFromFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
        String temp;

        StringBuilder builder = new StringBuilder();
        while((temp=reader.readLine())!=null){
            builder.append(temp);
        }

        return builder.toString();
    }

    public static void main(String[] args) {
        FileUtils fileUtils = new FileUtils();
        try {
            System.out.println(fileUtils.readFromFile("/Users/astha.a/work/TestCode/src/main/resources/attributes2.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
