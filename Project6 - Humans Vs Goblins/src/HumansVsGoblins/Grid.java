import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Grid {

    public Grid(Goblin goblin, Human human) {
        JLabel[][] labelGrid = createLabelGrid(6, 6);
        JPanel labelPanel = createPanel(labelGrid);
        JPanel buttonPanel = createButtonPanel(labelGrid, goblin, human);

        JFrame frame = new JFrame();
        frame.setLayout(new GridLayout(2, 1));
        frame.add(labelPanel);
        frame.add(buttonPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JLabel[][] createLabelGrid(int rows, int cols) {
        JLabel[][] labels = new JLabel[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                labels[i][j] = new JLabel("(" + i + " , " + j + " )");
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

    private JPanel createButtonPanel(JLabel[][] labels, Goblin goblin, Human human) {
        int rows = 1;
        int cols = 4;
        String[] direction = {"n", "s", "e", "w"};
        JButton[] buttons = new JButton[cols];
        JPanel panel = new JPanel(new GridLayout(rows, cols));
        for (int j = 0; j < cols; j++) {
            JButton button = new JButton(direction[j]);
            buttons[j] = button;
            final String jStr = direction[j];
            final int jTemp = j;
            final int iTemp = 0;
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    human.setPosition(jStr);
                    int[] pos = human.getPosition();
                    JLabel label = labels[pos[0]][pos[1]];
                    label.setText("h");
                }
            });
            panel.add(button);
        }
        return panel;
    }
}
