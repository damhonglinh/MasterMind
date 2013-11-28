package MasterMindGame;

/**
 *
 * @author Dam Linh - s3372757
 */
public class GetSecretCodeModel extends TokenModel {

    public GetSecretCodeModel(Token[] secretCodes, int numberOfColor) {
        super(numberOfColor, secretCodes.length, secretCodes);
    }

    @Override
    public void ok(int currentRow) {
        if (isAllFilled(currentRow)) {
            setChanged();
            notifyObservers(false);
            Main.applet.startGuessing();
        }
    }
}
