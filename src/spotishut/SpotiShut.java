/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spotishut;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JTextField;

/**
 * 
 * @author Alejandro Carballo
 */
public class SpotiShut {
    /**
     * Command used for getting Spotify's window title
     */
    private final String COMMAND = "tasklist /fi \"imagename eq"
            + " Spotify.exe\" /fi \"windowtitle eq Spotify Free\""
            + " /fo list";
    
    /**
     * Command used for getting Spotify's state
     */
    private final String STATE = "tasklist /fi \"imagename eq"
            + " Spotify.exe\"";
    
    /**
     * Command used for checking dependencies
     */
    private final String CHECK = "SoundVolumeView.exe /GetMute m1n231m2";
    
    /**
     * Muting command using nirsoft
     */
    private final String MUTE = "SoundVolumeView.exe /Mute \"Spotify.exe\"";
    
    /**
     * Unmuting command using nirsoft
     */
    private final String UNMUTE = "SoundVolumeView.exe /Unmute \"Spotify.exe\"";
    
    private JTextField textUI;
    
    @SuppressWarnings("unused")
	private Process p, state;
    
    private BufferedReader pRead;

    /**
     * 
     * @throws Exception 
     * @throws java.io.IOException
     */
    public SpotiShut(JTextField a) throws Exception {
    	if(!checkDependencies()) {
    		throw new Exception("Dependencies not met");
		}
    	this.textUI = a;
    }
    
    public boolean start() throws IOException {
        @SuppressWarnings("unused")
		Process mute, unmute;   // Mute or unmute
        String out;             // Used for comparing
        
		try {
			p = Runtime.getRuntime().exec(COMMAND);
			p.waitFor();
		} catch (InterruptedException ex) {
			textUI.setText("Error detected, trying to recover...");
			p = null;
			return false;
		}
		
		pRead = new BufferedReader(new InputStreamReader(p.getInputStream()));
		
		if(pRead.readLine() != null && (out = pRead.readLine()).contains("Spotify.exe")) {	//Ad or No Playback
			try {
				textUI.setText("No playback/Ads detected, now muted");
				mute = Runtime.getRuntime().exec(MUTE);
				mute.waitFor();
			} catch (InterruptedException ex) {
				textUI.setText("Error detected, trying to recover...");
				mute = null;
				return false;
			}
			try {
				while(out.contains("Spotify.exe")) {
					p = Runtime.getRuntime().exec(COMMAND);
					p.waitFor();
					pRead = new BufferedReader(new InputStreamReader(p.getInputStream()));
					if((pRead.readLine()) != null && (out = pRead.readLine()).contains("Spotify.exe")) {
						Thread.sleep(500);
					}
				}
			} catch (IOException | InterruptedException ex) {
				textUI.setText("Error detected, trying to recover...");
				unmute = Runtime.getRuntime().exec(UNMUTE);
				return false;
			}
			
			textUI.setText("Enjoy your music!");
			unmute = Runtime.getRuntime().exec(UNMUTE);

			pRead.close();
			mute = unmute = null;
			out = null;
			return false;
		} else {
			try {
				state = Runtime.getRuntime().exec(STATE);
				pRead = new BufferedReader(new InputStreamReader(state.getInputStream()));
				for(int i = 0; i < 4; i++) pRead.readLine();
				if(pRead.readLine() == null) {	//Spotify closed
					return true;
				} else {						//Spotify running
					pRead.close();
					mute = unmute = null;
					out = null;
					Thread.sleep(1000);
					return false;
				}
			} catch (IOException | InterruptedException ex) {
				textUI.setText("Error detected, trying to recover...");
				return false;
			}
		}
    }
    
    public void onExit() {
    	@SuppressWarnings("unused")
		Process unmute;
    	try {
			unmute = Runtime.getRuntime().exec(UNMUTE);
		} catch (IOException e) {}
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