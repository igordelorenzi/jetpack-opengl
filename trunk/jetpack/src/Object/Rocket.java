/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

import Load.JWavefrontModel;
import Main.Engine;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author arthur
 */
public class Rocket extends WorldObject
{
    private ArrayList<Point3f> smoke = new ArrayList<Point3f>();
    private ArrayList<Float> diams = new ArrayList<Float>();
    
    public Rocket()
    {
        this.position = new Point3f(30f, 30f, 0f);
        this.scale = 30f;
        for (int x = -10; x <= 10; x++)
        {
            for (int z = -10; z <= 10; z++)
            {
                smoke.add(new Point3f(x-30, -58, z-2));
                diams.add(Float.valueOf((float) Math.random()*2));
            }
        }
    }
    
    @Override
    public void load() throws Exception
    {
        String filename = "./data/rocket/rocket.obj";
        model = new JWavefrontModel(new File(filename));
        model.unitize();
        model.scale(scale);
        model.facetNormals();
        model.vertexNormals(90);
    }
    
    @Override
    public boolean update()
    {
        for (int i = 0; i < diams.size(); ++i)
        {
            Float size = diams.get(i);
            size += 0.1f;
            if (size >= 5f) size = 0.2f;
            diams.set(i, size);
        }
        return true;
    }

    public void draw(GLAutoDrawable glad)
    {
        Engine.gl.glPushMatrix();
        Engine.gl.glTranslatef(position.x, position.y, position.z);
        model.draw(glad);
        
        Engine.gl.glPushAttrib(GL.GL_ENABLE_BIT);
        Engine.gl.glDisable(GL.GL_LIGHTING);
        Engine.gl.glEnable(GL.GL_BLEND);
        Engine.gl.glEnable(GL.GL_TEXTURE_2D);
        Engine.gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

        Iterator<Point3f> ita = smoke.iterator();
        Iterator<Float> itb = diams.iterator();
        
        while (ita.hasNext())
        {
            Point3f point = ita.next();
            Float size = itb.next();
            Engine.gl.glTranslatef(position.x+point.x, position.y+point.y, position.z+point.z);
            Engine.gl.glColor4f(1f, 1f, 1f, 1-size/3);
            Engine.glut.glutSolidSphere(size, 10, 4);
            Engine.gl.glTranslatef(-(position.x+point.x), -(position.y+point.y), -(position.z+point.z));
        }
        
        Engine.gl.glColor4f(1,1,1,1);
        Engine.gl.glPopAttrib();
        Engine.gl.glPopMatrix();
        
    }
}
