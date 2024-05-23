/*

one player gui and logic
@file single_player.java
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


    public class single_player extends JFrame {

        private JLabel heapLabel;
        private int heapSize;
        private static int counter = 0;
        private boolean playerTurn;
        public JLabel[] labels;


        public single_player(int initialHeapSize, int starting) {
            // int to track number of marbles in game
            heapSize = initialHeapSize;
            // boolean to track whose turn it is
            playerTurn = starting == 1; // Player starts


            // set up frame
            JFrame frame = new JFrame("Dr. Nim (Single Player)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 600);
            frame.setBackground(Color.LIGHT_GRAY);
            frame.setLayout(null);
            frame.setLocationRelativeTo(null);

            // Heap label

            heapLabel = new JLabel("Heap size: " + heapSize, SwingConstants.CENTER);
            heapLabel.setBounds(400, 10, 100, 100);

            JButton endTurnButton = new JButton("End Turn");
            endTurnButton.setBounds(350, 500, 100, 50);
            endTurnButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent end) {
                    if (counter == 0 && playerTurn == true) { // case when no marbles are dropped
                        JOptionPane.showMessageDialog(frame, "Must take atleast one marble");
                    }
                    else {
                        if (heapSize > 0) {
                            // update game state then dispose of current frame and reboot new frame
                            removeObjectsFromHeap(counter);
                            counter = 0;
                            frame.dispose();
                            new single_player(heapSize, 1); // Pass initial heap size or any necessary parameters
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
            int to_nine = 0; // carryover to start a new row of marbles
            for (int i = 0; i < labels.length; i++) {
                to_nine++;
                JLabel label = new JLabel(icon);
                label.setBounds((50 + 100 * i) % 1100, 100 + 60 * Math.floorDiv(to_nine, 12), icon.getIconWidth(), icon.getIconHeight());
                labels[i] = label;
                objectPanel.add(labels[i]);


                MouseAdapter mouseAdapter = new MouseAdapter() {
                    private Point initial; // point object to track x and y of mouse

                    public void mousePressed (MouseEvent e){
                        initial = e.getPoint(); // get point when pressed
                        label.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                    }


                    public void delete (Component component){
                        // delete marble by setting it invisible and deleting from frame
                        label.setVisible(false);
                        frame.remove(component);
                    }

                    public void mouseReleased (MouseEvent e) {
                        label.setCursor(Cursor.getDefaultCursor());
                        Rectangle dropAreaBounds = new Rectangle(1000, 500, 100, 100);
                        // using the bounds of the drop area detect if the boundaries intersect
                        // delete the marble and increase the counter
                        if (!playerTurn) {
                            JOptionPane.showMessageDialog(frame, "End your turn, CPU goes first");
                        } else {
                            if (dropAreaBounds.intersects(label.getBounds()) && counter < 3) {
                                delete(label);
                                counter++;
                                JOptionPane.showMessageDialog(frame, "Marble dropped! Counter: " + counter);
                            }
                        }
                    }

                    // this is the tracker that moves the marble with the mouse
                    // very buggy when moving the application in window
                    // on my local end fast window movement and resizing messes up the offset
                    public void mouseDragged (MouseEvent e){
                        // get initial location
                        int px = label.getLocation().x;
                        int py = label.getLocation().y;
                        // get offset location
                        int x = e.getX() - initial.x;
                        int y = e.getY() - initial.y;
                        //combining both initial and offset constantly to give effect of the marble following mouse
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
            dropArea.setBackground(Color.GRAY);
            dropArea.setBounds(1000,500,100,100);
            JLabel dropLabel = new JLabel("Drop here!", SwingConstants.CENTER);
            dropArea.add(dropLabel, BorderLayout.CENTER);
            frame.add(dropArea);
            // make it visible
            frame.setSize(1200, 700);
            frame.setVisible(true);

        }
            // Remove objects from the heap
            private void removeObjectsFromHeap ( int objectsToRemove){
                if (heapSize >= objectsToRemove) { // case check heap size doesn't go below zero
                    heapSize -= objectsToRemove;
                    updateHeapLabel();
                    checkGameStatus();
                    playerTurn = false; // Player's turn ends
                    playComputerTurn();
                }
            }

            // Update the heap label with the new heap size
            private void updateHeapLabel () {
                heapLabel.setText("Heap size: " + heapSize);
            }


            // Perform computer's turn
            private void playComputerTurn () {
            int objectsToRemove = 0;
                if (heapSize > 0) {
                    // basic strategy is to always keep the heap size as a multiple of 4
                    // this gets tricky when the start size isn't predetermined
                    if ((heapSize)%4 == 1) { // losing scenario
                        objectsToRemove = 1;
                    }
                    else if (heapSize % 5 == 0 ) {
                        objectsToRemove = 1;
                    }
                    // rest is edge casing
                    else if (heapSize > 5 && heapSize < 9){ // leave at 5 for player
                        objectsToRemove = heapSize-5;
                    }
                    else if (heapSize % 5 == 1){
                        objectsToRemove = 2;
                    }
                    else if (heapSize == 2) {
                        objectsToRemove = 1;
                    }
                    else if (heapSize == 3) {
                        objectsToRemove = 2;
                    }
                    else if (counter == 0){
                        objectsToRemove = 1;
                    }
                    else {
                        objectsToRemove = 4-counter;
                    }



                    heapSize -= objectsToRemove;
                    updateHeapLabel();
                    JOptionPane.showMessageDialog(this, "CPU removes " + objectsToRemove + " marbles from the heap");
                    checkGameStatus();
                    playerTurn = true; // Computer's turn ends
                }
            }

            // Check if the game is over
            private void checkGameStatus () {
                if (heapSize == 0) {
                    if (playerTurn) {
                        JOptionPane.showMessageDialog(this, "Computer wins!");
                        System.exit(0);
                    } else {
                        JOptionPane.showMessageDialog(this, "You win!");
                        System.exit(0);
                    }


                }
            }
}
