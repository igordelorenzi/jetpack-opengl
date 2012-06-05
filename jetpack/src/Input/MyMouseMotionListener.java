/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Input;

import Main.Engine;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bruno
 */
public class MyMouseMotionListener implements MouseMotionListener
{
    private Robot robot;
    public static final float CAMERA_STEP = 0.1f;
    private int screenXDiv2;
    private int screenYDiv2;
    
    public MyMouseMotionListener()
    {
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
        Toolkit t = Toolkit.getDefaultToolkit();
        int screenX = t.getScreenSize().width;
        int screenY = t.getScreenSize().height;
        screenXDiv2=screenX>>1;
        screenYDiv2=screenY>>1;
    }
    
    public void mouseDragged(MouseEvent me) 
    {
        
    }

    public void mouseMoved(MouseEvent me) 
    {
        int x = me.getXOnScreen();
        int y = me.getYOnScreen();
        float dx = x-screenXDiv2;
        float dy = y-screenYDiv2;
        robot.mouseMove(screenXDiv2, screenYDiv2);
        Engine.angle += CAMERA_STEP*dx;
        Engine.yangle += CAMERA_STEP*dy;
        if (Engine.angle >= 360) 
            Engine.angle -= 360;
        if (Engine.angle < 0)
            Engine.angle += 360;
        if (Engine.yangle > 95)
            Engine.yangle = 95;
        if (Engine.yangle < -95)
            Engine.yangle = -95;
        Engine.ly = (float) -Math.sin(Engine.yangle*Math.PI/180.0);
        Engine.lx = (float) ((1-Math.abs(Engine.ly))*Math.cos(Engine.angle*Math.PI/180.0));
        Engine.lz = (float) ((1-Math.abs(Engine.ly))*Math.sin(Engine.angle*Math.PI/180.0));
    }
    
}