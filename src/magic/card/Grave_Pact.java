package magic.card;

import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.MagicType;
import magic.model.action.MagicPlayerAction;
import magic.model.choice.MagicTargetChoice;
import magic.model.event.MagicEvent;
import magic.model.event.MagicSacrificePermanentEvent;
import magic.model.trigger.MagicWhenOtherPutIntoGraveyardFromPlayTrigger;

public class Grave_Pact {
    public static final MagicWhenOtherPutIntoGraveyardFromPlayTrigger T = new MagicWhenOtherPutIntoGraveyardFromPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent otherPermanent) {
            final MagicPlayer player = permanent.getController();
            return (otherPermanent.isCreature() &&
                    otherPermanent.getController() == player) ?
                new MagicEvent(
                    permanent,
                    player,
                    MagicTargetChoice.TARGET_OPPONENT,
                    this,
                    "Opponent$ sacrifices a creature.") :
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object data[],
                final Object[] choiceResults) {
            event.processTargetPlayer(game,choiceResults,0,new MagicPlayerAction() {
                public void doAction(final MagicPlayer opponent) {
                    if (opponent.controlsPermanentWithType(MagicType.Creature)) {
                        game.addEvent(new MagicSacrificePermanentEvent(
                            event.getSource(),
                            opponent,
                            MagicTargetChoice.SACRIFICE_CREATURE));
                    }
                }
            });
        }
    };
}
