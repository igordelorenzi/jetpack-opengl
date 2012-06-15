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
 * @author arthur
 */
public class Bullet extends WorldObject
{
   
    public Bullet()
    {
        this.position = new Point3f(-40, 40, -100);
        this.scale = 100f;
    }
   
    @Override
    public void load() throws Exception
    {
        String filename = "./data/bullet/bala.obj";
        model = new JWavefrontModel(new File(filename));
        model.unitize();
        model.scale(scale);
        model.facetNormals();
        model.vertexNormals(90);
    }
   
    @Override
    public boolean update()
    {
        return true;
    }

    public void draw(GLAutoDrawable glad)
    {
        Engine.gl.glPushMatrix();
        Engine.gl.glTranslatef(position.x, position.y, position.z);
        model.draw(glad);
        Engine.gl.glPopMatrix();
    }
}

