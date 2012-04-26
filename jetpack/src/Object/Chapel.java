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
public class Chapel extends WorldObject
{
    public Chapel() throws IOException
    {
        String filename = "./data/chapel/chapel.obj";
        model = new JWavefrontModel(new File(filename));
        model.unitize();
        model.facetNormals();
        model.vertexNormals(90);
    }
}
