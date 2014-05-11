package magic.ui.screen;

import magic.MagicMain;
import magic.MagicUtility;
import magic.data.CardDefinitions;
import magic.data.IconImages;
import magic.data.MagicSetDefinitions;
import magic.ui.DownloadImagesDialog;
import magic.ui.ExplorerPanel;
import magic.ui.MagicFrame;
import magic.ui.ScreenOptionsOverlay;
import magic.ui.screen.interfaces.IActionBar;
import magic.ui.screen.interfaces.IOptionsMenu;
import magic.ui.screen.interfaces.IStatusBar;
import magic.ui.screen.widget.ActionBarButton;
import magic.ui.screen.widget.MenuButton;
import magic.ui.screen.widget.MenuPanel;

import javax.swing.AbstractAction;
import javax.swing.JPanel;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class CardExplorerScreen
    extends AbstractScreen
    implements IStatusBar, IActionBar, IOptionsMenu {

    public CardExplorerScreen() {
        setContent(new ExplorerPanel());
    }

    /* (non-Javadoc)
     * @see magic.ui.IMagStatusBar#getScreenCaption()
     */
    @Override
    public String getScreenCaption() {
        return "Card Explorer";
    }

    /* (non-Javadoc)
     * @see magic.ui.IMagActionBar#getLeftAction()
     */
    @Override
    public MenuButton getLeftAction() {
        return MenuButton.getCloseScreenButton("Close");
    }

    /* (non-Javadoc)
     * @see magic.ui.IMagActionBar#getRightAction()
     */
    @Override
    public MenuButton getRightAction() {
        return null;
    }

    /* (non-Javadoc)
     * @see magic.ui.IMagActionBar#getMiddleActions()
     */
    @Override
    public List<MenuButton> getMiddleActions() {
        if (MagicUtility.isDevMode() || MagicUtility.isDebugMode()) {
            final List<MenuButton> buttons = new ArrayList<MenuButton>();
            buttons.add(
                    new ActionBarButton(
                            IconImages.SAVE_ICON,
                            "Save Missing Cards", "Creates CardsMissingInMagarena.txt which can be used by the Scripts Builder.",
                            new AbstractAction() {
                                @Override
                                public void actionPerformed(final ActionEvent e) {
                                    try {
                                        saveMissingCardsList();
                                    } catch (IOException e1) {
                                        throw new RuntimeException(e1);
                                    }
                                }
                            })
                    );
            return buttons;
        } else {
            return null;
        }
    }

    private void saveMissingCardsList() throws IOException {
        final List<String> missingCards = CardDefinitions.getMissingCardNames();
        final Path savePath = Paths.get(MagicMain.getLogsPath()).resolve("CardsMissingInMagarena.txt");
        try (final PrintWriter writer = new PrintWriter(savePath.toFile())) {
            for (final String cardName : missingCards) {
                writer.println(cardName);
            }
        }
        Desktop.getDesktop().open(new File(MagicMain.getLogsPath()));
    }

    /* (non-Javadoc)
     * @see magic.ui.MagScreen#canScreenClose()
     */
    @Override
    public boolean isScreenReadyToClose(final AbstractScreen nextScreen) {
        MagicSetDefinitions.clearLoadedSets();
        DownloadImagesDialog.clearLoadedLogs();
        return true;
    }

    /* (non-Javadoc)
     * @see magic.ui.IMagScreenOptionsMenu#showOptionsMenuOverlay()
     */
    @Override
    public void showOptionsMenuOverlay() {
        new ScreenOptions(getFrame());
    }

    private class ScreenOptions extends ScreenOptionsOverlay {

        public ScreenOptions(final MagicFrame frame) {
            super(frame);
        }

        /* (non-Javadoc)
         * @see magic.ui.ScreenOptionsOverlay#getScreenMenu()
         */
        @Override
        protected MenuPanel getScreenMenu() {
            return null;
        }

    }

    /* (non-Javadoc)
     * @see magic.ui.screen.interfaces.IStatusBar#getStatusPanel()
     */
    @Override
    public JPanel getStatusPanel() {
        return null;
    }

}
