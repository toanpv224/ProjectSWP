/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Admin
 */
public class FileIO {
    public static void writeFile(String content, String filePath) throws IOException{
        File file = new File(filePath);
        if(!file.exists()){
            file.createNewFile();
        }
        
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
        fw.close();
    }
}
