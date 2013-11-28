package MasterMindGame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Dam Linh - s3372757
 */
public class PreGameListener extends MouseAdapter {

    PreGameModel model;

    public PreGameListener(PreGameModel model) {
        this.model = model;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof ActionButton) {
            ActionButton but = (ActionButton) e.getSource();
            String text = but.getText();
            if (text.equalsIgnoreCase("Easy")) {
                model.selecteEasy();               
            } else if (text.equalsIgnoreCase("Normal")) {
                model.selectNormal();
            } else if (text.equalsIgnoreCase("Hard")) {
                model.selectHard();
            } else if (text.equalsIgnoreCase("Crazy")) {
                model.selectCrazy();
            } else if (text.equalsIgnoreCase("Quick Game")) {
                model.selectQuickGame();
            } else if (text.equalsIgnoreCase("1 Player")) {
                model.select1PlayerMode();
            } else if (text.equalsIgnoreCase("2 Player")) {
                model.select2PlayerMode();
            } else if (text.equalsIgnoreCase("vs Computer")) {
                model.selectAIMode();
            } else {
                String[] temp = text.split(" ");
                int round = Integer.parseInt(temp[0]);
                model.selectNumberOfRound(round);               
            }
        }
    }
}
