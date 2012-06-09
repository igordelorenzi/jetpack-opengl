package Main;

import Input.MyKeyListener;
import Input.MyMouseListener;
import Load.JWavefrontModel;
import Object.Shot;
import Object.WorldObject;
import com.sun.opengl.util.GLUT;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Engine implements GLEventListener
{
    public static final float Z_FAR = 300f;
    public static final float Z_FAR_BOX = 200f;
    public static final float Z_FAR_FOG = 220f;
    public static final int W = 800, H = 600;
    public static GL gl;
    public static GLU glu;
    public static GLUT glut;
    private LinkedList<WorldObject> objectsList = new LinkedList<WorldObject>();
    public static float lx = 0, ly = 0, lz = 1, cx = 0, cy = 15, cz = 0, angle = 90, yangle = 0;

    public Engine()
    {
    }

    public void addObject(WorldObject object)
    {
        objectsList.add(object);
    }

    @Override
    public void init(GLAutoDrawable glad)
    {
        // criando objetos
        gl = glad.getGL();
        glu = new GLU();
        glut = new GLUT();
        
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_FASTEST);
        
        // luz
        gl.glEnable(GL.GL_LIGHTING);
        gl.glEnable(GL.GL_LIGHT0);
        
        // z-buffer
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL.GL_CULL_FACE);
        gl.glCullFace(GL.GL_BACK);
        
        //anti-aliasing
        //gl.glShadeModel(GL.GL_SMOOTH);
        
        gl.glFogi(GL.GL_FOG_MODE, GL.GL_LINEAR); // modo linear
        gl.glFogfv(GL.GL_FOG_COLOR, new float[]{0.0f,0.0f,0.0f,1}, 0); 
        gl.glFogf(GL.GL_FOG_DENSITY, 0.05f);
        gl.glHint(GL.GL_FOG_HINT, GL.GL_DONT_CARE); 
        gl.glFogf(GL.GL_FOG_START, Z_FAR_FOG); // depths do fog
        gl.glFogf(GL.GL_FOG_END, Z_FAR); 
        gl.glEnable(GL.GL_FOG); // habilita o fog
        
        // projecao
        gl.glMatrixMode(GL.GL_PROJECTION); // carrega a matriz de projeção
        gl.glLoadIdentity(); // le a matriz identidade
        glu.gluPerspective(60, 1.0f, 0.1f, Z_FAR);

        // cria objetos
        Iterator<WorldObject> it = objectsList.iterator();
        while (it.hasNext())
        {
            WorldObject next = it.next();
            try
            {
                next.load();
            } catch (Exception ex)
            {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (next.getModel() != null)
            {
                next.getModel().compile(glad, JWavefrontModel.WF_MATERIAL | JWavefrontModel.WF_TEXTURE | JWavefrontModel.WF_SMOOTH);
            }
        }
        lighting(glad);
    }

    private void updateGame()
    {
        // cria o tiro
        if (MyMouseListener.CLICK)
        {
            MyMouseListener.CLICK = false;
            float fx = 8;
            float fy = 0;
            float fz = 0;
            addObject(new Shot(cx, cy, cz, fx, fy, fz));
        }
        // atualiza outros objetos
        Iterator<WorldObject> it = objectsList.iterator();
        while (it.hasNext())
        {
            it.next().update();
        }
    }

    @Override
    public void display(GLAutoDrawable glad)
    {
        updateGame();

        gl = glad.getGL();
        glu = new GLU();

        gl.glClearColor(0.0f,0.0f,0.0f,1.0f); // cor do fog
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity(); //carrega matrix identidade
        //System.out.println("angle: " + angle + " cx: " + cx + " cy: " + cy + 
        // " cz: " + cz + " lx: " + lx + " ly: " + ly + " lz: " + lz);
        glu.gluLookAt(cx, cy, cz,
                cx + lx, cy + ly, cz + lz,
                0, 1, 0);

        Iterator<WorldObject> it = objectsList.iterator();

        while (it.hasNext())
        {
            it.next().draw(glad);
        }

        gl.glFlush(); //execute all commands
    }

    @Override
    public void reshape(GLAutoDrawable glad, int x, int y, int w, int h)
    {
    }

    @Override
    public void displayChanged(GLAutoDrawable glad, boolean bln, boolean bln1)
    {
    }

    private void lighting(GLAutoDrawable glad)
    {
        gl = glad.getGL();

        float[] luzAmbiente =
        {
            1f, 1f, 1f, 1.0f
        };
        float[] luzDifusa = new float[]
        {
            1f, 1f, 1f, 1.0f
        };
        float[] luzEspecular = new float[]
        {
            0.7f, 0.7f, 0.7f, 1.0f
        };
        // float[] posicaoLuz = new float[]{5 * 5.0f, 5 * 5.0f, 5 * 5.0f, 1.0f};
        float[] posicaoLuz = new float[]
        {
            0f, Engine.Z_FAR_BOX/2, Engine.Z_FAR_BOX/2, 0f
        };

        // Define os parametros da luz de numero 0
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, luzAmbiente, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, luzDifusa, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, luzEspecular, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, posicaoLuz, 0);
    }
}