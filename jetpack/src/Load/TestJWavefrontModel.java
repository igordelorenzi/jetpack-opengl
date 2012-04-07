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

import Main.Main;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.media.opengl.GLCapabilities;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * Test multiple JWavefronts objects.
 *
 * @author Fernando V. Paulovich
 */
public class TestJWavefrontModel {

    public static ArrayList<File> listFiles(File dir) {
        File[] list_files = dir.listFiles();
        ArrayList<File> files = new ArrayList<File>();

        for (File file : list_files) {
            if (file.isDirectory()) {
                ArrayList<File> files_aux = listFiles(file);
                files.addAll(files_aux);
            } else {
                if (file.getName().endsWith(".obj")) {
                    files.add(file);
                }
            }
        }

        return files;
    }

    public static void main(String[] args) throws IOException {
        File dir = new File("data");
        ArrayList<File> files = listFiles(dir);

        for (File file : files) {
            System.out.println(file.getName());

            JDialog frame = new JDialog();
            frame.setModal(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 800);

            JWavefrontModel model = new JWavefrontModel(file);
            model.unitize();
            model.facetNormals();
            model.vertexNormals(90);
            model.dump(false);
            System.out.println("###");

            //creting the projection panel
            GLCapabilities glcaps = new GLCapabilities();
            glcaps.setAccumBlueBits(16);
            glcaps.setAccumGreenBits(16);
            glcaps.setAccumRedBits(16);
            glcaps.setDoubleBuffered(true);
            glcaps.setHardwareAccelerated(true);

            Main viewer = new Main(glcaps, model);
            viewer.setOpaque(true);

            frame.getContentPane().add(viewer);
            frame.setVisible(true);
        }

    }

}
