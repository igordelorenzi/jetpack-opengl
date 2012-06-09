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
public class Moon extends WorldObject
{

    public static final int TILES = 7;
    private float dims[];
    private Texture texture;

    public Moon()
    {
        this.position = new Point3f(0, 0, 0);
        dims = new float[]{60,1,60};
    }
    
    @Override
    public void load() throws Exception
    {
        texture = TextureIO.newTexture(new File("./data/moon/TerrainTexture.jpg"), true);
        texture.setTexParameterf(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        texture.setTexParameterf(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
    }

    public void draw(GLAutoDrawable glad)
    {
        Engine.gl.glEnable(GL.GL_TEXTURE_2D);
        int si = (int) (Engine.cx / dims[0]);
        int sj = (int) (Engine.cz / dims[2]);
        for (int i = -TILES + si; i <= TILES + si; ++i)
        {
            for (int j = -TILES + sj; j <= TILES + sj; ++j)
            {
                Engine.gl.glPushMatrix();
                float x = i * dims[0];
                float z = j * dims[2];
                Engine.gl.glTranslatef(position.x + x, position.y, position.z + z);
                texture.enable();
                texture.bind();
                Engine.gl.glBegin(GL.GL_POLYGON);
                Engine.gl.glNormal3f(0, 1, 0);
                Engine.gl.glTexCoord2f(0, 0);
                Engine.gl.glVertex3f(-dims[0]/2, 0, -dims[2]/2);
                Engine.gl.glTexCoord2f(1, 0);
                Engine.gl.glVertex3f(-dims[0]/2, 0, dims[2]/2);
                Engine.gl.glTexCoord2f(1, 1);
                Engine.gl.glVertex3f(dims[0]/2, 0, dims[2]/2);
                Engine.gl.glTexCoord2f(0, 1);
                Engine.gl.glVertex3f(dims[0]/2, 0, -dims[2]/2);
                //texture.disable();
                Engine.gl.glEnd();
                Engine.gl.glPopMatrix();
            }
        }
        Engine.gl.glDisable(GL.GL_TEXTURE_2D);
    }
}
