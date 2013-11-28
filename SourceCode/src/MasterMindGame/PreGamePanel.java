package MasterMindGame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
public class PreGamePanel extends JPanel implements Observer {

    private CardLayout cardLayout = new CardLayout();
    private JButton[] difficultyButtons = new JButton[5];
    private JButton[] gameModeButtons = new JButton[5];
    private JButton[] numberOfRoundButtons = new JButton[6];
    private JButton easy = new ActionButton("Easy");
    private JButton normal = new ActionButton("Normal");
    private JButton hard = new ActionButton("Hard");
    private JButton crazy = new ActionButton("Crazy");
    private JButton quickGame = new ActionButton("Quick Game");
    private JButton singlePlayer = new ActionButton("1 Player");
    private JButton pvp = new ActionButton("2 Player");
    private JButton pve = new ActionButton("vs Computer");
    private JButton quit = new ActionButton("Quit");
    private JButton _1Round = new ActionButton("1 Round");
    private JButton _3Round = new ActionButton("3 Rounds");
    private JButton _5Round = new ActionButton("5 Rounds");
    private JButton _7Round = new ActionButton("7 Rounds");
    private JButton _10Round = new ActionButton("10 Rounds");
    private JButton back1 = new ActionButton("Back");
    private JButton back2 = new ActionButton("Back");

    public PreGamePanel() {
        gameModeButtons[0] = quickGame;
        quickGame.setToolTipText("Play a quick game");
        gameModeButtons[1] = singlePlayer;
        singlePlayer.setToolTipText("Play game with yourself alone");
        gameModeButtons[2] = pvp;
        pvp.setToolTipText("2 players challenging each other");
        gameModeButtons[3] = pve;
        pve.setToolTipText("Play against invicible computer");
        gameModeButtons[4] = quit;
        quit.setToolTipText("Please don't quit");

        difficultyButtons[0] = easy;
        easy.setToolTipText("4 secret codes and 6 colors.");
        difficultyButtons[1] = normal;
        normal.setToolTipText("4 secret codes and 8 colors.");
        difficultyButtons[2] = hard;
        hard.setToolTipText("6 secret codes and 8 colors.");
        difficultyButtons[3] = crazy;
        crazy.setToolTipText("6 secret codes and 10 colors.");
        difficultyButtons[4] = back1;
        back1.setToolTipText("Back");

        numberOfRoundButtons[0] = _1Round;
        numberOfRoundButtons[1] = _3Round;
        numberOfRoundButtons[2] = _5Round;
        numberOfRoundButtons[3] = _7Round;
        numberOfRoundButtons[4] = _10Round;
        numberOfRoundButtons[5] = back2;

        setLayout(cardLayout);

        drawGameModePanel();
        drawDifficultyPanel();
        drawNumberOfRoundPanel();
    }

    private void drawGameModePanel() {
        JPanel gameModePanel = new BeautifulPanel();
        gameModePanel.setLayout(new GridLayout(6, 1));
        add(gameModePanel, "1");
        gameModePanel.add(createLabel("Game Mode", 50));

        for (int i = 0; i < gameModeButtons.length; i++) {
            JPanel subPanel = new JPanel();
            subPanel.setOpaque(false);
            subPanel.add(gameModeButtons[i]);
            gameModePanel.add(subPanel);
        }
        //System exit if quit button is clicked
        quit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
    }

    private void drawDifficultyPanel() {
        JPanel difficultyPanel = new BeautifulPanel();
        difficultyPanel.setLayout(new GridLayout(6, 1));
        add(difficultyPanel, "2");
        difficultyPanel.add(createLabel("Difficulty", 60));

        for (int i = 0; i < difficultyButtons.length; i++) {
            JPanel subPanel = new JPanel();
            subPanel.setOpaque(false);
            subPanel.add(difficultyButtons[i]);
            difficultyPanel.add(subPanel);
        }
        back1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.previous(PreGamePanel.this);
            }
        });
    }

    private void drawNumberOfRoundPanel() {
        JPanel numberOfRoundPanel = new BeautifulPanel();
        numberOfRoundPanel.setLayout(new GridLayout(7, 1));
        numberOfRoundPanel.add(createLabel("Rounds", 50));
        add(numberOfRoundPanel, "3");

        for (int i = 0; i < numberOfRoundButtons.length; i++) {
            JPanel subPanel = new JPanel();
            subPanel.setOpaque(false);
            subPanel.add(numberOfRoundButtons[i]);
            numberOfRoundPanel.add(subPanel);
        }
        back2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.previous(PreGamePanel.this);
            }
        });
    }

    private JLabel createLabel(String text, int size) {
        Color color = new Color(80, 80, 80);
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        Font f = label.getFont().deriveFont(Font.BOLD, size);
        label.setFont(f);
        return label;
    }

    public void addListener(MouseListener listener) {
        //last buttons in following arrays do not need to add another listener
        for (int i = 0; i < difficultyButtons.length - 1; i++) {
            difficultyButtons[i].addMouseListener(listener);
        }
        for (int i = 0; i < gameModeButtons.length - 1; i++) {
            gameModeButtons[i].addMouseListener(listener);
        }
        for (int i = 0; i < numberOfRoundButtons.length - 1; i++) {
            numberOfRoundButtons[i].addMouseListener(listener);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Boolean) {
            boolean signal = (Boolean) arg;
            if (signal) {
                cardLayout.next(this);
            }
        }
    }
}
