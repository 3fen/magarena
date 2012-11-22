package magic.card;

import magic.model.MagicGame;
import magic.model.MagicLocationType;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.action.MagicPermanentAction;
import magic.model.action.MagicPlayCardAction;
import magic.model.action.MagicRemoveCardAction;
import magic.model.action.MagicRemoveFromPlayAction;
import magic.model.choice.MagicMayChoice;
import magic.model.choice.MagicTargetChoice;
import magic.model.event.MagicEvent;
import magic.model.target.MagicBounceTargetPicker;
import magic.model.trigger.MagicWhenComesIntoPlayTrigger;

public class Restoration_Angel {
    public static final MagicWhenComesIntoPlayTrigger T = new MagicWhenComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(
                final MagicGame game,
                final MagicPermanent permanent,
                final MagicPlayer player) {
            return new MagicEvent(
                permanent,
                new MagicMayChoice(
                    MagicTargetChoice.TARGET_NON_ANGEL_CREATURE_YOU_CONTROL
                ),
                MagicBounceTargetPicker.getInstance(),
                this,
                "You may$ exile target non-Angel creature$ you control, then return " +
                "that card to the battlefield under your control."
            );
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object[] choiceResults) {
            if (MagicMayChoice.isYesChoice(choiceResults[0])) {
                event.processTargetPermanent(game,choiceResults,1,new MagicPermanentAction() {
                    public void doAction(final MagicPermanent creature) {
                        game.doAction(new MagicRemoveFromPlayAction(
                            creature,
                            MagicLocationType.Exile
                        ));
                        final MagicRemoveCardAction removeCard = new MagicRemoveCardAction(creature.getCard(), MagicLocationType.Exile);
                        game.doAction(removeCard);
                        if (removeCard.isValid()) {
                            game.doAction(new MagicPlayCardAction(
                                creature.getCard(),
                                event.getPlayer(),
                                MagicPlayCardAction.NONE
                            ));
                        }
                    }
                });
            }
        }
    };
}
