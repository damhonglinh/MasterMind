package MasterMindGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Dam Linh - s3372757
 */
public class StatisticPanel extends BeautifulPanel implements Observer {

    public static final int MY_WIDTH = 260;
    private int numberOfPlayer;
    private int currentRound = 1;
    private Player player1;
    private Player player2;
    private JPanel stat;
    private JPanel player1Panel;
    private JPanel player2Panel;
    private JPanel resultPanel = new JPanel(new GridLayout(3, 1));
    private JLabel[] player1Labels = new JLabel[5];
    private JLabel[] player2Labels = new JLabel[5];
    private JLabel[] resultLabels = new JLabel[2];
    private JLabel titleLabel = new JLabel();
    private JLabel player1Label;
    private JLabel player2Label;
    private JButton actionButton;

    public StatisticPanel(Player p1, Player p2) {
        if (p1 != null) {
            if (p2 == null) {
                numberOfPlayer = 1;
                player1 = p1;
            } else {
                numberOfPlayer = 2;
                player1 = p1;
                player2 = p2;
            }
        } else {
            numberOfPlayer = 0;
            player1 = new Player();
        }
        initialize();
    }

    private void initialize() {
        setLayout(null);
        setOpaque(false);//set transparent background

        //draw action button
        actionButton = new ActionButton("Quit");
        actionButton.setBounds(87, 380, 100, 40);
        add(actionButton);

        //draw Round no.
        if (numberOfPlayer != 0) {
            titleLabel = createLabel("ROUND 1", 53);
            titleLabel.setBounds(14, 20, 239, 50);
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            add(titleLabel);
        }
        drawStatPanel();
        drawResultPanel();
    }

    private void drawStatPanel() {
        stat = new JPanel(new GridLayout(5, 1, 0, 10));
        add(stat);
        stat.setBounds(14, 123, 100, 250);
        stat.setOpaque(false);//set transparent background

        stat.add(createLabel("Total guess: ", 12));
        stat.add(createLabel("Time: ", 12));
        stat.add(createLabel("Avg guess: ", 12));
        stat.add(createLabel("Avg time: ", 12));
        stat.add(createLabel("Win: ", 12));

        //add playerPanel based on numberOfPlayers
        if (numberOfPlayer == 2) {
            player1Label = createLabel("Player 1", 14);
            if (player2 instanceof PlayerAI) {
                player2Label = createLabel("Invincible", 14);
            } else {
                player2Label = createLabel("Player 2", 14);
            }
            player1Label.setBounds(103, 90, 60, 25);
            player2Label.setBounds(182, 90, 72, 25);
            add(player1Label);
            add(player2Label);
            drawPlayer1Panel();
            drawPlayer2Panel();
            player1Panel.setBounds(105, 123, 75, 250);
            player2Panel.setBounds(185, 123, 75, 250);
        } else {
            drawPlayer1Panel();
            player1Panel.setBounds(130, 123, 95, 250);
        }
    }

    private void drawPlayer1Panel() {
        player1Panel = new JPanel(new GridLayout(5, 1, 0, 10));
        add(player1Panel);
        player1Panel.setOpaque(false);//set transparent background

        addPlayerLabel(player1, player1Labels, player1Panel);
    }

    private void drawPlayer2Panel() {
        player2Panel = new JPanel(new GridLayout(5, 1, 0, 10));
        add(player2Panel);
        player2Panel.setOpaque(false);//set transparent background

        addPlayerLabel(player2, player2Labels, player2Panel);
    }

    private JLabel createLabel(String text, int size) {
        Color color = new Color(80, 80, 80);
        JLabel label = new JLabel(text);
        label.setForeground(color);
        Font f = label.getFont().deriveFont(Font.BOLD, size);
        label.setFont(f);
        return label;
    }

