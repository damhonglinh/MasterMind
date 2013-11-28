package MasterMindGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Dam Linh - s3372757
 */
public class GetSecretCodePanel extends JPanel implements Observer {

    private Token[] secretCode;
    private Token[] colorTokens;
    private JPanel centerContainer = new JPanel();
    private BeautifulButton okButton = new OkButton(0);
    private BeautifulButton cancelButton = new CancelButton(0);
    private JPanel colorPanel;
    private JPanel secretCodePanel;

    public GetSecretCodePanel(Token[] secretCode, int numberOfColor, int playerId) {
        int numberOfCode = secretCode.length;

        setLayout(new BorderLayout());
        setOpaque(false);
        add(centerContainer);
        centerContainer.setLayout(new GridLayout(4, 1));
        centerContainer.setOpaque(false);
        centerContainer.add(new JLabel());//add nothing
        
        secretCodePanel = new JPanel(new GridLayout(1, numberOfCode));
        colorPanel = new JPanel(new GridLayout(2, numberOfColor));

        this.secretCode = secretCode;
        colorTokens = new Token[numberOfColor];

        drawTitlePanel(playerId);
        drawColorPanel();
        drawSecretCodePanel();
        drawActionPanel();
    }

    private void drawTitlePanel(int playerId) {
        String text = "Player " + playerId + " challenging";
        Color color = new Color(80, 80, 80);
        JLabel label = new JLabel(text);
        label.setForeground(color);
        Font f = label.getFont().deriveFont(Font.BOLD, 62);
        label.setFont(f);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);
    }

    private void drawSecretCodePanel() {
        secretCodePanel.setOpaque(false);
        centerContainer.add(secretCodePanel);

        for (int i = 0; i < secretCode.length; i++) {
            if (secretCode[i] == null) {
                secretCode[i] = new Token();
            }
            secretCode[i].setChangeable(true);
            JPanel subColorPanel = new JPanel();//to pack the area of the label to the size of the icon
            subColorPanel.setOpaque(false);
            subColorPanel.add(secretCode[i]);
            secretCodePanel.add(subColorPanel);
            secretCode[i].setColor(Token.NO_COLOR);
        }
    }

    private void drawColorPanel() {
        colorPanel.setOpaque(false);
        centerContainer.add(colorPanel);

        for (int i = 0; i < colorTokens.length; i++) {
            colorTokens[i] = new Token(i + 1);
            JPanel subColorPanel = new JPanel();//to pack the area of the label to the size of the icon
            subColorPanel.setOpaque(false);
            subColorPanel.add(colorTokens[i]);
            colorPanel.add(subColorPanel);
        }
    }

    private void drawActionPanel() {
        JPanel actionPanel = new JPanel(new GridLayout(1, 2));
        actionPanel.setOpaque(false);
        centerContainer.add(actionPanel);

        JPanel subOkPanel = new JPanel();
        subOkPanel.add(okButton);
        subOkPanel.setOpaque(false);
        okButton.setBorder(null);//to pack the area
        actionPanel.add(subOkPanel);

        JPanel subCancelPanel = new JPanel();
        subCancelPanel.add(cancelButton);
        subCancelPanel.setOpaque(false);
        cancelButton.setBorder(null);//to pack the area
        actionPanel.add(subCancelPanel);

    }

    public void addListener(TokenListener tokenListener,
            SubmitButtonListener submitButtonListener,
            MyWheelListener mywheelListener) {

        for (int i = 0; i < secretCode.length; i++) {
            secretCode[i].addMouseListener(tokenListener);
            secretCode[i].addMouseMotionListener(tokenListener);
        }
        for (int i = 0; i < colorTokens.length; i++) {
            colorTokens[i].addMouseListener(tokenListener);
            colorTokens[i].addMouseMotionListener(tokenListener);
        }
        okButton.addMouseListener(submitButtonListener);
        cancelButton.addMouseListener(submitButtonListener);

        secretCodePanel.addMouseWheelListener(mywheelListener);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Cursor) {
            secretCodePanel.setCursor((Cursor) arg);
            repaint();
        }
    }
}
