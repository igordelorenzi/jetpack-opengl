/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Input.MyKeyListener;
import Input.MyMouseMotionListener;
import Load.JWavefrontModel;
import Load.ModelViewer;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.JFrame;

/**
 *
 * @author arthur
 */
public class Main {

    static private GraphicsDevice device;

    public static void main(String args[]) throws IOException {
 
        device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        //creting the projection panel
        GLCapabilities glcaps = new GLCapabilities();
        GLCanvas canvas = new GLCanvas();
        
        glcaps.setAccumBlueBits(16);
        glcaps.setAccumGreenBits(16);
        glcaps.setAccumRedBits(16);
        glcaps.setDoubleBuffered(true);
        glcaps.setHardwareAccelerated(true);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(ModelViewer.W, ModelViewer.H);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setCursor(frame.getToolkit().createCustomCursor(
                new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
                "null"));

        
      
        if (device.isFullScreenSupported()) { // Go for full-screen mode
            frame.setIgnoreRepaint(true);     // Ignore OS re-paint request
            //device.setFullScreenWindow(frame);
        }

        
        
        String filename = "./data/chapel/chapel.obj";
        JWavefrontModel model = new JWavefrontModel(new File(filename));
        model.unitize();
        model.facetNormals();
        model.vertexNormals(90);
        model.dump(true);



        ModelViewer viewer = new ModelViewer(glcaps, model);
       // viewer.setOpaque(true);
        canvas.addGLEventListener(viewer);
        canvas.addMouseMotionListener(new MyMouseMotionListener());
        frame.addKeyListener(new MyKeyListener());
        frame.getContentPane().add(canvas);
        frame.setVisible(true);
        
     
        Animator animator = new FPSAnimator(canvas, 30);
        animator.start();
    }
}
