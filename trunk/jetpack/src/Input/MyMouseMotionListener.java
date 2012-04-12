/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Input;

import Load.ModelViewer;
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
            Logger.getLogger(ModelViewer.class.getName()).log(Level.SEVERE, null, ex);
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
        ModelViewer.angle += CAMERA_STEP*dx;
        ModelViewer.yangle += CAMERA_STEP*dy;
        if (ModelViewer.angle >= 360) 
            ModelViewer.angle -= 360;
        if (ModelViewer.angle < 0)
            ModelViewer.angle += 360;
        if (ModelViewer.yangle > 95)
            ModelViewer.yangle = 95;
        if (ModelViewer.yangle < -95)
            ModelViewer.yangle = -95;
        ModelViewer.ly = (float) -Math.sin(ModelViewer.yangle*Math.PI/180.0);
        ModelViewer.lx = (float) ((1-Math.abs(ModelViewer.ly))*Math.cos(ModelViewer.angle*Math.PI/180.0));
        ModelViewer.lz = (float) ((1-Math.abs(ModelViewer.ly))*Math.sin(ModelViewer.angle*Math.PI/180.0));
    }
    
}
