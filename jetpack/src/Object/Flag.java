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

    public Flag()
    {
        
    }

    @Override
    public boolean update()
    {
        for (int y = 0; y < 45; y++) 
        {
            float hold = points[0][y][2]; 
            for (int x = 0; x < 44; x++)
            {
                points[x][y][2] = points[x + 1][y][2];
            }
            points[44][y][2] = hold;
        }
        return true;
    }

    @Override
    public void load() throws Exception
    {
        texture = TextureIO.newTexture(new File("./data/flag/Flag.png"), true);
        texture.setTexParameterf(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        texture.setTexParameterf(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);

        for (int x = 0; x < 45; x++)
        {
            for (int y = 0; y < 45; y++)
            {
                points[x][y][0] = ((x / 5.0f) - 4.5f);
                points[x][y][1] = ((y / 5.0f) - 4.5f);
                points[x][y][2] = (float) (Math.sin((((x / 5.0f) * 40.0f) / 360.0f) * 3.141592654 * 2.0f));
            }
        }
    }

    public void draw(GLAutoDrawable glad)
    {
        Engine.gl.glPushMatrix();
        Engine.gl.glDisable(GL.GL_CULL_FACE);
        Engine.gl.glEnable(GL.GL_TEXTURE_2D);
        Engine.gl.glTranslatef(-30, 5, 0);
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
    }
}
