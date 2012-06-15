/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

import Load.JWavefrontModel;
import Main.Engine;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLException;

/**
 *
 * @author bruno
 */
public class Flag extends WorldObject
{

    private Texture texture;
    private float points[][][] = new float[45][45][3];
    private float time = 0;

    public Flag()
    {
        
    }

    @Override
    public boolean update()
    {
        time += 0.1f;
        for (int x = 0; x < 45; x++)
        {
            float mult = Math.min(0.5f, (float) Math.pow(1f/Math.sqrt(45-x), 1.3));
            for (int y = 0; y < 45; y++)
            {
                float rx = ((x / 5.0f) - 4.5f);
                float ry = ((y / 5.0f) - 4.5f);
                points[x][y][0] = rx;
                points[x][y][1] = ry;
                points[x][y][2] = (float) Math.sin(time+rx+ry/2)*mult;
            }
        }
        return true;
    }

    @Override
    public void load() throws Exception
    {
        texture = TextureIO.newTexture(new File("./data/flag/Flag.png"), true);
        texture.setTexParameterf(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        texture.setTexParameterf(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        
        update();
        
        String filename = "./data/flag/mastro.obj";
        model = new JWavefrontModel(new File(filename));
        model.unitize();
        model.scale(5);
        model.facetNormals();
        model.vertexNormals(90);
    }

    public void draw(GLAutoDrawable glad)
    {
        Engine.gl.glPushMatrix();
        Engine.gl.glTranslatef(-30, 12.7f, 0);
        Engine.gl.glDisable(GL.GL_CULL_FACE);
        Engine.gl.glEnable(GL.GL_TEXTURE_2D);
        Engine.gl.glRotatef(45, 0, 1, 0);
        Engine.gl.glScalef(0.5f, 0.5f, 0.5f);

        texture.enable();
        texture.bind();
        Engine.gl.glBegin(GL.GL_QUADS);
        for (int x = 0; x < 44; x++)    
        {
            for (int y = 0; y < 44; y++) 
            {
                float float_x = (x) / 44.0f;
                float float_y = (y) / 44.0f;  
                float float_xb = (x + 1) / 44.0f; 
                float float_yb = (y + 1) / 44.0f; 
                Engine.gl.glNormal3f(0, 0, 1);
                
                Engine.gl.glTexCoord2f(float_x, float_y);
                Engine.gl.glVertex3f(points[x][y][0], points[x][y][1], points[x][y][2]);

                Engine.gl.glTexCoord2f(float_x, float_yb); 
                Engine.gl.glVertex3f(points[x][y + 1][0], points[x][y + 1][1], points[x][y + 1][2]);

                Engine.gl.glTexCoord2f(float_xb, float_yb);
                Engine.gl.glVertex3f(points[x + 1][y + 1][0], points[x + 1][y + 1][1], points[x + 1][y + 1][2]);

                Engine.gl.glTexCoord2f(float_xb, float_y);
                Engine.gl.glVertex3f(points[x + 1][y][0], points[x + 1][y][1], points[x + 1][y][2]);
            }
        }
        Engine.gl.glEnd();

        Engine.gl.glDisable(GL.GL_TEXTURE_2D);
        Engine.gl.glEnable(GL.GL_CULL_FACE);
        
        Engine.gl.glPopMatrix();
        
        Engine.gl.glPushMatrix();
        Engine.gl.glScalef(1, 3, 1);
        Engine.gl.glTranslatef(-31.6f, 0, 1.6f);
        model.draw(glad);
        Engine.gl.glPopMatrix();
    }
}
