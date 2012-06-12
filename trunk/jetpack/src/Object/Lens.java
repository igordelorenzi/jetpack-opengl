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
public class Lens extends WorldObject
{
    float posx = 0, posy = Engine.Z_FAR_BOX/2, posz = Engine.Z_FAR_BOX;
    private Texture lens[] = new Texture[5];
            
    public Lens()
    {
        
    }
    
    @Override
    public void load() throws Exception
    {
        for (int i = 0; i < 5; ++i)
        {
            lens[i] = TextureIO.newTexture(new File("./data/lens/flare"+i+".png"), true);
            lens[i].setTexParameterf(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
            lens[i].setTexParameterf(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        }
    }
    
    private void drawFlare(float zplane, float r, float g, float b, float alpha, int txt, float s)
    {
        r *= alpha*2;
        g *= alpha*2;
        b *= alpha*2;
        lens[txt].enable();
        lens[txt].bind();
        Engine.gl.glColor4f(r, g, b, alpha);
        Engine.gl.glPushMatrix();
        Engine.gl.glTranslatef(0, 0, zplane);
        Engine.gl.glRotatef(180-Engine.angle+90, 0, 1, 0);
        Engine.gl.glRotatef(180+(-(Engine.yangle)-28), 1, 0, 0);
        Engine.gl.glBegin(GL.GL_QUADS);
        Engine.gl.glTexCoord2f(0, 1);
        Engine.gl.glVertex3f(-s, -s, 0);
        Engine.gl.glTexCoord2f(0, 0);
        Engine.gl.glVertex3f(-s, s, 0);
        Engine.gl.glTexCoord2f(1, 0);
        Engine.gl.glVertex3f(s, s, 0);
        Engine.gl.glTexCoord2f(1, 1);
        Engine.gl.glVertex3f(s, -s, 0);
        Engine.gl.glEnd();
        Engine.gl.glPopMatrix();
    }

    public void draw(GLAutoDrawable glad)
    {
        // checa se ta vendo o lens flare
        if (Engine.angle < 55 || Engine.angle > 125) return;
        if (Engine.yangle > 10 || Engine.yangle < -35) return;
        
        // salvando matrizes e habilitando blending
        Engine.gl.glPushAttrib(GL.GL_ENABLE_BIT);
        Engine.gl.glPushMatrix();
        Engine.gl.glDisable(GL.GL_LIGHTING);
        Engine.gl.glDisable(GL.GL_DEPTH_TEST);
        Engine.gl.glEnable(GL.GL_BLEND);
        Engine.gl.glEnable(GL.GL_TEXTURE_2D);
        Engine.gl.glBlendFunc(GL.GL_ONE, GL.GL_ONE);
        float alpha = 0.5f;
        if (Engine.angle < 70) alpha *= (Engine.angle-55)/15f;
        else if (Engine.angle > 110) alpha *= (125-Engine.angle)/15f;
        if (Engine.yangle < -20) alpha *= (Engine.yangle-(-35))/15f;
        else if (Engine.yangle > -5) alpha *= (10-Engine.yangle)/15f;
        System.out.println(alpha + " " + Engine.angle + " " + Engine.yangle);
        //alpha = 0.5f;
        Engine.gl.glColor4f(1, 1, 1, alpha);
        
        // ajustando vetor de flare
        Engine.gl.glTranslatef(Engine.cx+0, 40+Engine.cy, Engine.cz+100);
        // 24 eh o sin(40/100)
        Engine.gl.glRotatef((-Engine.yangle)/2-28, 1, 0, 0);
        Engine.gl.glRotatef((Engine.angle-90)/4, 0, 1, 0);
        
        // desenhando poligonos do flare
        float zplane = (float) (-100 - 0*Math.abs(Math.cos((Engine.angle)*Math.PI/180)));
        drawFlare(zplane, 0.5f, 0, 0, alpha, 3, 5);
        drawFlare(zplane, 0, 0, 1f, alpha, 1, 4.5f);
        zplane = -80f;
        drawFlare(zplane, 0.5f, 0, 1f, alpha, 1, 4.8f);
        zplane = -90f;
        drawFlare(zplane, 0, 0.5f, 0, alpha, 4, 2f);
        zplane = -60f;
        drawFlare(zplane, 0, 0.5f, 1f, alpha, 1, 2f);
        zplane = -55f;
        drawFlare(zplane, 0, 0.5f, 1f, alpha, 1, 2f);
        
        // restaurando contexto
        Engine.gl.glDisable(GL.GL_BLEND);
        Engine.gl.glPopMatrix();
        Engine.gl.glPopAttrib();
        
        Engine.gl.glColor4f(1, 1, 1, 1);
    }
}
