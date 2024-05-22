/*

two player gui and logic
@file double_player.java
@author David Zhang
@version 1.0 May 21, 2024

*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class double_player extends JFrame {

    private JLabel heapLabel;
    private int heapSize;
    private static int counter = 0;
    private int playerTurn;
    public JLabel[] labels;


    public double_player(int initialHeapSize, int curr) {
        heapSize = initialHeapSize;
        // player turn turns to int here for easier logic
        playerTurn = curr;


        // set up frame
        JFrame frame =  new JFrame("Dr. Nim (Double Player)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setBackground(Color.LIGHT_GRAY);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        // Heap label

        // labels and marble initialization is identical to the one in single_player.java
        // just here there are no instructions for cpu ( 2 humans playing each-other)
        heapLabel = new JLabel("Heap size: " + heapSize, SwingConstants.CENTER);
        heapLabel.setBounds(400, 10, 100, 100);

        JButton endTurnButton = new JButton("End Turn");
        endTurnButton.setBounds(350, 500, 100, 50);
        endTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent end) {
                if (counter == 0) {
                    JOptionPane.showMessageDialog(frame, "Must take atleast one marble");
                }
                else {
                    if (heapSize > 0) {
                        frame.dispose();
                        removeObjectsFromHeap(counter);
                        counter = 0;

                        new double_player(heapSize,playerTurn); // Pass initial heap size or any necessary parameters

                    }
                }
            }
        });


        frame.add(endTurnButton);
        frame.add(heapLabel);

        // Create an array of JLabels
        JPanel objectPanel = new JPanel(new FlowLayout());
        labels = new JLabel[heapSize]; // You can adjust the number of labels as needed
        ImageIcon icon = new ImageIcon("src/resources/marble.png");
        int to_nine = 0;
        for (int i = 0; i < labels.length; i++) {
            to_nine++;
            JLabel label = new JLabel(icon);
            label.setBounds((50 + 100 * i) % 1100, 100 + 60 * Math.floorDiv(to_nine, 12), icon.getIconWidth(), icon.getIconHeight());
            labels[i] = label;
            objectPanel.add(labels[i]);


            MouseAdapter mouseAdapter = new MouseAdapter() {
                private Point initial;

                public void mousePressed (MouseEvent e){
                    initial = e.getPoint();
                    label.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                }

                // TODO
                // the remove makes it not interactable but the image stays???
                // can probably visible(false)
                public void delete (Component component){
                    label.setVisible(false);
                    frame.remove(component);
                }

                public void mouseReleased (MouseEvent e){
                    label.setCursor(Cursor.getDefaultCursor());
                    Rectangle dropAreaBounds = new Rectangle(1000, 500, 100, 100);
                    if (dropAreaBounds.intersects(label.getBounds()) && counter < 3) {
                        delete(label);
                        counter++;
                        JOptionPane.showMessageDialog(frame, "Marble dropped! Counter: " + counter);
                    }
                }
                public void mouseDragged (MouseEvent e){
                    int px = label.getLocation().x;
                    int py = label.getLocation().y;
                    int x = e.getX() - initial.x;
                    int y = e.getY() - initial.y;
                    label.setLocation(x+px, y+py);
                }
            };
            label.addMouseListener(mouseAdapter);
            label.addMouseMotionListener(mouseAdapter);
            frame.add(label);
        }


        // Create drop area
        JPanel dropArea = new JPanel();
        dropArea.setLayout(new BorderLayout());
        dropArea.setBackground(Color.LIGHT_GRAY);
        dropArea.setBounds(1000, 500, 100, 100);
        JLabel dropLabel = new JLabel("Drop here!", SwingConstants.CENTER);
        dropArea.add(dropLabel, BorderLayout.CENTER);
        frame.add(dropArea);

        // Set frame size and make it visible
        frame.setSize(1200, 700);
        frame.setVisible(true);

    }
    // Remove objects from the heap
    private void removeObjectsFromHeap ( int objectsToRemove){
        if (heapSize >= objectsToRemove) {
            heapSize -= objectsToRemove;
            updateHeapLabel();
            checkGameStatus();
            playerTurn++; // Player's turn ends
        }
    }

    // Update the heap label with the new heap size
    private void updateHeapLabel () {
        heapLabel.setText("Heap size: " + heapSize);
    }


    // Check if the game is over
    private void checkGameStatus () {
        if (heapSize == 0) {
            if (playerTurn%2 == 1) {
                JOptionPane.showMessageDialog(this, "Player 2 wins!");
                System.exit(0);
            }
            else {
                JOptionPane.showMessageDialog(this, "Player 1 wins!");
                System.exit(0);

            }


        }
    }
}
