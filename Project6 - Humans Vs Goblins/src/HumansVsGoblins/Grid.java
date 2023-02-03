import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class Grid {

    public Grid(Goblin[] goblins, Human human) {
        JLabel[][] labelGrid = createLabelGrid(6, 6);
        JPanel labelPanel = createPanel(labelGrid);
        JPanel buttonPanel = createButtonPanel(labelGrid, goblins, human);

        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(1000, 1000));
        frame.setLayout(new GridLayout(2, 1));
        frame.add(labelPanel);
        frame.add(buttonPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        ArrayList<Integer> humanPos = human.getPosition();
        labelGrid[humanPos.get(0)][humanPos.get(1)].setText("H");

        for (int i = 0; i < goblins.length; i++) {
            ArrayList<Integer> goblinPos = goblins[i].getPosition();
            labelGrid[goblinPos.get(0)][goblinPos.get(1)].setText("G");
        }
    }

    private JLabel[][] createLabelGrid(int rows, int cols) {
        JLabel[][] labels = new JLabel[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                labels[i][j] = new JLabel(" ");
            }
        }
        return labels;
    }

    private JPanel createPanel(JLabel[][] labels) {
        int rows = labels.length;
        int cols = labels[0].length;
        JPanel panel = new JPanel(new GridLayout(rows, cols));
        for (JLabel[] rowOfLabels: labels) {
            for (JLabel label : rowOfLabels) {
                panel.add(label);
            }
        }
        return panel;
    }

    private JPanel createButtonPanel(JLabel[][] labels, Goblin[] goblins, Human human) {
        int rows = 1;
        int cols = 4;
        String[] direction = {"n", "s", "e", "w"};
        JButton[] buttons = new JButton[cols];
        JPanel panel = new JPanel(new GridLayout(rows, cols));
        final int[] gobCount = {0}; // determines which goblin you are fighting
        for (int j = 0; j < cols; j++) {
            JButton button = new JButton(direction[j]);
            buttons[j] = button;
            final String jStr = direction[j];
            final int jTemp = j;
            final int iTemp = 0;

            button.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    ArrayList<Integer> prevPos = new ArrayList<>();
                    prevPos.add(human.getPosition().get(0));
                    prevPos.add(human.getPosition().get(1));
                    String key = Character.toString(e.getKeyChar());
                    labels[prevPos.get(0)][prevPos.get(1)].setText(" ");
                    human.setPosition(key);

                    ArrayList<Integer> pos = new ArrayList<>();
                    pos.add(human.getPosition().get(0));
                    pos.add(human.getPosition().get(1));
                    JLabel label;

                    // protection from going out of bounds
                    try {
                        label = labels[pos.get(0)][pos.get(1)];
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "You are attempting to go out of bounds. Please pick a different direction");
                        label = labels[prevPos.get(0)][prevPos.get(1)];
                        human.setPosition(prevPos.get(0), prevPos.get(1));
                    }

                    // combat
                    if (label.getText().equals("G")) {
                        JOptionPane.showMessageDialog(null, "You have entered combat!");
                        while(human.getHealth() != 0 && goblins[gobCount[0]].getHealth() != 0) {
                            JOptionPane.showMessageDialog(null, "You attacked goblin for " + human.attack(goblins[gobCount[0]]) + ". Remaining goblin health: " + goblins[gobCount[0]].getHealth());
                            JOptionPane.showMessageDialog(null, "Goblin attacked you for " + goblins[gobCount[0]].attack(human) + " Your remaining health: " + human.getHealth());
                        }
                        if (human.getHealth() == 0) {
                            label.setText("G");
                            JOptionPane.showMessageDialog(null, "The goblin has killed you. Game over!");
                        } else {
                            label.setText("H");
                            if (gobCount[0] == goblins.length - 1) {
                                JOptionPane.showMessageDialog(null, "You're a hero! You killed all the goblins. You've won the game!");
                            }
                        }
                        gobCount[0]++;
                    } else label.setText("H");
                }

                @Override
                public void keyPressed(KeyEvent e) {

                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ArrayList<Integer> prevPos = new ArrayList<>();
                    prevPos.add(human.getPosition().get(0));
                    prevPos.add(human.getPosition().get(1));
                    labels[prevPos.get(0)][prevPos.get(1)].setText(" ");
                    human.setPosition(jStr);

                    ArrayList<Integer> pos = new ArrayList<>();
                    pos.add(human.getPosition().get(0));
                    pos.add(human.getPosition().get(1));
                    JLabel label;

                    // protection from going out of bounds
                    try {
                        label = labels[pos.get(0)][pos.get(1)];
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "You are attempting to go out of bounds. Please pick a different direction");
                        label = labels[prevPos.get(0)][prevPos.get(1)];
                        human.setPosition(prevPos.get(0), prevPos.get(1));
                    }

                    if (label.getText().equals("G")) {
                        JOptionPane.showMessageDialog(null, "You have entered combat!");
                        while(human.getHealth() != 0 && goblins[gobCount[0]].getHealth() != 0) {
                            JOptionPane.showMessageDialog(null, "You attacked goblin for " + human.attack(goblins[gobCount[0]]) + ". Remaining goblin health: " + goblins[gobCount[0]].getHealth());
                            JOptionPane.showMessageDialog(null, "Goblin attacked you for " + goblins[gobCount[0]].attack(human) + " Your remaining health: " + human.getHealth());
                        }
                        if (human.getHealth() == 0) {
                            label.setText("G");
                            JOptionPane.showMessageDialog(null, "The goblin has killed you. Game over!");
                        } else {
                            label.setText("H");
                            if (gobCount[0] == goblins.length - 1) {
                                JOptionPane.showMessageDialog(null, "You're a hero! You killed all the goblins. You've won the game!");
                            }
                        }
                        gobCount[0]++;
                    } else label.setText("H");
                }
            });
            panel.add(button);
        }
        return panel;
    }
}
