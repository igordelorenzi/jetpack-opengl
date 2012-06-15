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
public class Gun extends WorldObject
{
   
    public Gun()
    {
        
    }
   
    @Override
    public void load() throws Exception
    {
        String filename = "./data/gun/steampunk_shotgun.obj";
        model = new JWavefrontModel(new File(filename));
        model.unitize();
        //model.scale(0.5f);
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
        Engine.gl.glTranslatef(Engine.cx, Engine.cy, Engine.cz);
        Engine.gl.glRotatef(180-(Engine.angle+90), 0, 1, 0);
        float yangle = Engine.yangle;
        if (yangle < -45) yangle = -45;
        if (yangle > 0) yangle+=yangle/2f;
        Engine.gl.glRotatef(yangle, 1, 0, 0);
        Engine.gl.glTranslatef(0, -0.5f, 1);
        Engine.gl.glRotatef(-90, 0, 1, 0);
        Engine.gl.glRotatef(90, 1, 0, 0);
        model.draw(glad);
        Engine.gl.glPopMatrix();
    }
}

