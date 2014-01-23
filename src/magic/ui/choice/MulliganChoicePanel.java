package magic.ui.choice;

import java.util.Collections;

import javax.swing.SwingUtilities;

import magic.MagicMain;
import magic.model.MagicCardList;
import magic.model.MagicSource;
import magic.ui.GameController;
import magic.ui.MagicFrame;

@SuppressWarnings("serial")
public class MulliganChoicePanel extends MayChoicePanel {

    public MulliganChoicePanel(
            final GameController controller,
            final MagicSource source,
            final String message,
            final MagicCardList hand) {
        super(controller, source, message);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                showMulliganScreen(hand);
            }
        });
    }

    private void showMulliganScreen(final MagicCardList hand) {
        Collections.sort(hand);
        final MagicFrame frame = (MagicFrame)MagicMain.rootFrame;
        frame.showMulliganScreen(MulliganChoicePanel.this, hand);
    }

    public void doMulliganAction(final boolean takeMulligan) {
        setYesClicked(takeMulligan);
        getGameController().actionClicked();
    }

}
