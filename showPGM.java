/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package image1;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import java.util.*;

/**
 *
 * @author fleabag
 */
public class showPGM extends Component {

    private BufferedImage img;
    private int[][] px;
    private void pix2img() {
        int g;
        img = new BufferedImage(px[0].length, px.length, BufferedImage.TYPE_INT_ARGB);
        for (int row = 0; row < px.length; ++row) {
            for (int col = 0; col < px[row].length; ++col) {
                g = px[row][col];
                img.setRGB(col, row, ((255 << 24) | (g << 16) | (g << 8) | g));
            }
        }
    }

    public showPGM(String filename) {
        px = null;
        readFile(filename);
        if (px != null) {
            pix2img();
        }
        
       
        JFrame f = new JFrame("PGM");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.add(this);
        f.pack();
        f.setVisible(true);

    }
    
    public void readFile(String filename) {
        try {
            String filePath = filename;
            FileInputStream fileInputStream = new FileInputStream(filePath);

            Scanner scan = new Scanner(fileInputStream);
            scan.nextLine();
            scan.nextLine();
            
            int picHeight = scan.nextInt();
            int picWidth = scan.nextInt();
            int maxvalue = scan.nextInt();

            fileInputStream.close();
            fileInputStream = new FileInputStream(filePath);
            DataInputStream dis = new DataInputStream(fileInputStream);

            int numnewlines = 4;
            while (numnewlines > 0) {
                char c;
                do {
                    c = (char) (dis.readUnsignedByte());
                } while (c != '\n');
                numnewlines--;
            }

            int a;
            px = new int[picWidth][picHeight];
            for (int row = 0; row < picWidth; row++) {
                for (int col = 0; col < picHeight; col++) {
                    px[row][col] = dis.readUnsignedByte();
                }
            }

            fileInputStream.close();
        } catch (FileNotFoundException fe) {
            System.out.println("Had a problem opening a file.");
        } catch (Exception e) {
            System.out.println(e.toString() + " caught in readPPM.");
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) 
    {
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }

    public Dimension getPreferredSize() {
        if (img == null) {
            return new Dimension(100, 100);
        } else {
            // make sure the window is not two small to be seen
            return new Dimension(Math.max(100, img.getWidth(null)),
                    Math.max(100, img.getHeight(null)));
        }
    }
}
