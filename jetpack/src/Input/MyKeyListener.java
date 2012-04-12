/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Input;

import Load.ModelViewer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author bruno
 */
public class MyKeyListener implements KeyListener
{
    private static float WALK_STEP = 1;
    
    public void keyTyped(KeyEvent ke) 
    {

    }

    public void keyPressed(KeyEvent ke) 
    {
        if (ke.getKeyCode() == KeyEvent.VK_W) 
        {
            ModelViewer.cz += Math.sin(ModelViewer.angle*Math.PI/180.0) * WALK_STEP;
            ModelViewer.cx += Math.cos(ModelViewer.angle*Math.PI/180.0) * WALK_STEP;
        }
        else if (ke.getKeyCode() == KeyEvent.VK_S) 
        {
            ModelViewer.cz -= Math.sin(ModelViewer.angle*Math.PI/180.0) * WALK_STEP;
            ModelViewer.cx -= Math.cos(ModelViewer.angle*Math.PI/180.0) * WALK_STEP;
        }
    }

    public void keyReleased(KeyEvent ke) 
    {

    }

}
