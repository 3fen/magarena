package magic.card;

import magic.model.MagicDamage;
import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.MagicPowerToughness;
import magic.model.MagicSource;
import magic.model.event.MagicEvent;
import magic.model.mstatic.MagicCDA;
import magic.model.trigger.MagicIfDamageWouldBeDealtTrigger;

public class Malignus {
    public static final MagicCDA CDA = new MagicCDA() {
        @Override
        public void modPowerToughness(
                final MagicGame game,
                final MagicPlayer player,
                final MagicPowerToughness pt) {
            // floor((x + 1) / 2) == ceil(x / 2)
            final int amount = (player.getOpponent().getLife() + 1) / 2;
            pt.set(amount,amount);
        }
    };
    
    public static final MagicIfDamageWouldBeDealtTrigger T = new MagicIfDamageWouldBeDealtTrigger(3) {
        @Override
        public MagicEvent executeTrigger(
                final MagicGame game,
                final MagicPermanent permanent,
                final MagicDamage damage) {
            final MagicSource source = damage.getSource();
            if (source == permanent) {
                // Generates no event or action.
                damage.setUnpreventable();
            }            
            return MagicEvent.NONE;
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object[] data,
                final Object[] choiceResults) {
        }
    };
}
