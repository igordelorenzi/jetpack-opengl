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
public class Invader extends WorldObject
{
    float angle, curra = 0;
    Point3f orig;
    
    public Invader()
    {
        this.position = new Point3f(40, 5, (float)Math.random()*20);
        this.scale = 1f;
        angle = (float) Math.random();
    }
    
    @Override
    public void load() throws Exception
    {
        String filename = "./data/invader/invader.obj";
        model = new JWavefrontModel(new File(filename));
        model.unitize();
        model.scale(scale);
        model.facetNormals();
        model.vertexNormals(90);
    }
    
    @Override
    public boolean update()
    {
        angle += 10f;
        curra += 0.1f;
        position.x = (float) (30 + Math.cos(curra)*10);
        position.z = (float) (0 + Math.sin(curra)*10);
        return true;
    }

    public void draw(GLAutoDrawable glad)
    {
        Engine.gl.glPushMatrix();
        Engine.gl.glDisable(GL.GL_CULL_FACE);

        Engine.gl.glTranslatef(position.x, position.y, position.z);
        Engine.gl.glRotatef(angle, 0, 1, 0);
        model.draw(glad);

        Engine.gl.glEnable(GL.GL_CULL_FACE);
        Engine.gl.glPopMatrix();
    }
}
