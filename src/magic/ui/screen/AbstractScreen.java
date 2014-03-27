package magic.ui.screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import magic.MagicMain;
import magic.data.IconImages;
import magic.ui.MagicFrame;
import magic.ui.screen.interfaces.IActionBar;
import magic.ui.screen.interfaces.IOptionsMenu;
import magic.ui.screen.interfaces.IStatusBar;
import magic.ui.screen.widget.ActionBar;
import magic.ui.screen.widget.StatusBar;
import magic.ui.widget.FontsAndBorders;
import magic.ui.widget.TexturedPanel;
import net.miginfocom.swing.MigLayout;

/**
 * Base class for a screen ensuring consistent style and layout.
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractScreen extends JPanel {

    private JPanel content;
    private final MagicFrame frame;
    private ActionBar actionbar;

    // CTR
    public AbstractScreen() {
        this.frame = MagicMain.rootFrame;
        setBusy(true);
        setOpaque(false);
        setEscapeKeyInputMap();
    }

    protected void refreshActionBar() {
        actionbar.refreshLayout();
    }

    protected void setContent(final JPanel content) {
        this.content = content;
        doMigLayout();
        revalidate();
        repaint();
        setBusy(false);
    }

    private void doMigLayout() {
        removeAll();
        setLayout(new MigLayout("insets 0, gap 0, flowy"));
        layoutMagStatusBar();
        add(this.content, "w 100%, h 100%");
        layoutMagActionBar();
    }

    private void layoutMagStatusBar() {
        if (hasStatusBar()) {
            add(new StatusBar(this), "w 100%");
        }
    }

    private void layoutMagActionBar() {
        if (hasActionBar()) {
            this.actionbar = new ActionBar((IActionBar)this);
            add(actionbar, "w 100%");
        } else if (!(this instanceof DuelGameScreen)) {
            add(getKeysStrip(), "w 100%");
        }
    }

    private void setEscapeKeyInputMap() {
        getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "OptionsMenu");
        getActionMap().put("OptionsMenu", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                showOptionsMenuOrCloseScreen();
            }
        });
    }

    private void showOptionsMenuOrCloseScreen() {
        if (this.hasOptionsMenu()) {
            ((IOptionsMenu)this).showOptionsMenuOverlay();
        } else {
            frame.closeActiveScreen(true);
        }
    }

    public boolean hasOptionsMenu() {
       return this instanceof IOptionsMenu;
    };

    private boolean hasActionBar() {
        return this instanceof IActionBar;
    }

    private boolean hasStatusBar() {
        return this instanceof IStatusBar;
    }

    /**
     * Gives the active screen the chance to prevent closing.
     */
    public abstract boolean isScreenReadyToClose(final AbstractScreen nextScreen);

    protected MagicFrame getFrame() {
        return frame;
    }

    public void setBusy(final boolean isBusy) {
        if (isBusy) {
          final ImageIcon ii = IconImages.BUSY;
          final JPanel pnl = new JPanel(new MigLayout("insets 0, gap 0"));
          pnl.setOpaque(false);
          final JLabel lbl = new JLabel(ii);
          lbl.setHorizontalAlignment(SwingConstants.CENTER);
          lbl.setOpaque(false);
          pnl.add(lbl, "w 100%, h 100%");
          frame.setGlassPane(pnl);
          pnl.setVisible(true);
      } else {
          frame.getGlassPane().setVisible(false);
      }

    }

    private JPanel getKeysStrip() {
        final JPanel p = new TexturedPanel();
        p.setPreferredSize(new Dimension(0, 20));
        p.setLayout(new MigLayout("gap 14, insets 2 6 2 6, center"));
        p.setBackground(FontsAndBorders.MENUPANEL_COLOR);
        p.add(getLabel("F10: Screenshot"));
        p.add(getLabel("F11: Fullscreen"));
        p.add(getLabel("F12: Background"));
        p.add(getLabel("ESC: Options / Close"));
        return p;
    }

    private JLabel getLabel(final String text) {
        final JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.LIGHT_GRAY.darker());
        return lbl;
    }

}
