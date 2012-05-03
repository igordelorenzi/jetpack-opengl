/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

import Load.JWavefrontModel;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author bruno
 */
public abstract class WorldObject
{
    protected JWavefrontModel model;
    
    public JWavefrontModel getModel()
    {
        return model;
    }
            
    public void draw(GLAutoDrawable glad)
    {
        
        model.draw(glad);
    }
    
    public void update()
    {
        
    }
}
