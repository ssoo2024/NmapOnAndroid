package com.example.mynmap;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import static java.lang.System.out;

public class ShellExecuter{
    public ShellExecuter() { }

    public String Executer(String command) {
        final StringBuffer output = new StringBuffer();
        ProcessBuilder ps = new ProcessBuilder("su");
        ps.redirectErrorStream(true);

        try {
            Process p = ps.start();
            DataOutputStream os = new DataOutputStream(p.getOutputStream());
            os.writeBytes(command + "\n");
            p.getOutputStream().close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
                out.println(line + "\n");
            } reader.close();

            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = output.toString();
        return response;
    }
}

