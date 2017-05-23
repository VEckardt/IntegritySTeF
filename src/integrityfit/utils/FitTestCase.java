/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit.utils;

/**
 * Class to manage the FitNesse test case
 *
 * @author veckardt
 */
public class FitTestCase {

    private String testCase;
    private String fileName;
    private Boolean overwriteFlag;

    /**
     * Constructor with filename as parameter
     *
     * @param fileName the file name where to store the testcase to
     */
    public FitTestCase(String fileName) {
        this.testCase = "";
        this.fileName = fileName;
    }

    /**
     * Adds an additional text to the current stored testcase
     *
     * @param testCase
     */
    public void addToTestCase(String testCase) {
        this.testCase = this.testCase.concat(testCase);
    }

    /**
     * Replaces a part 'what' within the testcase with 'with'
     *
     * @param what to look for
     * @param with to replace with
     */
    public void replaceInTestCase(String what, String with) {
        this.testCase = this.testCase.replace(what, with);
    }

    public String getTestCase() {
        return this.testCase;
    }

    public Boolean testCaseFileSave(String filename, Boolean overwriteFlag) {
        FileHandler fh = new FileHandler(this.fileName);
        if (fh.fileExists(filename)) {
            return overwriteFlag;
        }
        return true;
    }

    public Boolean writeTestCase() {
        FileHandler fh = new FileHandler(this.fileName);
        return fh.writeToFile(this.testCase);
    }

    public Boolean writeTestCase(String fileName) {
        // System.out.println("overwriteFlag "+overwriteFlag);
        this.fileName = fileName;
        return writeTestCase();

    }
}