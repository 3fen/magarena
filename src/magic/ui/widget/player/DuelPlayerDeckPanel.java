package magic.ui.widget.player;

import magic.MagicUtility;
import magic.data.DeckType;
import magic.model.MagicColor;
import magic.model.MagicDeckProfile;
import magic.ui.MagicFrame;
import magic.ui.dialog.DeckChooserDialog;
import magic.ui.widget.FontsAndBorders;
import magic.ui.widget.TexturedPanel;
import magic.utility.MagicStyle;
import net.miginfocom.swing.MigLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * Displays the deck type and name.
 *
 */
@SuppressWarnings("serial")
public class DuelPlayerDeckPanel extends TexturedPanel {

    // ui
    private final MigLayout migLayout = new MigLayout();
    private final MagicFrame frame;
    private final JLabel deckTypeLabel = new JLabel();
    private final JLabel deckValueLabel = new JLabel();
    // properties
    private DeckType deckType = DeckType.Random;
    private String deckValue = MagicDeckProfile.ANY_THREE;

    public DuelPlayerDeckPanel(final MagicFrame frame, final MagicDeckProfile deckProfile) {
        this.frame = frame;
        setDeckType(deckProfile.getDeckType());
        setDeckValue(deckProfile.getDeckValue());
        addMouseListener(getMouseAdapter());
        setLookAndFeel();
        refreshLayout();
    }

    private void setLookAndFeel() {
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        setBackground(FontsAndBorders.MAGSCREEN_BAR_COLOR);
        setLayout(migLayout);
        // deck type label
        deckTypeLabel.setForeground(Color.WHITE);
        deckTypeLabel.setFont(new Font("Dialog", Font.ITALIC, 14));
        deckTypeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // deck value label
        deckValueLabel.setForeground(Color.WHITE);
        deckValueLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
        deckValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void refreshLayout() {
        removeAll();
        migLayout.setLayoutConstraints("flowy, insets 6 0 0 0, gap 4");
        add(deckValueLabel, "w 100%");
        add(deckTypeLabel, "w 100%");
    }


    private void setDeckType(final DeckType value) {
        deckType = value;
        deckTypeLabel.setText(deckType + " deck");
        deckValueLabel.setText(getFormattedDeckValue());
    }
    public DeckType getDeckType() {
        return deckType;
    }

    private void setDeckValue(final String value) {
        deckValue = value;
        deckValueLabel.setText(getFormattedDeckValue());
    }
    public String getDeckValue() {
        return deckValue;
    }

    private String getFormattedDeckValue() {
        if (deckType == DeckType.Random) {
            if (deckValue.equals(MagicDeckProfile.ANY_THREE)) {
                return "Any three colors";
            } else if (deckValue.equals(MagicDeckProfile.ANY_TWO)) {
                return "Any two colors";
            } else if (deckValue.equals(MagicDeckProfile.ANY_ONE)) {
                return "Any single color";
            } else if (deckValue.equals(MagicDeckProfile.ANY_DECK)) {
                return "Preconstructed";
            } else {
                if (deckValue.length() <= 3) {
                    return getVerboseColors(deckValue);
                } else {
                    // random theme deck.
                    return deckValue;
                }
            }
        } else {
            return deckValue;
        }
    }

    private String getVerboseColors(final String colorCodes) {
        String colors = "";
        for (char ch: colorCodes.toCharArray()) {
            colors += MagicColor.getColor(ch).name() + ", ";
        }
        return colors.trim().substring(0, colors.trim().length() - 1);
    }

    private MouseAdapter getMouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                MagicUtility.setBusyMouseCursor(true);
                setDeckProfile();
                MagicUtility.setBusyMouseCursor(false);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                MagicStyle.setHightlight(DuelPlayerDeckPanel.this, true);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                MagicStyle.setHightlight(DuelPlayerDeckPanel.this, false);
            }
        };
    }

    public void setDeckProfile() {
        final DeckChooserDialog dialog = new DeckChooserDialog(frame);
        if (!dialog.isCancelled()) {
            setDeckType(dialog.getDeckType());
            setDeckValue(dialog.getDeckValue());
            refreshLayout();
        }
    }

}

