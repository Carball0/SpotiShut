/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spotishut;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Alejandro Carballo
 */
public class SpotiShut {
    /**
     * Command used for getting Spotify.exe process details
     */
    private final String COMMAND = "tasklist /fi \"imagename eq"
            + " Spotify.exe\" /fo list /v";
    
    /**
     * Command used for getting Spotify.exe process details
     */
    private final String CHECK = "SoundVolumeView.exe /GetMute m1n231m2";
    
    /**
     * Muting command using nircmd
     */
    private final String MUTE = "SoundVolumeView.exe /Mute \"Spotify.exe\"";
    
    /**
     * Unmuting command using nircmd
     */
    private final String UNMUTE = "SoundVolumeView.exe /Unmute \"Spotify.exe\"";
    
    /**
     * String displayed in window name when playing ad
     */
    private final String WNWTEXT = "Spotify Free";

    /**
     * 
     * @throws Exception 
     * @throws java.io.IOException
     */
    public SpotiShut() throws Exception {
    	if(!checkDependencies()) {
    		throw new Exception("Dependencies not met: SoundVolumeView not present on the system\n"
    				+ "Download SoundVolumeView.exe from nirsoft webpage and place it on System32.");
		}
    	System.out.println("Welcome to SpotiShut!");
    }
    
    public void start() throws IOException {
        Process p;              // Get process info
        @SuppressWarnings("unused")
		Process mute, unmute;   // Mute or unmute
        BufferedReader read;    // Reads proccess info
        String out;             // Used for comparing
        boolean adDetect = false;
        
		try {
			p = Runtime.getRuntime().exec(COMMAND);
			p.waitFor();
		} catch (IOException | InterruptedException ex) {
			System.out.println("Error while obtaining processes: \n" + ex.getMessage());
			return;
		}
		read = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while ((out = read.readLine()) != null) {
			if (out.contains("de ventana:") && !out.contains("N/D")) {
				while (out != null && out.contains(WNWTEXT)) {
					if (!adDetect) { // Avoids repeating command while ads play
						try {
							System.out.println("Ads detected, muting...");
							mute = Runtime.getRuntime().exec(MUTE);
							mute.waitFor();
						} catch (InterruptedException ex) {
							System.out.println("Exception when muting: \n" + ex.getMessage());
						}
					}

					adDetect = true;
					try {
						p = Runtime.getRuntime().exec(COMMAND);
						p.waitFor();
						read = new BufferedReader(new InputStreamReader(p.getInputStream()));
						while ((out = read.readLine()) != null) {
							if (out.contains(WNWTEXT)) {
								Thread.sleep(500);
								break;
							}
						}
					} catch (IOException | InterruptedException ex) {
						System.out.println("Error while muted: \n" + ex.getMessage());
						unmute = Runtime.getRuntime().exec(UNMUTE);
						return;
					}
				}
				if (adDetect) {
					adDetect = false;
					System.out.println("Ads ended");
					unmute = Runtime.getRuntime().exec(UNMUTE);
				}
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			System.out.println("Thread sleep error: \n" + ex.getMessage());
		}
		read.close();
		p = mute = unmute = null;
		read = null;
		out = null;
    }
    
	private boolean checkDependencies() throws InterruptedException, IOException {
		Process check;
		try {
			check = Runtime.getRuntime().exec(CHECK);
		} catch (IOException e) {
			return false;
		}
		check.waitFor();
		BufferedReader read = new BufferedReader(new InputStreamReader(check.getInputStream()));
		if (read.readLine() == null) {
			read.close();
			return true;
		} else {
			read.close();
			return false;
		}
	}
}
