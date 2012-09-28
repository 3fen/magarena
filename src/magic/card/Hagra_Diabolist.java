package magic.card;

import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.MagicSubType;
import magic.model.action.MagicChangeLifeAction;
import magic.model.action.MagicPlayerAction;
import magic.model.choice.MagicMayChoice;
import magic.model.choice.MagicTargetChoice;
import magic.model.event.MagicEvent;
import magic.model.trigger.MagicWhenOtherComesIntoPlayTrigger;

public class Hagra_Diabolist {
    public static final MagicWhenOtherComesIntoPlayTrigger T = new MagicWhenOtherComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent otherPermanent) {
            return (otherPermanent.isFriend(permanent) &&
                    otherPermanent.hasSubType(MagicSubType.Ally)) ?
                new MagicEvent(
                    permanent,
                    new MagicMayChoice(
                        MagicTargetChoice.NEG_TARGET_PLAYER
                    ),
                    this,
                    "PN may$ have target player$ lose life " +
                    "equal to the number of Allies he or she controls."
                ) :
                MagicEvent.NONE;
        }
        
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object[] data,
                final Object[] choiceResults) {
            if (MagicMayChoice.isYesChoice(choiceResults[0])) {
                event.processTargetPlayer(game,choiceResults,1,new MagicPlayerAction() {
                    public void doAction(final MagicPlayer targetPlayer) {
                        final MagicPlayer player = event.getPlayer();
                        final int amount =
                                player.getNrOfPermanentsWithSubType(MagicSubType.Ally);
                        if (amount > 0) {
                            game.doAction(new MagicChangeLifeAction(targetPlayer,-amount));
                        }
                    }
                });
            }            
        }        
    };
}
