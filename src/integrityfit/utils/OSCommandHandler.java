/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author veckardt
 */
public class OSCommandHandler {

    private String[] outputFilter;
    private String filteredResult;
    private String unfilteredResult;

    /**
     * Constructor
     *
     * @param outputFilter
     */
    public OSCommandHandler(String[] outputFilter) {
        this.outputFilter = outputFilter;
        this.filteredResult = "";
        this.unfilteredResult = "";
    }

    public String getFilteredResult() {
        return filteredResult;
    }

    public String getUnfilteredResult() {
        return unfilteredResult;
    }

    public int executeCmd(String command, Boolean wait, Map<String, String> env) {
        int exitCode = 0;
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", command);

            if (env!= null && env.size() > 0) {
                pb.environment().putAll(env);
            }

            pb.redirectErrorStream();
            Process p = pb.start();
            // any error message?
            StreamConsumer ise = new StreamConsumer(p.getErrorStream(), "ERROR");

            StreamConsumer isc = new StreamConsumer(p.getInputStream(), "OUTPUT");
            isc.start();
            ise.start();

            if (wait) {
                exitCode = p.waitFor();
            } else {
                exitCode = p.exitValue();
            }

            isc.join();
            ise.join();
            System.out.println(" OS Process terminated with " + exitCode);
            return exitCode;
        } catch (IOException ex) {
            Logger.getLogger(OSCommandHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(OSCommandHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    /**
     *
     * @param command
     * @return
     */
    public int executeCmd(String command, Boolean wait) {

        int exitCode = 0;
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", command);
            pb.redirectErrorStream();
            Process p = pb.start();
            // any error message?
            StreamConsumer ise = new StreamConsumer(p.getErrorStream(), "ERROR");

            StreamConsumer isc = new StreamConsumer(p.getInputStream(), "OUTPUT");
            isc.start();
            ise.start();

            if (wait) {
                exitCode = p.waitFor();
            } else {
                exitCode = p.exitValue();
            }

            isc.join();
            ise.join();
            System.out.println(" OS Process terminated with " + exitCode);
            return exitCode;
        } catch (IOException ex) {
            Logger.getLogger(OSCommandHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(OSCommandHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;

    }

    /**
     * Inner Class to handle Output and Error
     */
    public class StreamConsumer extends Thread {

        private InputStream is;
        private String type;

        public StreamConsumer(InputStream is, String type) {
            this.is = is;
            this.type = type;
        }

        @Override
        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    if (!line.isEmpty()) {
                        System.out.println(type + "> " + line);
                        for (int i = 0; i < outputFilter.length; i++) {
                            if (line.contains(outputFilter[i])) {
                                filteredResult = filteredResult.concat(ltrim(line)) + "\n";
                            }
                        }
                        unfilteredResult = unfilteredResult.concat(ltrim(line)) + "\n";
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(OSCommandHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * Trims the left part of the string
     *
     * @param text
     * @return
     */
    private final static Pattern LTRIM = Pattern.compile("^\\s+");

    public static String ltrim(String text) {
        return LTRIM.matcher(text).replaceAll("");
    }
}
