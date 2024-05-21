import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MultipleDraggableObjects {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Multiple Draggable Objects");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null); // Using null layout for simplicity

        // Create an array of JLabels
        JLabel[] labels = new JLabel[3]; // You can adjust the number of labels as needed

        // Load an image to use as an icon
        ImageIcon icon = new ImageIcon("src/resources/marble.png"); // Replace "icon.png" with your image file path

        // Create and configure each label
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(icon);
            label.setBounds(50 + 100 * i, 50, icon.getIconWidth(), icon.getIconHeight());

            // Add mouse listener to make the label draggable
            label.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    label.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                }

                public void mouseReleased(MouseEvent e) {
                    label.setCursor(Cursor.getDefaultCursor());
                }
            });
            label.addMouseMotionListener(new MouseAdapter() {
                public void mouseDragged(MouseEvent e) {
                    int x = e.getXOnScreen();
                    int y = e.getYOnScreen();
                    label.setLocation(x - label.getWidth() / 2, y - label.getHeight() / 2);
                }
            });

            // Add label to the frame
            frame.add(label);
            labels[i] = label; // Store the label in the array
        }

        // Set frame size and make it visible
        frame.setSize(600, 200);
        frame.setVisible(true);
    }
}
