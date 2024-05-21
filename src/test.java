import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.ImageIcon;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
public class test {
    private static int counter = 0;


    public test(int initial_heap) {
        JFrame frame = new JFrame("Limited Drag and Drop With Counter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null); // Using null layout for simplicity

        // Create an array of JLabels
        JLabel[] labels = new JLabel[initial_heap]; // You can adjust the number of labels as needed
        ImageIcon icon = new ImageIcon("src/resources/marble.png");
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(icon);
            label.setBounds(50 + 100 * i, 50, icon.getIconWidth(), icon.getIconHeight());


            label.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    label.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                }

                public void mouseReleased(MouseEvent e) {
                    label.setCursor(Cursor.getDefaultCursor());
                    if (isWithinDropArea(label) && counter < 3) {
                        frame.setVisible(false);
                        counter++;
                        JOptionPane.showMessageDialog(frame, "Image dropped! Counter: " + counter);
                    }
                }
            });
            label.addMouseMotionListener(new MouseAdapter() {
                public void mouseDragged(MouseEvent e) {
                    int x = e.getXOnScreen();
                    int y = e.getYOnScreen();
                    label.setLocation(x - label.getWidth() / 2, y - label.getHeight() / 2);
                }
            });
            frame.add(label);
            labels[i] = label;
        }


        // Create drop area
        JPanel dropArea = new JPanel();
        dropArea.setLayout(new BorderLayout());
        dropArea.setBackground(Color.LIGHT_GRAY);
        dropArea.setBounds(800, 650, 100, 100);
        JLabel dropLabel = new JLabel("Drop here!", SwingConstants.CENTER);
        dropArea.add(dropLabel, BorderLayout.CENTER);
        frame.add(dropArea);

        // Set frame size and make it visible
        frame.setSize(1000, 800);
        frame.setVisible(true);
    }

    // Method to check if the label overlaps with the drop area
    private static boolean isWithinDropArea(Component component) {
        Rectangle dropAreaBounds = new Rectangle(800, 650, 100, 100);
        Rectangle labelBounds = component.getBounds();
        return dropAreaBounds.intersects(labelBounds);
    }
}
