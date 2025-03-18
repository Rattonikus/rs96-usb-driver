package com.rattonikus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;

public class ProcessRunner {

    ProcessBuilder pb; 

    public ProcessRunner()
    {
        try 
        {
            Process pb = new ProcessBuilder("wpctl", "get-volume", "@DEFAULT_AUDIO_SINK@").start();
            String pbOutput = new String(pb.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            
            System.out.println("Output: " + pbOutput);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }

    }
    

}
