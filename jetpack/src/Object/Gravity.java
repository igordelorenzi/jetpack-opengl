/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

import Input.MyKeyListener;
import Main.Engine;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author bruno
 */
public class Gravity extends WorldObject
{

    public static final float WALK_STEP = 0.02f;
    public static final float FORCE_STEP = 0.02f;
    public static final float GRAVITY = FORCE_STEP / 6f;
    private float forcex = 0, forcey = 0, forcez = 0;

    public void draw(GLAutoDrawable glad)
    {
    }

    @Override
    public boolean update()
    {
        // atualiza as forcas
        if (MyKeyListener.KEY_W)
        {
            forcex += Math.cos(Engine.angle * Math.PI / 180.0) * WALK_STEP;
            forcez += Math.sin(Engine.angle * Math.PI / 180.0) * WALK_STEP;
        }
        if (MyKeyListener.KEY_S)
        {
            forcex -= Math.cos(Engine.angle * Math.PI / 180.0) * WALK_STEP;
            forcez -= Math.sin(Engine.angle * Math.PI / 180.0) * WALK_STEP;
        }
        if (MyKeyListener.KEY_A)
        {
            forcex += Math.sin(Engine.angle * Math.PI / 180.0) * WALK_STEP;
            forcez -= Math.cos(Engine.angle * Math.PI / 180.0) * WALK_STEP;
        }
        if (MyKeyListener.KEY_D)
        {
            forcex -= Math.sin(Engine.angle * Math.PI / 180.0) * WALK_STEP;
            forcez += Math.cos(Engine.angle * Math.PI / 180.0) * WALK_STEP;
        }
        if (MyKeyListener.KEY_SPACE)
        {
            forcey += FORCE_STEP;
        }
        // normaliza as forcas
        float norm = (float) Math.sqrt(forcex*forcex+forcez*forcez);
        if (norm < 2/3f) norm = 2/3f;
        float addx = forcex /= norm*1.5;
        float addz = forcez /= norm*1.5;
        // atualiza as posicoes
        Engine.cx += addx;
        Engine.cy += forcey;
        Engine.cz += addz;
        forcey -= GRAVITY;
        forcex -= forcex/100;
        forcez -= forcez/100;
        // limita o maximo/minimo da gravidade
        forcey = Math.min(FORCE_STEP * 10, forcey);
        forcey = Math.max(-FORCE_STEP * 10, forcey);
        // checa colisao com a lua
        if (Engine.cy < 4 && forcey < 0)
        {
            forcey *= -0.5;
            if (forcey < 0.01)
                forcey = 0;
        }
        return true;
    }
}
