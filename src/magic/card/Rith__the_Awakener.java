package magic.card;

import magic.data.TokenCardDefinitions;
import magic.model.MagicColor;
import magic.model.MagicDamage;
import magic.model.MagicGame;
import magic.model.MagicManaCost;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.action.MagicPlayTokenAction;
import magic.model.choice.MagicColorChoice;
import magic.model.choice.MagicMayChoice;
import magic.model.choice.MagicPayManaCostChoice;
import magic.model.event.MagicEvent;
import magic.model.target.MagicTarget;
import magic.model.target.MagicTargetFilter;
import magic.model.trigger.MagicWhenDamageIsDealtTrigger;

import java.util.Collection;

public class Rith__the_Awakener {
    public static final MagicWhenDamageIsDealtTrigger T = new MagicWhenDamageIsDealtTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicDamage damage) {
            final MagicPlayer player=permanent.getController();
            return (damage.getSource()==permanent&&damage.getTarget().isPlayer()&&damage.isCombat()) ?
                new MagicEvent(
                        permanent,
                        player,
                        new MagicMayChoice(
                            "You may pay {2}{G}.",
                            new MagicPayManaCostChoice(MagicManaCost.TWO_GREEN),
                            MagicColorChoice.MOST_INSTANCE),
                        this,
                        "You may$ pay {2}{G}$. If you do, choose a color$. "+
                        "Put a 1/1 green Saproling creature token onto the battlefield for each permanent of that color."):
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object[] choiceResults) {
            if (MagicMayChoice.isYesChoice(choiceResults[0])) {
                final MagicPlayer player=event.getPlayer();
                final MagicColor color=(MagicColor)choiceResults[2];
                final Collection<MagicPermanent> targets=
                    game.filterPermanents(player,MagicTargetFilter.TARGET_PERMANENT);
                for (final MagicPermanent permanent : targets) {
                    if (permanent.hasColor(color)) {
                        game.doAction(new MagicPlayTokenAction(player,TokenCardDefinitions.get("Saproling")));
                    }
                }
            }
        }
    };
}
