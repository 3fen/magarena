package magic.card;

import magic.model.MagicDamage;
import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.action.MagicDealDamageAction;
import magic.model.action.MagicDrawAction;
import magic.model.action.MagicTargetAction;
import magic.model.choice.MagicTargetChoice;
import magic.model.event.MagicEvent;
import magic.model.target.MagicDamageTargetPicker;
import magic.model.target.MagicTarget;
import magic.model.trigger.MagicWhenDamageIsDealtTrigger;


public class Sword_of_Fire_and_Ice {
    public static final MagicWhenDamageIsDealtTrigger T = new MagicWhenDamageIsDealtTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicDamage damage) {
            return (damage.getSource()==permanent.getEquippedCreature()&&damage.getTarget().isPlayer()&&damage.isCombat()) ?
                new MagicEvent(
                        permanent,
                        MagicTargetChoice.NEG_TARGET_CREATURE_OR_PLAYER,
                        new MagicDamageTargetPicker(2),
                        this,
                        "SN deals 2 damage to target creature or player$ and you draw a card."):
                MagicEvent.NONE;
        }
        
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object data[],
                final Object[] choiceResults) {
            event.processTarget(game,choiceResults,0,new MagicTargetAction() {
                public void doAction(final MagicTarget target) {
                    final MagicDamage damage=new MagicDamage(event.getPermanent(),target,2,false);
                    game.doAction(new MagicDealDamageAction(damage));
                }
            });
            game.doAction(new MagicDrawAction(event.getPlayer(),1));
        }
    };
}
