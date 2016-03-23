/**
 * Copyright (c) <2016> <copyright Ming Liu>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package colormandelbrot;

import edu.princeton.cs.Picture;
import java.awt.Color;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.event.*;
import static javafx.scene.input.KeyCode.I;

/**
 *
 * @author Ming Liu
 * @version March 22 2016
 */
public class ColorMandelbrot extends JFrame implements ActionListener {

    //instance
    private final JPanel ctrlPanel;
    private final JPanel btnPanel;
    private final int numIter = 50;
    private double zoom = 130;
    private double zoomIncrease = 100;
    private int ITERS = 20;
    private BufferedImage I;
    private double zx, zy, xc, yc, temp;
    private int xMove = 0;
    private int yMove = 0;
    private final JButton[] ctrlBtns = new JButton[9];
    private final Color themeColor = new Color(150, 180, 200);

    /**
     * Constructor
     */
    public ColorMandelbrot() {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        plotPoints();

        Container contentPane = getContentPane();

        contentPane.setLayout(null);

        //Set up control Panel
        ctrlPanel = new JPanel();
        ctrlPanel.setBounds(600, 0, 200, 600);
        ctrlPanel.setBackground(Color.GREEN);
        ctrlPanel.setLayout(null);

        //Set up button Panel
        btnPanel = new JPanel();
        btnPanel.setBounds(0, 200, 200, 200);
        btnPanel.setLayout(new GridLayout(4, 4));
        btnPanel.setBackground(Color.GRAY);

        //Create buttons
        ctrlBtns[1] = new JButton("Up");
        ctrlBtns[7] = new JButton("Down");
        ctrlBtns[3] = new JButton("Left");
        ctrlBtns[5] = new JButton("Right");
        ctrlBtns[0] = new JButton("+");
        ctrlBtns[2] = new JButton("-");
        ctrlBtns[6] = new JButton(">");
        ctrlBtns[8] = new JButton("<");
        ctrlBtns[4] = new JButton("@");

        //add control and button panel to content Panel
        contentPane.add(ctrlPanel);
        contentPane.add(new imgPanel());
        ctrlPanel.add(btnPanel);

        for (int x = 0; x < ctrlBtns.length; x++) {
            btnPanel.add(ctrlBtns[x]);
            ctrlBtns[x].addActionListener(this);
        }//for

        validate();
    } // ColorMandelbrot

    public class imgPanel extends JPanel {

        public imgPanel() {
            setBounds(0, 0, 600, 600);
        } //imgPanel()  

        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(I, 0, 0, this);
        } //paint()
    } //imgPanel

    // return number of iterations to check if c = a + ib is in Mandelbrot set
    public static int mand(Complex z0, int d) {
        Complex z = z0;
        for (int t = 0; t < d; t++) {
            if (z.abs() > 2.0) {
                return t;
            } // if
            z = z.times(z).plus(z0);
        } // for
        return d;
    } // mand

    //Modified from Complex
    public void plotPoints() {
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                zx = zy = 0;
                xc = (x - 320 + xMove) / zoom;
                yc = (y - 290 + yMove) / zoom;
                int iter = numIter;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    temp = zx * zx - zy * zy + xc;
                    zy = 2 * zx * zy + yc;
                    zx = temp;
                    iter--;
                }//while
                I.setRGB(x, y, iter | (iter << ITERS));
            }//for
        }//for
    }//plotPoints
    
    
    //Create Action Events
    @Override
    public void actionPerformed(ActionEvent ae) {
        String event = ae.getActionCommand();

        switch (event) {
            case "Up":
                yMove -= 110;
                break;
            case "Down":
                yMove += 110;
                break;
            case "Left":
                xMove -= 110;
                break;
            case "Right":
                xMove += 110;
                break;
            case "+":
                double initialZoom = zoom;
                zoom += zoomIncrease;
                zoomIncrease *=2;
                xMove *=2;
                yMove *=2;
                break;
            case "-":
                zoom -= zoomIncrease;
                zoomIncrease -= 110;
                break;
            case ">":
                ITERS++;
                break;
            case "<":
                ITERS--;
                break;
        }//switch

        plotPoints();
        validate();
        repaint();
    } //actionPerfomed

    public static void main(String[] args) {
//        ColorMandelbrot colorMandelbrot = new ColorMandelbrot();
//        colorMandelbrot.plotPoints();
        new ColorMandelbrot().setVisible(true);
    }//main ( String, args )    

}//ColorMandelbrot
