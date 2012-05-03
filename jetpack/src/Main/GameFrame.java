/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import javax.swing.JFrame;
import Input.MyKeyListener;
import Input.MyMouseMotionListener;
import Load.JWavefrontModel;
import Object.Moon;
import Object.Rocket;
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
 * @author bruno
 */
public class GameFrame extends JFrame
{
    private GraphicsDevice device;
    private Engine viewer;
    private GLCanvas canvas;
    
    public GameFrame()
    {
        
    }
    
    public void start()
    {
        setVisible(true);
        Animator animator = new FPSAnimator(canvas, 30);
        animator.start();
    }
    
    public void load() throws IOException
    {
        device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        //creting the projection panel
        GLCapabilities glcaps = new GLCapabilities();
        
        glcaps.setAccumBlueBits(16);
        glcaps.setAccumGreenBits(16);
        glcaps.setAccumRedBits(16);
        glcaps.setDoubleBuffered(true);
        glcaps.setHardwareAccelerated(true);
        canvas = new GLCanvas(glcaps);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Engine.W, Engine.H);
        setLocationRelativeTo(null);
        //frame.setUndecorated(true);
        setResizable(false);
        setCursor(getToolkit().createCustomCursor(
                new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
                "null"));
        if (device.isFullScreenSupported()) { // Go for full-screen mode
            setIgnoreRepaint(true);     // Ignore OS re-paint request
            //device.setFullScreenWindow(frame);
        }
        
        viewer = new Engine();
        canvas.addGLEventListener(viewer);
        canvas.addMouseMotionListener(new MyMouseMotionListener());
        addKeyListener(new MyKeyListener());
        getContentPane().add(canvas);
        
        loadObjects();
    }
    
    private void loadObjects() throws IOException
    {
        //model.dump(true);
        viewer.addObject(new Rocket());
        viewer.addObject(new Moon());
    }
}