    private void addPlayerLabel(Player player, JLabel[] labels, JPanel playerPanel) {
        //create labels and add them to playerPanel with player details
        String avgGuess = String.format("%1.1f", player.getAvgGuess());
        String avgTime = String.format("%1.1f", player.getAvgTimeCount());

        labels[0] = createLabel("" + player.getTotalNumberOfGuess(), 26);
        labels[1] = createLabel("" + player.getTimeCount()[currentRound - 1], 26);
        labels[2] = createLabel(avgGuess, 26);
        labels[3] = createLabel(avgTime, 26);
        labels[4] = createLabel("" + player.getNumberOfWin(), 26);

        playerPanel.add(labels[0]);
        playerPanel.add(labels[1]);
        playerPanel.add(labels[2]);
        playerPanel.add(labels[3]);
        playerPanel.add(labels[4]);
    }

    private void updatePlayerLabel(Player player, JLabel[] labels) {
        String avgGuess = String.format("%1.1f", player.getAvgGuess());
        String avgTime = String.format("%1.1f", player.getAvgTimeCount());
        labels[0].setText("" + player.getTotalNumberOfGuess());
        labels[1].setText("" + player.getTimeCount()[currentRound - 1]);
        labels[2].setText(avgGuess);
        labels[3].setText(avgTime);
        labels[4].setText("" + player.getNumberOfWin());
    }

    private void drawResultPanel() {
        add(resultPanel);
        resultPanel.setBounds(18, 150, 230, 300);
        resultPanel.setVisible(false);
        resultPanel.setOpaque(false);

        resultLabels[0] = createLabel("", 65);
        resultLabels[0].setHorizontalAlignment(SwingConstants.CENTER);
        resultLabels[1] = createLabel("", 35);
        resultLabels[1].setHorizontalAlignment(SwingConstants.CENTER);
        resultPanel.add(resultLabels[0]);
        resultPanel.add(resultLabels[1]);
    }

    private void toggleResultPanelVisibility(String[] result) {
        if (!resultPanel.isVisible()) {
            resultPanel.setVisible(true);
            stat.setVisible(false);
            player1Panel.setVisible(false);
            if (numberOfPlayer == 2) {
                player1Label.setVisible(false);
                player2Panel.setVisible(false);
                player2Label.setVisible(false);
            }
            resultLabels[0].setText(result[0]);
            resultLabels[1].setText(result[1]);

            actionButton.setText(result[2]);
        } else {
            resultPanel.setVisible(false);
            actionButton.setText("Quit");
            titleLabel.setText("ROUND " + result[0]);
            currentRound = Integer.parseInt(result[0]);
            stat.setVisible(true);
            player1Panel.setVisible(true);
            if (numberOfPlayer == 2) {
                player1Label.setVisible(true);
                player2Panel.setVisible(true);
                player2Label.setVisible(true);
            }
        }
    }

    private void showFinalResult(byte winner) {
        resultPanel.setVisible(false);
        actionButton.setText("Quit");
        stat.setVisible(true);
        player1Panel.setVisible(true);
        titleLabel.setText("");

        if (numberOfPlayer == 2) {
            if (winner == 0) {//if DRAW
                titleLabel.setText("DRAW");
            } else {
                titleLabel.setText("PLAYER " + winner + " WON!");
                //resize font
                Font f = titleLabel.getFont().deriveFont(30f);
                titleLabel.setFont(f);
                titleLabel.setForeground(Color.WHITE);
            }
            player1Label.setVisible(true);
            player2Panel.setVisible(true);
            player2Label.setVisible(true);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Player) {
            Player player = (Player) arg;
            if (player.getId() == 2) {
                updatePlayerLabel(player, player2Labels);
            } else {
                updatePlayerLabel(player, player1Labels);
            }
        } else if (arg instanceof String[]) {
            toggleResultPanelVisibility((String[]) arg);
        } else if (arg instanceof Byte) {
            showFinalResult((Byte) arg);
        }
        repaint();
    }

    public void addActionButtonListener(ActionButtonListener listener) {
        actionButton.addMouseListener(listener);
    }
}
