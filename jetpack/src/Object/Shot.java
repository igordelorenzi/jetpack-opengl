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
 * @author brunoja
 */
public class Shot extends WorldObject
{

    private float forcex, forcey, forcez;

    public Shot(float x, float y, float z, float fx, float fy, float fz)
    {
        this.position = new Point3f(x, y, z);
        forcex = fx;
        forcey = fy;
        forcez = fz;
    }

    public void draw(GLAutoDrawable glad)
    {
        //Engine.gl.glDisable(GL.GL_LIGHTING);
        Engine.gl.glPushMatrix();

        Engine.gl.glTranslatef(position.x, position.y, position.z);

        Engine.glut.glutSolidCylinder(1, 2, 10, 10);

        Engine.gl.glPopMatrix();

        // Engine.gl.glEnable(GL.GL_LIGHTING);

    }

    @Override
    public void update()
    {
        this.position.x += forcex;
        this.position.y += forcey;
        this.position.z += forcez;
    }
}