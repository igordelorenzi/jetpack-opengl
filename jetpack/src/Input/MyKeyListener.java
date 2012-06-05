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
    private static float WALK_STEP = 0.05f;
    
    public void keyTyped(KeyEvent ke) 
    {

    }

    public void keyPressed(KeyEvent ke) 
    {
        if (ke.getKeyCode() == KeyEvent.VK_W) 
        {
            Engine.cz += Math.sin(Engine.angle*Math.PI/180.0) * WALK_STEP;
            Engine.cx += Math.cos(Engine.angle*Math.PI/180.0) * WALK_STEP;
        }
        else if (ke.getKeyCode() == KeyEvent.VK_S) 
        {
            Engine.cz -= Math.sin(Engine.angle*Math.PI/180.0) * WALK_STEP;
            Engine.cx -= Math.cos(Engine.angle*Math.PI/180.0) * WALK_STEP;
        }
    }

    public void keyReleased(KeyEvent ke) 
    {

    }

}
