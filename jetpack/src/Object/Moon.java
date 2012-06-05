/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

import Load.JWavefrontModel;
import Main.Engine;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author bruno
 */
public class Moon extends WorldObject
{

    public Moon() throws IOException {
        
        this.position = new Point3f(0, 0, 0);
        this.scale = 25f;
        String filename = "./data/moon/Terrain.obj";//moon/moon.obj";
        model = new JWavefrontModel(new File(filename));
        model.unitize();
        //model.scale(scale);
        model.facetNormals();
        model.vertexNormals(90);
    }
    
    public void draw(GLAutoDrawable glad)
    {
        
        Engine.gl.glPushMatrix();
        
       // Engine.gl.glTranslatef(position.x, position.y, position.z);
        
        Engine.gl.glTranslatef(position.x, position.y, position.z);
        Engine.gl.glScalef(scale, scale, scale);
        Engine.gl.glTranslatef(-position.x, -position.y, -position.z);
        
        model.draw(glad);
        
        Engine.gl.glPopMatrix();
        
    }
}
