import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

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
        image.setLayout(new BorderLayout());
        JFrame frame = new JFrame("Fractal Explorer");
        frame.add(image, BorderLayout.CENTER);

        JButton resetButton = new JButton("Reset");
        ButtonHandler resetHandler = new ButtonHandler();
        resetButton.addActionListener(resetHandler);
        JButton saveButton = new JButton("Save");
        JPanel myButtomPanel = new JPanel();
        myButtomPanel.add(saveButton);
        myButtomPanel.add(resetButton);
        frame.add(myButtomPanel, BorderLayout.SOUTH);
        ButtonHandler saveHandler = new ButtonHandler();
        saveButton.addActionListener(saveHandler);

        MouseHandler click = new MouseHandler();
        image.addMouseListener(click);

        FractalGenerator mandelbrotFractal = new Mandelbrot();
        FractalGenerator tricornFractal = new Tricorn();
        FractalGenerator burningShipFractal = new BurningShip();

        JComboBox<FractalGenerator> myComboBox = new JComboBox<>();
        myComboBox.addItem(mandelbrotFractal);
        myComboBox.addItem(tricornFractal);
        myComboBox.addItem(burningShipFractal);
        ButtonHandler fractalChooser = new ButtonHandler();
        myComboBox.addActionListener(fractalChooser);

        JPanel myPanel = new JPanel();
        JLabel myLabel = new JLabel("Fractal:");
        myPanel.add(myLabel);
        myPanel.add(myComboBox);
        frame.add(myPanel, BorderLayout.NORTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String command = event.getActionCommand();

            if (event.getSource() instanceof JComboBox mySource) {
                fractal = (FractalGenerator) mySource.getSelectedItem();
                assert fractal != null;
                fractal.getInitialRange(range);
                drawFractal();
            }

            else if (command.equals("Reset")) {
                fractal.getInitialRange(range);
                drawFractal();
            }

            else if (command.equals("Save")) {
                JFileChooser fileChooser = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
                fileChooser.setFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);

                int userSelection = fileChooser.showSaveDialog(image);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        BufferedImage displayImage = image.getImage();
                        ImageIO.write(displayImage, "png", file);
                    }
                    catch (Exception exception) {
                        JOptionPane.showMessageDialog(image, exception.getMessage(), "Cannot Save Image", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
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