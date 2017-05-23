/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit.utils;

import integrityfit.IntegrityFitNesse;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple file handler
 *
 * @author veckardt
 */
public class FileHandler {

    String filename;

    /**
     * Constructor
     *
     * @param filename the file name
     */
    public FileHandler(String filename) {
        this.filename = filename;
    }

    /**
     * Checks if file exists
     *
     * @param filename The filename to check
     * @return True if exists, otherwise false
     */
    public Boolean fileExists(String filename) {
        File file = new File(filename);
        return file.exists();
    }

    /**
     * Writes the constructed testcase to a file
     *
     * @param content The content to be written
     * @return True if writing was successfully, otherwise false
     */
    public Boolean writeToFile(String content) {
        // ;
        try {
            File file = new File(filename);
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(content);
            output.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the line number in the file
     *
     * @return line number
     */
    public int getLineCount() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(this.filename));
            int lines = 0;
            while (reader.readLine() != null) {
                lines++;
            }
            reader.close();
            return lines;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IntegrityFitNesse.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IntegrityFitNesse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Checks if the Text is part of the file
     *
     * @param text the text to search for
     * @return true or false
     */
    public boolean contains(String text) {
        BufferedReader reader;
        Boolean found = false;
        String currentLine;
        try {
            reader = new BufferedReader(new FileReader(this.filename));
            int lines = 0;
            while ((currentLine = reader.readLine()) != null) {
                lines++;
                if (currentLine.indexOf(text) > 0) {
                    found = true;
                }
            }
            reader.close();
            return found;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IntegrityFitNesse.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IntegrityFitNesse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
