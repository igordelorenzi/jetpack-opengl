package Main;

import Input.MyKeyListener;
import Input.MyMouseListener;
import Load.JWavefrontModel;
import Object.Shot;
import Object.WorldObject;
import com.sun.opengl.util.GLUT;
import java.util.Iterator;
import java.util.LinkedList;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Engine implements GLEventListener {

    public static final float WALK_STEP = 0.02f;
    public static final float FORCE_STEP = 0.02f;
    public static final float GRAVITY = FORCE_STEP / 6f;
    public static final int W = 800, H = 600;
    public static GL gl;
    public static GLU glu;
    public static GLUT glut;
    private LinkedList<WorldObject> objectsList = new LinkedList<WorldObject>();
    public static float lx = 0, ly = 0, lz = 1, cx = 0, cy = 5, cz = 0, angle = 90, yangle = 0;
    public float forcex = 0, forcey = 0, forcez = 0;

    public Engine() {
    }

    public void addObject(WorldObject object) {
        objectsList.add(object);
    }

    @Override
    public void init(GLAutoDrawable glad) {
        //OpenGl Parameters
        gl = glad.getGL();
        glu = new GLU();
        glut = new GLUT();
        gl.glEnable(GL.GL_LIGHTING);
        gl.glEnable(GL.GL_LIGHT0);
        //z-buffer
        gl.glEnable(GL.GL_DEPTH_TEST);

        gl.glEnable(GL.GL_CULL_FACE);
        gl.glCullFace(GL.GL_BACK);
        //anti-aliasing
        gl.glShadeModel(GL.GL_SMOOTH);
        gl.glMatrixMode(GL.GL_PROJECTION); // carrega a matriz de projeção
        gl.glLoadIdentity(); // le a matriz identidade
        //gl.glViewport(-1, -1, 2, 2);
        glu.gluPerspective(60, 1.0f, 0.1f, 200);

        Iterator<WorldObject> it = objectsList.iterator();
        while (it.hasNext()) {
            it.next().getModel().compile(glad, JWavefrontModel.WF_MATERIAL | JWavefrontModel.WF_TEXTURE | JWavefrontModel.WF_SMOOTH);
        }
        lighting(glad);
    }

    private void updateGame() {
        // cria o tiro
        if (MyMouseListener.CLICK) {
            MyMouseListener.CLICK = false;
            float fx = 8;
            float fy = 0;
            float fz = 0;
            addObject(new Shot(cx, cy, cz, fx, fy, fz));
        }
        // atualiza as forcas
        if (MyKeyListener.KEY_W) {
            forcex += Math.cos(angle * Math.PI / 180.0) * WALK_STEP;
            forcez += Math.sin(angle * Math.PI / 180.0) * WALK_STEP;
        }
        if (MyKeyListener.KEY_S) {
            forcex -= Math.cos(angle * Math.PI / 180.0) * WALK_STEP;
            forcez -= Math.sin(angle * Math.PI / 180.0) * WALK_STEP;
        }
        if (MyKeyListener.KEY_A) {
            forcex += Math.sin(angle * Math.PI / 180.0) * WALK_STEP;
            forcez -= Math.cos(angle * Math.PI / 180.0) * WALK_STEP;
        }
        if (MyKeyListener.KEY_D) {
            forcex -= Math.sin(angle * Math.PI / 180.0) * WALK_STEP;
            forcez += Math.cos(angle * Math.PI / 180.0) * WALK_STEP;
        }
        if (MyKeyListener.KEY_SPACE) {
            forcey += FORCE_STEP;
        }
        // atualiza as posicoes
        cx += forcex;
        cy += forcey;
        cz += forcez;
        forcey -= GRAVITY;
        // limita o maximo/minimo das forcas
        forcex = Math.min(WALK_STEP * 10, forcex);
        forcex = Math.max(-WALK_STEP * 10, forcex);
        forcey = Math.min(FORCE_STEP * 10, forcey);
        forcey = Math.max(-FORCE_STEP * 10, forcey);
        forcez = Math.min(WALK_STEP * 10, forcez);
        forcez = Math.max(-WALK_STEP * 10, forcez);
        // atualiza outros objetos
        Iterator<WorldObject> it = objectsList.iterator();
        while (it.hasNext()) {
            it.next().update();
        }
    }

    @Override
    public void display(GLAutoDrawable glad) {
        updateGame();

        gl = glad.getGL();
        glu = new GLU();

        //gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f); //backgroung color
        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity(); //carrega matrix identidade
        //System.out.println("angle: " + angle + " cx: " + cx + " cy: " + cy + 
        // " cz: " + cz + " lx: " + lx + " ly: " + ly + " lz: " + lz);
        glu.gluLookAt(cx, cy, cz,
                cx + lx, cy + ly, cz + lz,
                0.0f, 1.0f, 0.0f);

        Iterator<WorldObject> it = objectsList.iterator();

        while (it.hasNext()) {
            it.next().draw(glad);
        }

        gl.glFlush(); //execute all commands
    }

    @Override
    public void reshape(GLAutoDrawable glad, int x, int y, int w, int h) {
    }

    @Override
    public void displayChanged(GLAutoDrawable glad, boolean bln, boolean bln1) {
    }

    private void lighting(GLAutoDrawable glad) {
        gl = glad.getGL();

        float[] luzAmbiente = {0.3f, 0.3f, 0.3f, 1.0f};
        float[] luzDifusa = new float[]{0.75f, 0.75f, 0.75f, 1.0f};
        float[] luzEspecular = new float[]{0.7f, 0.7f, 0.7f, 1.0f};
        // float[] posicaoLuz = new float[]{5 * 5.0f, 5 * 5.0f, 5 * 5.0f, 1.0f};
        float[] posicaoLuz = new float[]{0f, 1f, 1f, 0f};

        // Define os parametros da luz de numero 0
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, luzAmbiente, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, luzDifusa, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, luzEspecular, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, posicaoLuz, 0);
    }
}