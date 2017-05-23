/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrityfit.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Simple timer class
 *
 * @author veckardt
 */
public class Timer {

    /**
     * converts time (in milliseconds) to human-readable format "<dd:>hh:mm:ss"
     */
    long startTime;

    public Timer() {
        startTime = System.currentTimeMillis();
    }

    public String getStartTime() {
        return millisToShortDHMS(startTime);
    }

    public String getTime() {
        return millisToShortDHMS(System.currentTimeMillis());
    }

    private String getDiff() {
        return millisToShortDHMS(System.currentTimeMillis() - startTime);
    }

    public String millisToShortDHMS(long duration) {

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
        return (dateFormat.format(duration));

    }
}
