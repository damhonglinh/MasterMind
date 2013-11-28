package MasterMindGame;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Observable;

/**
 *
 * @author Dam Linh - s3372757
 */
public abstract class TokenModel extends Observable {

    protected int color = Token.NO_COLOR;
    protected int numberOfColor;
    protected int numberOfCode;
    protected Token[] tokens;

    public TokenModel(int numberOfColor, int numberOfCode, Token[] tokens) {
        this.numberOfColor = numberOfColor;
        this.numberOfCode = numberOfCode;
        this.tokens = tokens;
    }

    public void setColor(int color) {
        this.color = color;
        setChanged();
        notifyObservers(createCursor(color));
    }

    public int getColor() {
        return color;
    }

    public int getNumberOfColor() {
        return numberOfColor;
    }

    public void changeTokenColor(Token token) {
        if (!token.isChangeable()) {
            return;//if token is not changeable then do nothing
        }
        token.setColor(color);
    }

    public void cancel(int currentRow) {
        for (int i = currentRow * numberOfCode; i < (currentRow + 1) * numberOfCode; i++) {
            tokens[i].setColor(Token.NO_COLOR);
        }
    }

    public abstract void ok(int currentRow);

    public boolean isAllFilled(int currentRow) {
        for (int i = currentRow * numberOfCode; i < (currentRow + 1) * numberOfCode; i++) {
            if (tokens[i].getColor() == Token.NO_COLOR) {
                return false;
            }
        }
        return true;
    }

    public Cursor createCursor(int color) {
        URL url = null;
        switch (color) {
            case Token.GREEN:
                url = this.getClass().getResource("Images/cursorImage/green.png");
                break;
            case Token.BLUE:
                url = this.getClass().getResource("Images/cursorImage/blue.png");
                break;
            case Token.RED:
                url = this.getClass().getResource("Images/cursorImage/red.png");
                break;
            case Token.YELLOW:
                url = this.getClass().getResource("Images/cursorImage/yellow.png");
                break;
            case Token.PURPLE:
                url = this.getClass().getResource("Images/cursorImage/purple.png");
                break;
            case Token.DARK_GREEN:
                url = this.getClass().getResource("Images/cursorImage/darkgreen.png");
                break;
            case Token.PINK:
                url = this.getClass().getResource("Images/cursorImage/pink.png");
                break;
            case Token.SKY:
                url = this.getClass().getResource("Images/cursorImage/sky.png");
                break;
            case Token.BLACK:
                url = this.getClass().getResource("Images/cursorImage/black.png");
                break;
            case Token.WHITE:
                url = this.getClass().getResource("Images/cursorImage/white.png");
                break;
        }
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(url);
        Cursor cursor = toolkit.createCustomCursor(image, new Point(15, 15), "C");
        return cursor;
    }
}
