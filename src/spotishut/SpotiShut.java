/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spotishut;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipInputStream;

import javax.swing.JTextField;

/**
 *
 * @author Alejandro Carballo
 */
public class SpotiShut {
	
	private final String APPDATADIR = System.getenv("APPDATA") + File.separator;

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
     * Muting command using nirsoft
     */
    private final String MUTE = APPDATADIR + "SoundVolumeView.exe /Mute \"Spotify.exe\"";

    /**
     * Unmuting command using nirsoft
     */
    private final String UNMUTE = APPDATADIR + "SoundVolumeView.exe /Unmute \"Spotify.exe\"";

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
    	this.textUI = a;
    	
    	if(extractDependencies()) {
    		a.setText("Dependencies OK!");
    	} else {
    		a.setText("Dependencies extracted in %appdata%");
    	}
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
    
    private boolean extractDependencies() throws Exception {
		File depend = new File(APPDATADIR + File.separator + "SoundVolumeView.exe");
		if (depend.exists())
			return true;

		FileInputStream isFile;
		byte[] buffer = new byte[4096];
		try {		// Extract dependencies into %appdata%
			isFile = new FileInputStream("SoundVolumeView.zip");
			ZipInputStream isZip = new ZipInputStream(isFile);
			isZip.getNextEntry();
			File exec = new File(APPDATADIR + File.separator + "SoundVolumeView.exe");
			FileOutputStream osFile = new FileOutputStream(exec);
			int len;
			while ((len = isZip.read(buffer)) > 0) {
				osFile.write(buffer, 0, len);
				System.out.println("AAA");
			}
			osFile.close();
			isZip.closeEntry();
			isZip.close();
			isFile.close();
			return false;
		} catch (IOException e) {
			throw new Exception("Error when extracting dependencies");
		}
	}
}