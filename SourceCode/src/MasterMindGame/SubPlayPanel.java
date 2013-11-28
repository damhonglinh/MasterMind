package MasterMindGame;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

/**
 *
 * @author Dam Linh - s3372757
 */
public class SubPlayPanel extends BeautifulPanel implements Observer {

    private JPanel playPanel = new JPanel(null);
    private JPanel colorPanel = new JPanel(new GridLayout(2, 3));
    private JPanel checkPanel = new JPanel(null);
    private JPanel actionPanel = new JPanel(null);
    private JPanel secretCodePanel = new JPanel(null);
    private Token[] tokens = new Token[40];
    private CheckToken[] checkTokens = new CheckToken[40];
    private Token[] colorTokens;
    private Token[] secretCode;
    private OkButton[] okButtons = new OkButton[10];
    private CancelButton[] cancelButtons = new CancelButton[10];
    private int mainPanelWidth = 385;
    private int playPanelWidth = 157;
    private int numberOfCode = 4;

    public SubPlayPanel(int numberOfCode, int numberOfColor) {
        setLayout(null);
        colorTokens = new Token[numberOfColor];
        secretCode = new Token[numberOfCode];

        if (numberOfCode == 6) {//initialize if numberOfCode is 6
            this.numberOfCode = numberOfCode;
            tokens = new Token[60];
            checkTokens = new CheckToken[60];
            playPanelWidth = 233;
            // if there are only 6 color and 6 code
            if (numberOfColor == 6) {
                //make 6 colors in one line
                colorPanel = new JPanel(new GridLayout(1, 6));
            }
        }
        drawPlayPanel();
        drawCheckPanel();
        drawColorPanel();
        drawActionPanel();
        drawSecretCodePanel();
    }

    private void drawPlayPanel() {
        int leftMargin = 60;
        if (numberOfCode == 4) {
            leftMargin = 100;
        }
        add(playPanel);
        playPanel.setBounds(leftMargin, 40, playPanelWidth, 406);
        playPanel.setOpaque(false);//set transparent background
        int x = 5;
        int y = 8;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < numberOfCode; j++) {
                tokens[i * numberOfCode + j] = new Token();
                Token t = tokens[i * numberOfCode + j];
                t.setBounds(x, y, Token.SIZE, Token.SIZE);
                playPanel.add(t);
                x += 38;
            }
            y += 40;
            x = 5;
        }
    }

    private void drawCheckPanel() {
        int checkPanelWidth = 51;
        int leftMargin = 4;
        if (numberOfCode == 4) {
            checkPanelWidth = 40;
            leftMargin = 40;
        }
        add(checkPanel);
        checkPanel.setBounds(leftMargin, 40, checkPanelWidth, 406);
        checkPanel.setOpaque(false);//set transparent background

        int x = 4;
        int y = 6;
        for (int i = 0; i < 10; i++) {
            JPanel subCheckPanel = new JPanel(new GridLayout(2, numberOfCode / 2));
            subCheckPanel.setOpaque(false);//set transparent background
            subCheckPanel.setBounds(x, y, checkPanelWidth - 6, 34);
            for (int j = 0; j < numberOfCode; j++) {
                checkTokens[i * numberOfCode + j] = new CheckToken();
                subCheckPanel.add(checkTokens[i * numberOfCode + j]);
            }
            y += 40;
            checkPanel.add(subCheckPanel);
        }
    }

    private void drawColorPanel() {
        add(colorPanel);
        colorPanel.setBounds(0, 449, mainPanelWidth - 15, 80);
        colorPanel.setOpaque(false);//set transparent background

        for (int i = 0; i < colorTokens.length; i++) {
            colorTokens[i] = new Token(i + 1);
            JPanel subColorPanel = new JPanel();//to pack the area of the label to the size of the icon
            subColorPanel.setOpaque(false);
            subColorPanel.add(colorTokens[i]);
            colorPanel.add(subColorPanel);
        }
    }

    private void drawActionPanel() {
        int leftMargin = 65;
        if (numberOfCode == 4) {
            leftMargin = 115;
        }
        add(actionPanel);
        actionPanel.setBounds(leftMargin + playPanelWidth, 40, 80, 406);
        actionPanel.setOpaque(false);//set transparent background

        int y = 8;
        for (int i = 0; i < okButtons.length; i++) {
            okButtons[i] = new OkButton(i);
            okButtons[i].setBounds(3, y, Token.SIZE, Token.SIZE);
            actionPanel.add(okButtons[i]);

            cancelButtons[i] = new CancelButton(i);
            cancelButtons[i].setBounds(Token.SIZE + 10, y, Token.SIZE, Token.SIZE);
            actionPanel.add(cancelButtons[i]);
            y += 40;
        }
    }

    private void drawSecretCodePanel() {
        int leftMargin = 60;
        if (numberOfCode == 4) {
            leftMargin = 100;
        }
        add(secretCodePanel);
        secretCodePanel.setBounds(leftMargin, 3, playPanelWidth, 36);
        secretCodePanel.setOpaque(false);//set transparent background

        //set color for secretCode
        for (int i = 0; i < secretCode.length; i++) {
            secretCode[i] = new Token(Token.HIDDEN);
        }

        int x = 5;
        int y = 3;
        for (int i = 0; i < secretCode.length; i++) {
            secretCode[i].setBounds(x, y, Token.SIZE, Token.SIZE);
            secretCodePanel.add(secretCode[i]);
            x += 38;
        }
    }

    public Token[] getTokens() {
        return tokens;
    }

    public CheckToken[] getCheckTokens() {
        return checkTokens;
    }

    public OkButton[] getOkButtons() {
        return okButtons;
    }

    public CancelButton[] getCancelButtons() {
        return cancelButtons;
    }

    public int getMainPanelWidth() {
        return mainPanelWidth;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof int[]) {
            int[] codes = (int[]) arg;
            for (int i = 0; i < secretCode.length; i++) {
                secretCode[i].setColor(codes[i]);
            }
        } else if (arg instanceof Cursor) {
            playPanel.setCursor((Cursor) arg);
        }
        repaint();
    }

    public void addListener(TokenListener tokenListener, SubmitButtonListener submitButtonListener,
            ActionButtonListener buttonListener, MyWheelListener wheelListener) {
        for (int i = 0; i < tokens.length; i++) {
            tokens[i].addMouseMotionListener(tokenListener);
            tokens[i].addMouseListener(tokenListener);
        }
        for (int i = 0; i < colorTokens.length; i++) {
            colorTokens[i].addMouseMotionListener(tokenListener);
            colorTokens[i].addMouseListener(tokenListener);
        }

        for (int i = 0; i < okButtons.length; i++) {
            okButtons[i].addMouseListener(submitButtonListener);
            cancelButtons[i].addMouseListener(submitButtonListener);
        }
        playPanel.addMouseWheelListener(wheelListener);
    }

    public void setContainerVisible(boolean visible) {
        this.getParent().setVisible(visible);
    }
}
