/* ***** BEGIN LICENSE BLOCK *****
 *
 * Copyright (c) 2005-2010 Universidade de Sao Paulo, Sao Carlos/SP, Brazil.
 * All Rights Reserved.
 *
 * This file is part of Java Wavefront (JWavefront) reader project.
 *
 * JWavefront is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * JWavefront is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * This code was developed by Fernando V. Paulovich <fpaulovich@gmail.com>,
 * member of Visualization, Imaging and Computer Graphics Group
 * (http://www.lcad.icmc.usp.br) at Instituto de Ciencias Matematicas e de
 * Computacao - ICMC - (http://www.icmc.usp.br) of Universidade de Sao Paulo,
 * Sao Carlos/SP, Brazil.
 *
 * You should have received a copy of the GNU General Public License along
 * with JWavefront. If not, see <http://www.gnu.org/licenses/>.
 *
 * ***** END LICENSE BLOCK ***** */

package Load;

import com.sun.opengl.util.GLUT;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;


/**
 * JWavefront object viewer.
 *
 * @author Fernando V. Paulovich
 */
public class ModelViewer implements GLEventListener{
    public static int W = 800, H = 600;
    public static float lx = 0, ly = 0, lz = 1, cx = 0, cy = 0, cz = 0, angle = 90, yangle = 0;
    
    public ModelViewer(GLCapabilities glcaps, JWavefrontModel model) {

        this.model = model;
    }

    @Override
    public void init(GLAutoDrawable glad) {
        //OpenGl Parameters
        GL gl = glad.getGL();
        GLU glu = new GLU();
        gl.glEnable(GL.GL_LIGHTING);
        gl.glEnable(GL.GL_LIGHT0);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glShadeModel(GL.GL_SMOOTH);
        gl.glMatrixMode(GL.GL_PROJECTION); // carrega a matriz de projeção
        gl.glLoadIdentity(); // le a matriz identidade
        gl.glViewport(-200, -200, 400, 400);
        glu.gluPerspective(60, 1.0f, 0.1f, 200); 

        //compiling the object
        model.compile(glad, JWavefrontModel.WF_MATERIAL | JWavefrontModel.WF_TEXTURE | JWavefrontModel.WF_SMOOTH);

        lighting(glad);
    }

    @Override
    public void display(GLAutoDrawable glad) {
        GL gl = glad.getGL();
        GLU glu = new GLU();
        
        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f); //backgroung color
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity(); //carrega matrix identidade
        System.out.println("angle: " + angle + " cx: " + cx + " cy: " + cy + 
                           " cz: " + cz + " lx: " + lx + " ly: " + ly + " lz: " + lz);
        glu.gluLookAt(	cx, cy, cz,
			cx+lx, cy+ly, cz+lz,
			0.0f, 1.0f,  0.0f);
        
        //draw the object
        model.draw(glad);

        gl.glFlush(); //execute all commands
    }

    @Override
    public void reshape(GLAutoDrawable glad, int x, int y, int w, int h) {
    }

    @Override
    public void displayChanged(GLAutoDrawable glad, boolean bln, boolean bln1) {
    }

    private void lighting(GLAutoDrawable glad) {
        GL gl = glad.getGL();

        float[] luzAmbiente = {0.3f, 0.3f, 0.3f, 1.0f};
        float[] luzDifusa = new float[]{0.75f, 0.75f, 0.75f, 1.0f};
        float[] luzEspecular = new float[]{0.7f, 0.7f, 0.7f, 1.0f};
        float[] posicaoLuz = new float[]{5 * 5.0f, 5 * 5.0f, 5 * 5.0f, 1.0f};

        // Define os parametros da luz de numero 0
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, luzAmbiente, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, luzDifusa, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, luzEspecular, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, posicaoLuz, 0);
    }



    private JWavefrontModel model;
}
