/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spotishut;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Alejandro Carballo
 */
public class Main {
    public static void main(String[] args) {
    	JFrame frame = new JFrame("SpotiShut!");
    	JTextArea textArea = new JTextArea();
    	JScrollPane scrollPane;
    	
    	textArea.setCaretPosition(textArea.getDocument().getLength());
    	frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
        frame.setSize(240, 150);
        scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane);
        frame.setVisible(true);
        try {
        	SpotiShut sab = new SpotiShut(textArea);
        	while(true) {
        		if(frame.isVisible()) {
        			sab.start();
            		System.gc();
        		} else {
        			sab.onExit();
        			return;
        		}
        		if(sab.isClosed()) {
        			sab.onExit();
        			textArea.setText("Spotify closed, exiting...");
        			Thread.sleep(2000);
        			frame.dispose();
        			return;
        		}
        	}
        } catch (IOException ex) {
        	textArea.setText("IO Exception when starting: \n" + ex);
			return;
        } catch (Exception err) {
        	textArea.setText("Error while starting: \n" + err);
			return;
		}
    }
}
