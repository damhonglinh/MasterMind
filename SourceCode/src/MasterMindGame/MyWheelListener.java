package MasterMindGame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;

/**
 *
 * @author Dam Linh - s3372757
 */
public class MyWheelListener extends MouseAdapter {

    private TokenModel model = null;

    public MyWheelListener(TokenModel model) {
        this.model = model;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int currentColor = model.getColor();
        int nextColor;
        if (e.getWheelRotation() < 0) {
            if (currentColor <= 1 || currentColor > model.getNumberOfColor()) {
                nextColor = model.getNumberOfColor();
            } else {
                nextColor = --currentColor;
            }
            model.setColor(nextColor);
        } else {
            if (currentColor >= model.getNumberOfColor()) {
                nextColor = 1;
            } else {
                nextColor = ++currentColor;
            }
            model.setColor(nextColor);
        }
    }
}
