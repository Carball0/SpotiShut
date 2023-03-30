/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spotishut;

import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 *
 * @author Alejandro Carballo
 */
public class Main {

    public static void main(String[] args) {
    	JFrame frame = new JFrame("SpotiShut!");
    	JTextField title = new JTextField("Welcome to SpotiShut!");
    	JTextField textField = new JTextField("Enjoy your music!");
    	boolean closed = false;

    	frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
    	frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(title);
        frame.add(textField);
        frame.setSize(240, 130);
        frame.setVisible(true);
        try {
        	SpotiShut sab = new SpotiShut(textField);
        	while(true) {
        		if(frame.isVisible() && !closed) {
        			closed = sab.start();
        		} else {
        			sab.onExit();
        			return;
        		}
        		if(closed) {
        			sab.onExit();
        			textField.setText("Spotify closed, exiting...");
        			Thread.sleep(2000);
        			frame.dispose();
        			return;
        		}
        	}
        } catch (IOException ex) {
        	textField.setText("IO Err: " + ex);
			return;
        } catch (Exception err) {
        	textField.setText("Err: " + err);
			return;
		}
    }
}
