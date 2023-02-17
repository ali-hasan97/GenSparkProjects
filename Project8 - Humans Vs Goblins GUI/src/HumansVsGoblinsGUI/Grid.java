import java.awt.*;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class Grid {

    public Grid(Goblin[] goblins, Human human) {
        // human image icon
        ImageIcon humanImageIcon = null;
        try{
            humanImageIcon = new ImageIcon("./src/HumansVsGoblinsGUI/Resources/human.jpg");
        } catch (Exception ex) {
            System.out.println("Failed to load image");
            ex.printStackTrace();
        }

        // goblin image icon
        ImageIcon goblinImageIcon = null;
        try{
            goblinImageIcon = new ImageIcon("./src/HumansVsGoblinsGUI/Resources/goblin.jpg");
        } catch (Exception exc) {
            System.out.println("Failed to load image");
            exc.printStackTrace();
        }

        // blank image icon
        ImageIcon blankImageIcon = null;
        try{
            blankImageIcon = new ImageIcon("./src/HumansVsGoblinsGUI/Resources/blank.jpg");
        } catch (Exception exc) {
            System.out.println("Failed to load image");
            exc.printStackTrace();
        }

        // create all the areas of the GUI
        JLabel[][] labelGrid = createLabelGrid(6, 6, blankImageIcon);
        JPanel labelPanel = createPanel(labelGrid);
        JTextArea textArea = new JTextArea("", 4, 50); // Do not set initial text because it will not scroll to bottom anymore due to improper carat placement.
        JPanel buttonPanel = createButtonPanel(labelGrid, textArea, goblins, human, blankImageIcon, humanImageIcon, goblinImageIcon);
        JPanel textPanel = createTextPanel(textArea);

        JFrame frame = new JFrame("Human Vs Goblins");
        frame.setPreferredSize(new Dimension(600, 765)); // 65px textArea + 600px grid (6 x 100px cells) + 100px buttons
        frame.add(labelPanel);
        frame.add(textPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // set human icon
        ArrayList<Integer> humanPos = human.getPosition();
        labelGrid[humanPos.get(0)][humanPos.get(1)].setIcon(humanImageIcon);

        // set goblin icons
        for (Goblin goblin : goblins) {
            ArrayList<Integer> goblinPos = goblin.getPosition();
            labelGrid[goblinPos.get(0)][goblinPos.get(1)].setIcon(goblinImageIcon);
        }

        JOptionPane.showMessageDialog(null, "Welcome to Humans vs Goblins! Kill all the goblins to win. Move your player by pressing the 'n, s, e, w' keys or by clicking them below. Good luck!");
        textArea.append("Welcome to Humans vs Goblins! Kill all the goblins to win. Move your player by pressing the 'n, s, e, w' keys or by clicking them below. Good luck!");
    }

    // constructs grid of blank icons
    private JLabel[][] createLabelGrid(int rows, int cols, ImageIcon blankImageIcon) {
        JLabel[][] labels = new JLabel[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // grid created with blank image icons so they can be changed later
                JLabel blankImage = new JLabel(blankImageIcon);
                labels[i][j] = blankImage;
            }
        }
        return labels;
    }

    // create panel of grid
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

    // create panel for combat logger
    private JPanel createTextPanel(JTextArea textArea) {
        JPanel textPanel = new JPanel(new GridLayout(1, 1));
        textArea.setEditable(false);
        textArea.setFocusable(false); // prevents textArea from consuming keyTyping listeners
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // Set up scrollbar and scroll to bottom automatically thanks to caret.
        JScrollPane sbarText = new JScrollPane(textArea);
        sbarText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        DefaultCaret caret = (DefaultCaret)textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        textPanel.add(sbarText);
        return textPanel;
    }

    // create panel for movement buttons
    private JPanel createButtonPanel(JLabel[][] labels, JTextArea textArea, Goblin[] goblins, Human human, ImageIcon blankImageIcon, ImageIcon humanImageIcon, ImageIcon goblinImageIcon) {
        int rows = 1;
        int cols = 4;
        String[] direction = {"n", "s", "e", "w"};
        JPanel panel = new JPanel(new GridLayout(rows, cols));
        panel.setPreferredSize(new Dimension(600, 100));
        final int[] gobCount = {0}; // determines which goblin you are fighting
        for (int j = 0; j < cols; j++) {
            JButton button = new JButton(direction[j]);
            String dir = direction[j];

            // detect keyboard typing
            button.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    String key = Character.toString(e.getKeyChar());
                    JLabel label = handleMove(human, key, labels, blankImageIcon);

                    if (label.getIcon().equals(goblinImageIcon)) {
                        combat(human, goblins, gobCount, label, humanImageIcon, textArea);
                    } else {
                        label.setIcon(humanImageIcon);
                    }
                }

                @Override
                public void keyPressed(KeyEvent e) {

                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });

            // set clickable button listeners
            button.addActionListener(e -> {
                JLabel label = handleMove(human, dir, labels, blankImageIcon);

                if (label.getIcon().equals(goblinImageIcon)) {
                    combat(human, goblins, gobCount, label, humanImageIcon, textArea);
                } else {
                    label.setIcon(humanImageIcon);
                }
            });
            panel.add(button);
        }
        return panel;
    }

    private JLabel handleMove(Human human, String key, JLabel[][] labels, ImageIcon blankImageIcon) {
        ArrayList<Integer> prevPos = new ArrayList<>();
        prevPos.add(human.getPosition().get(0));
        prevPos.add(human.getPosition().get(1));
        labels[prevPos.get(0)][prevPos.get(1)].setIcon(blankImageIcon);
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
        return label;
    }

    private void combat(Human human, Goblin[] goblins, int[] gobCount, JLabel label, ImageIcon humanImageIcon, JTextArea textArea) {
        JOptionPane.showMessageDialog(null, "You have entered combat!");
        textArea.append("\nYou have entered combat!");

        while(human.getHealth() != 0 && goblins[gobCount[0]].getHealth() != 0) {
            int humAtt = human.attack(goblins[gobCount[0]]);
            int gobAtt = goblins[gobCount[0]].attack(human);
            int humHealth = human.getHealth();
            int gobHealth = goblins[gobCount[0]].getHealth();

            JOptionPane.showMessageDialog(null, "You attacked goblin for " + humAtt + ". Remaining goblin health: " + gobHealth);
            JOptionPane.showMessageDialog(null, "Goblin attacked you for " + gobAtt + ". Your remaining health: " + humHealth);
            textArea.append("\nYou attacked goblin for " + humAtt + ". Remaining goblin health: " + gobHealth);
            textArea.append("\nGoblin attacked you for " + gobAtt + ". Your remaining health: " + humHealth);
        }

        if (human.getHealth() == 0) {
            JOptionPane.showMessageDialog(null, "The goblin has killed you. Game over!");
            textArea.append("\nThe goblin has killed you. Game over!");
            System.exit(0);
        } else {
            label.setIcon(humanImageIcon);
            if (gobCount[0] == goblins.length - 1) {
                JOptionPane.showMessageDialog(null, "You're a hero! You killed all the goblins. You've won the game!");
                textArea.append("\nYou're a hero! You killed all the goblins. You've won the game!");
            }
        }
        gobCount[0]++;
    }
}
