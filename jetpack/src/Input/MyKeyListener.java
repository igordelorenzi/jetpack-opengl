/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Input;

import Main.Engine;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author bruno
 */
public class MyKeyListener implements KeyListener
{

    static public boolean KEY_W = false;
    static public boolean KEY_A = false;
    static public boolean KEY_S = false;
    static public boolean KEY_D = false;
    static public boolean KEY_SPACE = false;

    public void keyTyped(KeyEvent ke)
    {
    }

    public void keyPressed(KeyEvent ke)
    {
        if (ke.getKeyCode() == KeyEvent.VK_W)
        {
            KEY_W = true;
        } else if (ke.getKeyCode() == KeyEvent.VK_S)
        {
            KEY_S = true;
        } else if (ke.getKeyCode() == KeyEvent.VK_A)
        {
            KEY_A = true;
        } else if (ke.getKeyCode() == KeyEvent.VK_D)
        {
            KEY_D = true;
        } else if (ke.getKeyCode() == KeyEvent.VK_SPACE)
        {
            KEY_SPACE = true;
        }
    }

    public void keyReleased(KeyEvent ke)
    {
        if (ke.getKeyCode() == KeyEvent.VK_W)
        {
            KEY_W = false;
        } else if (ke.getKeyCode() == KeyEvent.VK_S)
        {
            KEY_S = false;
        } else if (ke.getKeyCode() == KeyEvent.VK_A)
        {
            KEY_A = false;
        } else if (ke.getKeyCode() == KeyEvent.VK_D)
        {
            KEY_D = false;
        } else if (ke.getKeyCode() == KeyEvent.VK_SPACE)
        {
            KEY_SPACE = false;
        }
    }
}