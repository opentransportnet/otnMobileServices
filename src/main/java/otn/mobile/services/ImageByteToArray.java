/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otn.mobile.services;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author EMantziou
 */
public class ImageByteToArray {

    public static void main(String args[]) throws FileNotFoundException, IOException {

        InputStream inputstream = new FileInputStream("c:\\images\\im1.png");
        byte[] bytes = IOUtils.toByteArray(inputstream);
        System.out.println("bytes " + bytes);

        InputStream input = new ByteArrayInputStream(bytes);
        OutputStream output = new FileOutputStream("c:\\images\\im1-copied.png");
        IOUtils.copy(input, output);
        
        System.out.println("copied");

    }

}
