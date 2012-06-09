/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

import Load.JWavefrontModel;
import Main.Engine;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author arthur
 */
public class Invader extends WorldObject
{

    public Invader(float i)
    {
        this.position = new Point3f(-i, i, i);
        this.scale = 0.5f;
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

    public void draw(GLAutoDrawable glad)
    {
        //Engine.gl.glDisable(GL.GL_LIGHTING);
        Engine.gl.glPushMatrix();

        Engine.gl.glTranslatef(position.x, position.y, position.z);

        model.draw(glad);

        Engine.gl.glPopMatrix();

        // Engine.gl.glEnable(GL.GL_LIGHTING);

    }
}
