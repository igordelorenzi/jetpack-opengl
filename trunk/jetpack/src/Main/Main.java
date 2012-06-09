/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.IOException;

/**
 *
 * @author arthur
 */
public class Main
{

    public static void main(String args[]) throws IOException
    {
        GameFrame frame = new GameFrame();
        frame.load();
        frame.start();
    }
}
