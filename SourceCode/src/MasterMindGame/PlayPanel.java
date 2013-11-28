package MasterMindGame;

import javax.swing.JPanel;

/**
 *
 * @author Dam Linh - s3372757
 */
public class PlayPanel extends JPanel {

    private static final int MY_HEIGHT = 561;
    private StatisticPanel statPanel;
    private SubPlayPanel subPlayPanel;
    private InGameModel model;

    public PlayPanel(int numberOfColor, Token[] secretCode, Player player1, Player player2) {
        this.setLayout(null);

        int numberOfCode = secretCode.length;

        subPlayPanel = new SubPlayPanel(numberOfCode, numberOfColor);
        subPlayPanel.setBounds(0, 0, subPlayPanel.getMainPanelWidth(), MY_HEIGHT - 26);
        add(subPlayPanel);

        statPanel = new StatisticPanel(player1, player2);
        statPanel.setBounds(subPlayPanel.getMainPanelWidth(), 0, StatisticPanel.MY_WIDTH, MY_HEIGHT - 26);
        add(statPanel);

        model = new InGameModel(subPlayPanel.getTokens(),
                subPlayPanel.getCheckTokens(),
                subPlayPanel.getCancelButtons(),
                subPlayPanel.getOkButtons(),
                numberOfColor,
                secretCode);
        model.addObserver(subPlayPanel);
        model.addObserver(statPanel);
        model.setPlayer1(player1);
        model.setPlayer2(player2);

        TokenListener tokenListener = new TokenListener(model);
        SubmitButtonListener submitButtonListener = new SubmitButtonListener(model);
        ActionButtonListener buttonListener = new ActionButtonListener(model);
        MyWheelListener wheelListener = new MyWheelListener(model);

        subPlayPanel.addListener(tokenListener, submitButtonListener, buttonListener, wheelListener);
        statPanel.addActionButtonListener(buttonListener);

        model.startTime();
    }

    public InGameModel getModel() {
        return model;
    }
}
