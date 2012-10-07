package magic.card;

import magic.model.MagicDamage;
import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.action.MagicDealDamageAction;
import magic.model.action.MagicTargetAction;
import magic.model.choice.MagicTargetChoice;
import magic.model.event.MagicEvent;
import magic.model.target.MagicDamageTargetPicker;
import magic.model.target.MagicTarget;
import magic.model.trigger.MagicWhenComesIntoPlayTrigger;

public class Forge_Devil {
    public static final MagicWhenComesIntoPlayTrigger T = new MagicWhenComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(
                final MagicGame game,
                final MagicPermanent permanent,
                final MagicPlayer player) {
            return new MagicEvent(
                permanent,
                MagicTargetChoice.TARGET_CREATURE,
                new MagicDamageTargetPicker(1),
                this,
                "SN deals 1 damage to target creature$ and 1 damage to PN."
            );
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object[] choiceResults) {
            final MagicPermanent permanent = event.getPermanent();
            event.processTarget(game,choiceResults,0,new MagicTargetAction() {
                public void doAction(final MagicTarget target) {
                    final MagicDamage damage = new MagicDamage(permanent,target,1,false);
                    game.doAction(new MagicDealDamageAction(damage));
                }
            });
            final MagicDamage damage = new MagicDamage(permanent,event.getPlayer(),1,false);
            game.doAction(new MagicDealDamageAction(damage));
        }
    };
}
