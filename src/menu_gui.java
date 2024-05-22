/*

menu gui source code
@file menu_gui.java
@author David Zhang
@version 1.0 May 21, 2024

*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menu_gui extends JFrame {

    private JTextField heapSizeTextField;

    public menu_gui() {
        // create layout
        setTitle("Dr. Nim - Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // panel to hold buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add margin from the sides

        // panel to enter amount of marbles for game
        JPanel heapSizePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel heapSizeLabel = new JLabel("Enter amount of marbles:");
        heapSizeTextField = new JTextField(5); // Create a text field for heap size input
        heapSizePanel.add(heapSizeLabel);
        heapSizePanel.add(heapSizeTextField);
        buttonPanel.add(heapSizePanel);

        // one player button
        JButton singlePlayerButton = new JButton("Single Player");
        singlePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose(); // Close the menu frame
                // take in input from the textbox
                // if wrong format, output message "input an positive integer"
                // same for all buttons
                try {
                    int initialHeapSize = Integer.parseInt((heapSizeTextField.getText()));
                    new single_player(initialHeapSize);
                }
                catch (NumberFormatException excpt){
                    System.out.println("Input an positive integer");

                }
            }
        });
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add vertical space between buttons
        buttonPanel.add(singlePlayerButton);

        // 2 player button
        JButton doublePlayerButton = new JButton("Double Player");
        doublePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    int initialHeapSize = Integer.parseInt((heapSizeTextField.getText()));
                    new double_player(initialHeapSize,1);
                }
                catch (NumberFormatException excpt){
                    System.out.println("Input an positive integer");

                    }
            }
        });
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(doublePlayerButton);

        // 3 player button
        JButton triplePlayerButton = new JButton("Triple Player ( 1 or 2 CPU )");
        triplePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose(); // Close the menu frame
                try {
                    int initialHeapSize = Integer.parseInt((heapSizeTextField.getText()));
                    // special menu to choose 1 or 2 cpus
                    new triple_option(initialHeapSize);
                }
                catch (NumberFormatException excpt){
                    System.out.println("Input an positive integer");

                }
            }
        });
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(triplePlayerButton);

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);

    }
}
