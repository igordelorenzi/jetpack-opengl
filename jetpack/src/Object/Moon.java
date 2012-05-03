/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Object;

import Load.JWavefrontModel;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author bruno
 */
public class Moon extends WorldObject
{
    public Moon() throws IOException
    {
        String filename = "./data/moon/moon.obj";
        model = new JWavefrontModel(new File(filename));
        model.scale(1000);
        model.unitize();
        model.facetNormals();
        model.vertexNormals(90);
    }
}
