/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author brunoja
 */
public class MyMouseListener implements MouseListener
{
    static public boolean CLICK = false;
    
    public void mouseClicked(MouseEvent me) {
        
    }

    public void mousePressed(MouseEvent me) {
        CLICK = true;
    }

    public void mouseReleased(MouseEvent me) {
        
    }

    public void mouseEntered(MouseEvent me) {
        
    }

    public void mouseExited(MouseEvent me) {
        
    }
    
}