/*

separate menu option for triple player
@file triple_option.java
@author David Zhang
@version 1.0 May 21, 2024

*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class triple_option extends JFrame {

    public triple_option(int initialHeapSize) {
        // create a small gui
        setTitle("Game Options");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Select the number of CPUs:");
        add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JButton oneCPUButton = new JButton("1 CPU");
        JButton twoCPUButton = new JButton("2 CPUs");

        oneCPUButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(triple_option.this, "You selected 1 CPU");
                // dispose menu frame and initialize game frame
                dispose();
                new triple_player(initialHeapSize, true, 0);

            }
        });

        twoCPUButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // same here dispose menu and start game
                JOptionPane.showMessageDialog(triple_option.this, "You selected 2 CPUs");
                dispose();
                new triple_player(initialHeapSize, false, 0);
            }
        });

        buttonPanel.add(oneCPUButton);
        buttonPanel.add(twoCPUButton);
        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

}
