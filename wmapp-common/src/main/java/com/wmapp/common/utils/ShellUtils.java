package com.wmapp.common.utils;


import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * @author wmapp
 */
public class ShellUtils {

    public static int ExecCommand(String command) {
        int retCode = 0;
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh","-c",command},null,null);
            retCode = process.waitFor();
            ExecOutput(process);
        } catch (Exception e) {
            e.printStackTrace();
            retCode = -1;
        }
        return retCode;
    }

    public static boolean ExecOutput(Process process) throws Exception {
        if (process == null) {
            return false;
        } else {
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
            String output = "";
            while ((line = input.readLine()) != null) {
                output += line + "\n";
            }
            input.close();
            ir.close();
            if (output.length() > 0) {
                System.out.println(output);
            }
        }
        return true;
    }

    public static int deleteFile(String path) {
        String command = "echo \"Delete logs Application for Fil\"\n" +
                "        logPath=\"/home/wmapp/\"\n" +
                "        if [  -x \"$logPath\" ];\n" +
                "        then\n" +
                "        rm -rf \"$logPath\"\n" +
                "        fi\n" +
                "        rm -rf "+path+
                "        echo \"Delete success...\"";
       return ExecCommand(command);
    }
}
