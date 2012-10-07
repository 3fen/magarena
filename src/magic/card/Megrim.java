package magic.card;

import magic.model.MagicCard;
import magic.model.MagicDamage;
import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.action.MagicDealDamageAction;
import magic.model.event.MagicEvent;
import magic.model.trigger.MagicWhenDiscardedTrigger;

public class Megrim {
    public static final MagicWhenDiscardedTrigger T = new MagicWhenDiscardedTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicCard data) {
            final MagicPlayer otherController = data.getOwner();
            final MagicPlayer player = permanent.getController();
            return (otherController != player) ?
                new MagicEvent(
                        permanent,
                        otherController,
                        this,
                        "SN deals 2 damage to PN."):
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object[] choiceResults) {
            final MagicDamage damage = new MagicDamage(
                    event.getSource(),
                    event.getPlayer(),
                    2,
                    false);
            game.doAction(new MagicDealDamageAction(damage));
        }
    };
}
