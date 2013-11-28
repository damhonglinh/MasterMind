package MasterMindGame;

import java.awt.CardLayout;
import java.util.Random;
import javax.swing.JApplet;
import javax.swing.JFrame;

/**
 *
 * @author Dam Linh - s3372757
 */
public class Main extends JApplet {

    private PreGamePanel preGamePanel;
    private GetSecretCodePanel getCodePanel;
    private PlayPanel playPanel;
    private Player player1;
    private Player player2;
    private Token[] secretCode;
    private int numberOfCode = 6;
    private int numberOfRound = 10;
    private int numberOfColor = 8;
    private int playMode;
    private CardLayout card = new CardLayout();
    private BeautifulPanel mainContainer = new BeautifulPanel();
    public static Main applet;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Main app = new Main();
        Main.applet = app;
        frame.add(applet);

        app.init();
        app.start();

        frame.setTitle("Master Mind");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 564);
        frame.setResizable(false);
    }

    @Override
    public void init() {
        Main.applet = this;
        setSize(645, 535);
        add(mainContainer);
        mainContainer.setLayout(card);
    }

    @Override
    public void start() {
        if (playPanel != null) {
            resetGameData();
        }
        preGamePanel = new PreGamePanel();
        mainContainer.add(preGamePanel, "preGamePanel");
        card.show(mainContainer, "preGamePanel");

        PreGameModel model = new PreGameModel();
        model.addObserver(preGamePanel);

        PreGameListener listener = new PreGameListener(model);
        preGamePanel.addListener(listener);
    }

    public void setSecretCodeFromRandom() {
        for (int i = 0; i < secretCode.length; i++) {
            if (secretCode[i] == null) {
                secretCode[i] = new Token();
            }
            int a = new Random().nextInt(numberOfColor) + 1;
            secretCode[i].setColor(a);
        }
    }

    //invoked by InGameModel
    public void obtainSecretCodeFromPlayer() {
        int playerId = 2;
        if (playPanel != null) {
            playerId = playPanel.getModel().getCurrentPlayer().getId();
        }
        getCodePanel = new GetSecretCodePanel(secretCode, numberOfColor, playerId);
        mainContainer.add(getCodePanel, "getCodePanel");
        card.show(mainContainer, "getCodePanel");

        GetSecretCodeModel secretCodeModel = new GetSecretCodeModel(secretCode, numberOfColor);
        secretCodeModel.addObserver(getCodePanel);

        MyWheelListener myWheelListener = new MyWheelListener(secretCodeModel);
        TokenListener tokenListener = new TokenListener(secretCodeModel);
        SubmitButtonListener submitListener = new SubmitButtonListener(secretCodeModel);
        getCodePanel.addListener(tokenListener, submitListener, myWheelListener);
    }

    //invoked by GetSecretCodeModel
    public void startGuessing() {
        // if it is a completely new game
        if (playPanel == null) {
            playPanel = new PlayPanel(numberOfColor, secretCode, player1, player2);
            mainContainer.add(playPanel, "playPanel");
        } else {
            playPanel.getModel().startTime();

            //AI player starts guessing
            if (playPanel.getModel().getCurrentPlayer() instanceof PlayerAI) {
                PlayerAI tempPlayer = (PlayerAI) playPanel.getModel().getCurrentPlayer();
                tempPlayer.startGuessing();
            }
        }
        card.show(mainContainer, "playPanel");
    }

    //includes get secret code and guessing, invoked by PreGameModel
    public void startGame() {
        secretCode = new Token[numberOfCode];
        if ((player2 == null) || (player2 instanceof PlayerAI)) {
            setSecretCodeFromRandom();
            startGuessing();
        } else {
            obtainSecretCodeFromPlayer();
        }
    }

    private void resetGameData() {
        playPanel = null;
        player1 = null;
        player2 = null;
        secretCode = null;
    }

    public void setDifficulty(int numberOfCode, int numberOfColor) {
        Main.applet.numberOfCode = numberOfCode;
        Main.applet.numberOfColor = numberOfColor;
    }

    public void setQuickPlayMode() {
        playMode = 0;
    }

    public void set1PlayerMode() {
        playMode = 1;
    }

    public void set2PlayerMode() {
        playMode = 2;
    }

    public void setAIMode() {
        playMode = 3;
    }

    public void setNumberOfRound(int rounds) {
        numberOfRound = rounds;
        switch (playMode) {
            case 0:
                player1 = null;
                break;
            case 1:
                player1 = new Player(numberOfRound, 1);
                break;
            case 2:
                player1 = new Player(numberOfRound, 1);
                player2 = new Player(numberOfRound, 2);
                break;
            case 3:
                player1 = new Player(numberOfRound, 1);
                player2 = new PlayerAI(numberOfRound, 2);
                break;
        }
    }
}
