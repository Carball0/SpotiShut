/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spotishut;

import java.io.IOException;

/**
 *
 * @author Alejandro
 */
public class Main {
    public static void main(String[] args) {
        try {
        	SpotiShut sab = new SpotiShut();
        	while(true) {
        		sab.start();
        		System.gc();
        	}
        } catch (IOException ex) {
            System.out.println("IO Exception when starting: \n" + ex);
        } catch (Exception err) {
        	System.out.println("Error while starting: \n" + err);
		}
    }
}
