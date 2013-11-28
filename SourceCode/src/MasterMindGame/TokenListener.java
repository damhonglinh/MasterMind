package MasterMindGame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Dam Linh - s3372757
 */
public class TokenListener extends MouseAdapter {

    private TokenModel model = null;
    private Token lastEntered;

    public TokenListener(TokenModel model) {
        this.model = model;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Token token = (Token) e.getSource();               
        if (token.isBased()) {
            model.setColor(token.getColor());
        } else {
            model.changeTokenColor((Token) e.getSource());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        model.changeTokenColor(lastEntered);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        lastEntered = (Token) e.getSource();
    }
}
