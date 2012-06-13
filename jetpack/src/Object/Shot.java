/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

import Input.MyMouseListener;
import Load.JWavefrontModel;
import Main.Engine;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author brunoja
 */
public class Shot extends WorldObject
{
    private LinkedList<Vector> shots = new LinkedList<Vector>();
    private Texture texture = null;

    public Shot()
    {
        
    }
    
    @Override
    public void load() throws IOException
    {
        if (texture == null)
        {
            texture = TextureIO.newTexture(new File("./data/shot/shot.png"), true);
            texture.setTexParameterf(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
            texture.setTexParameterf(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        }
    }

    public void draw(GLAutoDrawable glad)
    {
        Engine.gl.glPushAttrib(GL.GL_ENABLE_BIT);
        Engine.gl.glDisable(GL.GL_LIGHTING);
        Engine.gl.glDisable(GL.GL_CULL_FACE);
        Engine.gl.glEnable(GL.GL_TEXTURE_2D);
        Engine.gl.glEnable(GL.GL_BLEND);
        Engine.gl.glBlendFunc(GL.GL_ONE, GL.GL_ONE);
        
        Iterator<Vector> it = shots.iterator();
        while (it.hasNext())
        {
            Vector pos = it.next();
            Engine.gl.glPushMatrix();
            Engine.gl.glTranslatef(pos.posa.x, pos.posa.y, pos.posa.z);
            texture.enable();
            texture.bind();
            Engine.gl.glColor4f(1, 0, 0, 1);
            //Engine.gl.glPushMatrix();
            Engine.gl.glRotatef(180-Engine.angle+90, 0, 1, 0);
            Engine.gl.glRotatef(180+(-(Engine.yangle)-28), 1, 0, 0);
            Engine.gl.glBegin(GL.GL_QUADS);
            Engine.gl.glTexCoord2f(0, 1);
            Engine.gl.glVertex3f(-1, -1, 0);
            Engine.gl.glTexCoord2f(0, 0);
            Engine.gl.glVertex3f(-1, 1, 0);
            Engine.gl.glTexCoord2f(1, 0);
            Engine.gl.glVertex3f(1, 1, 0);
            Engine.gl.glTexCoord2f(1, 1);
            Engine.gl.glVertex3f(1, -1, 0);
            Engine.gl.glEnd();
            Engine.gl.glPopMatrix();
        }
        Engine.gl.glPopAttrib();
        Engine.gl.glColor4f(1,1,1,1);
    }

    @Override
    public boolean update()
    {
        // cria o tiro
        if (MyMouseListener.CLICK)
        {
            MyMouseListener.CLICK = false;
            float fx = Engine.lx;
            float fy = Engine.ly;
            float fz = Engine.lz;
            float mod = (float) Math.sqrt(fx*fx+fy*fy+fz*fz);
            fx /= mod;
            fy /= mod;
            fz /= mod;
            fx *= 10;
            fy *= 10;
            fz *= 10;
            Vector pos = new Vector(new Point3f(Engine.cx, Engine.cy, Engine.cz), new Point3f(fx, fy, fz));
            shots.add(pos);
            
        }
        // atualiza tiros
        Iterator<Vector> it = shots.iterator();
        while (it.hasNext())
        {
            Vector pos = it.next();
            pos.posa.x += pos.posb.x;
            pos.posa.y += pos.posb.y;
            pos.posa.z += pos.posb.z;
            if (Math.abs(pos.posa.x - Engine.cx) > Engine.Z_FAR) it.remove();
            else if (Math.abs(pos.posa.y - Engine.cy) > Engine.Z_FAR) it.remove();
            else if (Math.abs(pos.posa.z - Engine.cz) > Engine.Z_FAR) it.remove();
        }
        return true;
    }
}

class Vector
{
    Vector(Point3f a, Point3f b)
    {
        posa = a;
        posb = b;
    }
    Point3f posa;
    Point3f posb;
}