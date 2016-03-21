package colormandelbrot;


import edu.princeton.cs.Picture;
import java.awt.Color;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.event.*;
import static javafx.scene.input.KeyCode.I;

public class ColorMandelbrot extends JFrame implements ActionListener  {
    
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
    
    public ColorMandelbrot (){
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        plotPoints();

        Container contentPane = getContentPane();

        contentPane.setLayout(null);

        ctrlPanel = new JPanel();
        ctrlPanel.setBounds(600, 0, 200, 600);
        ctrlPanel.setBackground(themeColor);
        ctrlPanel.setLayout(null);

        btnPanel = new JPanel();
        btnPanel.setBounds(0, 200, 200, 200);
        btnPanel.setLayout(new GridLayout(3, 3));
        btnPanel.setBackground(themeColor);

        ctrlBtns[1] = new JButton("up");
        ctrlBtns[7] = new JButton("down");
        ctrlBtns[3] = new JButton("left");
        ctrlBtns[5] = new JButton("right");
        ctrlBtns[2] = new JButton("+");
        ctrlBtns[0] = new JButton("-");
        ctrlBtns[8] = new JButton(">");
        ctrlBtns[6] = new JButton("<");
        ctrlBtns[4] = new JButton();

        contentPane.add(ctrlPanel);
        contentPane.add(new imgPanel());
        ctrlPanel.add(btnPanel);

        for (int x = 0; x < ctrlBtns.length; x++) {
            btnPanel.add(ctrlBtns[x]);
            ctrlBtns[x].addActionListener(this);
        }//for

        validate();
    }
    
    public class imgPanel extends JPanel {

        public imgPanel() {
            setBounds(0, 0, 600, 600);
        }//imgPanel()  
         public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(I, 0, 0, this);
        }//paint()
       }
    
    // return number of iterations to check if c = a + ib is in Mandelbrot set
    public static int mand(Complex z0, int d) {
        Complex z = z0;
        for (int t = 0; t < d; t++) {
            if (z.abs() > 2.0) return t;
            z = z.times(z).plus(z0);
        }
        return d;
    }
    
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
    
    public void actionPerformed(ActionEvent ae) {
        String event = ae.getActionCommand();

        switch (event) {
            case "up":
                yMove -= 100;
                break;
            case "down":
                yMove += 100;
                break;
            case "left":
                xMove -= 100;
                break;
            case "right":
                xMove += 100;
                break;            
            case "+":
                double initialZoom = zoom;
                zoom += zoomIncrease;
                zoomIncrease *= 2;
                xMove *= 2;
                yMove *= 2;
                break;
            case "-":
                zoom -= zoomIncrease;
                zoomIncrease -= 100;
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
    }//actionPerfomed
    
    public static void main(String[] args)  {
//        ColorMandelbrot colorMandelbrot = new ColorMandelbrot();
//        colorMandelbrot.plotPoints();
          new ColorMandelbrot().setVisible(true);
    }//main ( String, args )    

}//ColorMandelbrot