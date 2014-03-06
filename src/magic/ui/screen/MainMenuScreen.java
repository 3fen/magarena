package magic.ui.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutionException;

import magic.data.DownloadMissingFiles;
import magic.data.GeneralConfig;
import magic.model.MagicGameReport;
import magic.ui.MagicFrame;
import magic.ui.screen.widget.MenuPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

@SuppressWarnings("serial")
public class MainMenuScreen extends AbstractScreen {

    public MainMenuScreen(final MagicFrame frame) {
        setContent(getScreenContent());
        checkForMissingFiles();
    }

    private JPanel getScreenContent() {

        final JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new MigLayout("insets 0, gap 0, center, center"));

        final MenuPanel menuPanel = new MenuPanel("Main Menu");
        content.add(menuPanel);

        menuPanel.addMenuItem("New duel", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {

                getFrame().showDuelPlayersScreen();
            }
        });
        menuPanel.addMenuItem("Resume duel", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                getFrame().loadDuel();
            }
        });
        menuPanel.addMenuItem("Card explorer", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                getFrame().showCardExplorerScreen();
            }
        });
        menuPanel.addMenuItem("Deck editor", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                getFrame().showDeckEditor();
            }
        });
        menuPanel.addMenuItem("Settings", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                getFrame().showSettingsMenuScreen();
            }
        });
        menuPanel.addMenuItem("Help", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                getFrame().showHelpMenuScreen();
            }
        });
        menuPanel.addMenuItem("Quit to desktop", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                getFrame().closeActiveScreen(false);
            }
        });

        menuPanel.refreshLayout();
        return content;
    }

    /* (non-Javadoc)
     * @see magic.ui.MagScreen#isScreenReadyToClose(magic.ui.MagScreen)
     */
    @Override
    public boolean isScreenReadyToClose(final AbstractScreen nextScreen) {
        return true;
    }

    private void checkForMissingFiles() {
        final SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void> () {
            @Override
            protected Boolean doInBackground() throws Exception {
                boolean isMissingFiles = false;
                try {
                    final DownloadMissingFiles newFiles = new DownloadMissingFiles("images.txt");
                    isMissingFiles = !newFiles.isEmpty();
                } catch (NullPointerException e) {  // just in case images.txt is not found.
                    e.printStackTrace();            // does not warrant terminating IMO.
                }
                return isMissingFiles;
            }
            @Override
            protected void done() {
                try {
                    GeneralConfig.getInstance().setIsMissingFiles(get());
                    repaint();
                } catch (InterruptedException | ExecutionException e1) {
                    MagicGameReport.reportException(Thread.currentThread(), e1);
                }
            }
        };
        worker.execute();
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (GeneralConfig.getInstance().isMissingFiles()) {
            g.setFont(new Font("Dialog", Font.PLAIN, 22));
            drawStringWithOutline(g, "New images are ready to download -", 20, 30);
            g.setFont(new Font("Dialog", Font.PLAIN, 18));
            drawStringWithOutline(g, "Settings >> Download Images", 20, 56);
        }
    }

    private void drawStringWithOutline(final Graphics g, final String str, int x, int y) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(Color.BLACK);
        for (int i = 1; i <= 1; i++) {
            g.drawString(str,x+i,y);
            g.drawString(str,x-i,y);
            g.drawString(str,x,y+i);
            g.drawString(str,x,y-i);
        }
        g.setColor(Color.WHITE);
        g.drawString(str,x,y);
    }

}
