/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

import Load.JWavefrontModel;
import Main.Engine;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author bruno
 */
public abstract class WorldObject
{

    protected JWavefrontModel model = null;
    protected Point3f position;
    protected float scale;

    public JWavefrontModel getModel()
    {
        return model;
    }

    public abstract void draw(GLAutoDrawable glad);
    
    public void load() throws Exception
    {
        
    }

    public void update()
    {
    }
}
