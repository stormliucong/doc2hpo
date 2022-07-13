package edu.columbia.dbmi.doc2hpo.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;


//import org.springframework.stereotype.Component;

//@Component("runBashCommand")
public class RunBashCommand {

    /**
     * Constructor
     * @return 
     */
	
	public static void main(String[] args) {
		String currentDir = System.getProperty("user.dir");
		File metamapDir = new File("/Users/congliu/public_mm/bin");
		File inputFile = new File(currentDir + "/src/main/resources/examples/mm_note.txt");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String strLine = null;
		try {
			strLine = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String Input = "";
		while(strLine!=null) {
			Input += strLine;
			try {
				strLine = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String command = metamapDir+"/metamap16" + " -I -p -J -K -8 --conj cgab,genf,lbpr,lbtr,patf,dsyn,fndg";
		System.out.println(command);
		RunBashCommand rbc = new RunBashCommand();
		String resultByName = rbc.runCommand(command,Input);
		System.out.println(resultByName); 
	}
    public RunBashCommand() {

    }

    public String runCommand(String command, String input) {
        String result = null;
        Runtime _runtime = Runtime.getRuntime();
        try {
            // System.out.println("Testcommand is " + command);
            Process proc = _runtime.exec(command);
            // feed input stream
            OutputStream procInput = proc.getOutputStream();
            procInput.write(input.getBytes());
            // give it two newlines so the metamap process will crunch on the
            // input
            String end = "\n\n";
            procInput.write(end.getBytes());
            procInput.flush();

            // catch error stream
            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");

            // catch output stream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT", outputStream);

            // kick them off
            errorGobbler.start();
            outputGobbler.start();

            // close the input stream to the server, causing any remaining
            // output to be flushed
            procInput.close();

            // any error???
            int exitVal = proc.waitFor();
            if (exitVal != 0) {
                System.out.println("METAMAP11 ABNORMAL EXITVALUE " + exitVal);
            }

            errorGobbler.join();
            outputGobbler.join();

            result = outputStream.toString();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;

    }
    
  public String runCommand2(String[] command, String input) {
  String result = null;
  Runtime _runtime = Runtime.getRuntime();
  try {
      // System.out.println("Testcommand is " + command);
      Process proc = _runtime.exec(command);
      // feed input stream
      OutputStream procInput = proc.getOutputStream();
      procInput.write(input.getBytes());
      // give it two newlines so the metamap process will crunch on the
      // input
      String end = "\n\n";
      procInput.write(end.getBytes());
      procInput.flush();

      // catch error stream
      StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");

      // catch output stream
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT", outputStream);

      // kick them off
      errorGobbler.start();
      outputGobbler.start();

      // close the input stream to the server, causing any remaining
      // output to be flushed
      procInput.close();

      // any error???
      int exitVal = proc.waitFor();
      if (exitVal != 0) {
          System.out.println("METAMAP11 ABNORMAL EXITVALUE " + exitVal);
      }

      errorGobbler.join();
      outputGobbler.join();

      result = outputStream.toString();
  } catch (Throwable t) {
      t.printStackTrace();
  }
  return result;

}


}
