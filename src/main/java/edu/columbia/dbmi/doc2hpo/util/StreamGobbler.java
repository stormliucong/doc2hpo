package edu.columbia.dbmi.doc2hpo.util;

///** code copied from
// *  http://www.javaworld.com/javaworld/jw-12-2000/jw-1229-traps.html
// *  When Runtime.exec() won't: Navigate yourself around pitfalls related to the Runtime.exec() method
// *  @author Michael Daconta
// *  ********mw.utils
// */


import java.io.*;


public class StreamGobbler extends Thread {

    InputStream is;
    String type;
    OutputStream os;
    String result = "";
    
    public StreamGobbler(InputStream is, String type) {
        this(is, type, null);
    }

    public StreamGobbler(InputStream is, String type, OutputStream redirect) {
        this.is = is;
        this.type = type;
        this.os = redirect;
    }

    /** creates readers to handle the text created by the external program
     */
    public void run() {
        try {
            PrintWriter printWriter = null;
            if (os != null) {
                printWriter = new PrintWriter(os);
            }

            InputStreamReader inputStreamReader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (printWriter != null) {
                    printWriter.println(line);
                }
                System.out.println(type + "> " + line);
            }
            if (printWriter != null) {
                printWriter.flush();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
    }
}


