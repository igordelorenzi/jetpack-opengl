/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

import Main.Engine;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author bruno
 */
public class SkyBox extends WorldObject
{

    private Texture texture, sun;

    public SkyBox()
    {
    }
    
    @Override
    public void update()
    {

    }

    @Override
    public void load() throws Exception
    {
        texture = TextureIO.newTexture(new File("./data/skybox/sky.png"), true);
        texture.setTexParameterf(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        texture.setTexParameterf(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        sun = TextureIO.newTexture(new File("./data/skybox/sun.png"), true);
        sun.setTexParameterf(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        sun.setTexParameterf(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
    }

    @Override
    public void draw(GLAutoDrawable glad)
    {
        // desabilitar as coisas primeiro, luz, depth test etc..
        Engine.gl.glPushMatrix();
        Engine.gl.glPushAttrib(GL.GL_ENABLE_BIT);
        Engine.gl.glEnable(GL.GL_TEXTURE_2D);
        Engine.gl.glDisable(GL.GL_DEPTH_TEST);
        Engine.gl.glDisable(GL.GL_LIGHTING);
        Engine.gl.glDisable(GL.GL_BLEND);
        Engine.gl.glDisable(GL.GL_CULL_FACE);
        Engine.gl.glDisable(GL.GL_FOG);

        Engine.gl.glTranslatef(Engine.cx, Engine.cy, Engine.cz);
        
        // topo
        texture.enable();
        texture.bind();
        Engine.gl.glBegin(GL.GL_QUADS);
        Engine.gl.glTexCoord2f(0, 1);
        Engine.gl.glVertex3f(-Engine.Z_FAR_BOX, Engine.Z_FAR_BOX, -Engine.Z_FAR_BOX);
        Engine.gl.glTexCoord2f(0, 0);
        Engine.gl.glVertex3f(-Engine.Z_FAR_BOX, Engine.Z_FAR_BOX, Engine.Z_FAR_BOX);
        Engine.gl.glTexCoord2f(1, 0);
        Engine.gl.glVertex3f(Engine.Z_FAR_BOX, Engine.Z_FAR_BOX, Engine.Z_FAR_BOX);
        Engine.gl.glTexCoord2f(1, 1);
        Engine.gl.glVertex3f(Engine.Z_FAR_BOX, Engine.Z_FAR_BOX, -Engine.Z_FAR_BOX);
        Engine.gl.glEnd();
        
        // frente
        sun.enable();
        sun.bind();
        Engine.gl.glBegin(GL.GL_QUADS);
        Engine.gl.glTexCoord2f(0, 1);
        Engine.gl.glVertex3f(-Engine.Z_FAR_BOX, Engine.Z_FAR_BOX, Engine.Z_FAR_BOX);
        Engine.gl.glTexCoord2f(0, 0);
        Engine.gl.glVertex3f(-Engine.Z_FAR_BOX, 0, Engine.Z_FAR_BOX);
        Engine.gl.glTexCoord2f(1, 0);
        Engine.gl.glVertex3f(Engine.Z_FAR_BOX, 0, Engine.Z_FAR_BOX);
        Engine.gl.glTexCoord2f(1, 1);
        Engine.gl.glVertex3f(Engine.Z_FAR_BOX, Engine.Z_FAR_BOX, Engine.Z_FAR_BOX);
        Engine.gl.glEnd();

        // restora as matrizes e attr
        Engine.gl.glPopAttrib();
        Engine.gl.glPopMatrix();
    }
    
}
