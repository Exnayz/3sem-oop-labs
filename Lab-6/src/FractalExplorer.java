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

    private int rowsRemaining;
    private JButton saveButton;
    private JButton resetButton;
    private JComboBox<FractalGenerator> myComboBox;

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

        resetButton = new JButton("Reset");
        ButtonHandler resetHandler = new ButtonHandler();
        resetButton.addActionListener(resetHandler);
        saveButton = new JButton("Save");
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

        myComboBox = new JComboBox<>();
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

    private void drawFractal() {
        enableUI(false);
        rowsRemaining = displaySize;
        for (int i = 0; i < displaySize; i++){
            FractalWorker drawRow = new FractalWorker(i);
            drawRow.execute();
        }
    }

    private void enableUI(boolean val) {
        myComboBox.setEnabled(val);
        resetButton.setEnabled(val);
        saveButton.setEnabled(val);
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
                        JOptionPane.showMessageDialog(image, exception.getMessage(), "Can't Save Image", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
    //update
    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e){
            if (rowsRemaining != 0) {
                return;
            }
            int x = e.getX(), y = e.getY();
            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);
            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            drawFractal();
        }
    }

    private class FractalWorker extends SwingWorker<Object, Object>
    {
        int intYCoord;
        int[] computedRGBValues;
        private FractalWorker(int row) {
            intYCoord = row;
        }

        protected Object doInBackground() {
            computedRGBValues = new int[displaySize];
            for (int i = 0; i < computedRGBValues.length; i++) {
                double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, i);
                double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, intYCoord);
                int iteration = fractal.numIterations(xCoord, yCoord);

                if (iteration == -1){
                    computedRGBValues[i] = 0;
                }
                else {
                    float hue = 0.7f + (float) iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    computedRGBValues[i] = rgbColor;
                }
            }
            return null;

        }

        protected void done() {
            for (int i = 0; i < computedRGBValues.length; i++) {
                image.drawPixel(i, intYCoord, computedRGBValues[i]);
            }
            image.repaint(0, 0, intYCoord, displaySize, 1);
            rowsRemaining--;
            if (rowsRemaining == 0) {
                enableUI(true);
            }
        }
    }

    public static void main(String[] args)
    {
        FractalExplorer explorer = new FractalExplorer(800);
        explorer.createAndShowGUI();
        explorer.drawFractal();
    }
}