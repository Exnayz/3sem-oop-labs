import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class FractalExplorer {
    private int displaySize = 0;
    private JImageDisplay image = null;
    private FractalGenerator fractal = null;
    private Rectangle2D.Double range = null;

    public FractalExplorer(int _displaySize) {
        displaySize = _displaySize;
        fractal = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractal.getInitialRange(range);
        image = new JImageDisplay(displaySize, displaySize);
    }

    public void createAndShowGUI(){
        JFrame frame = new JFrame("Fractal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(image, BorderLayout.CENTER);

        JButton button = new JButton("Reset");
        ResetHandler reset = new ResetHandler();
        button.addActionListener(reset);
        frame.add(button, BorderLayout.SOUTH);

        MouseHandler click = new MouseHandler();
        image.addMouseListener(click);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void drawFractal(){
        double x, y;
        int iteration;
        for (int i = 0; i < displaySize; i++) {
            for (int j = 0; j < displaySize; j++) {
                x = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, i);
                y = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, j);
                iteration = fractal.numIterations(x, y);
                if (iteration == -1) {
                    image.drawPixel(i, j, 0);
                }
                else {
                    float hue = 0.7f + (float) iteration/200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    image.drawPixel(i, j, rgbColor);
                }
            }
        }
        image.repaint();
    }

    private class ResetHandler implements ActionListener {
        public void actionPerformed(ActionEvent e){
            fractal.getInitialRange(range);
            drawFractal();
        }
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e){
            int x = e.getX(), y = e.getY();
            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);
            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            drawFractal();
        }
    }

    public static void main(String[] args)
    {
        FractalExplorer explorer = new FractalExplorer(800);
        explorer.createAndShowGUI();
        explorer.drawFractal();
    }
}
