package magic.card;

import magic.model.MagicDamage;
import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.action.MagicDealDamageAction;
import magic.model.action.MagicTargetAction;
import magic.model.choice.MagicTargetChoice;
import magic.model.event.MagicEvent;
import magic.model.stack.MagicCardOnStack;
import magic.model.target.MagicDamageTargetPicker;
import magic.model.target.MagicTarget;
import magic.model.trigger.MagicWhenOtherSpellIsCastTrigger;


public class Kaervek_the_Merciless {
    public static final MagicWhenOtherSpellIsCastTrigger T = new MagicWhenOtherSpellIsCastTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicCardOnStack cardOnStack) {
            final MagicPlayer player=permanent.getController();
            final int damage=cardOnStack.getCardDefinition().getConvertedCost();
            return (cardOnStack.getController()!=player) ?
                new MagicEvent(
                        permanent,
                        MagicTargetChoice.NEG_TARGET_CREATURE_OR_PLAYER,
                        new MagicDamageTargetPicker(damage),
                        damage,
                        this,
                        "SN deals "+damage+" damage to target creature or player$."):
                MagicEvent.NONE;
        }
        
        @Override
        public void executeEvent(final MagicGame game,final MagicEvent event,final Object[] choiceResults) {
            event.processTarget(game,choiceResults,new MagicTargetAction() {
                public void doAction(final MagicTarget target) {
                    final MagicDamage damage=new MagicDamage(event.getSource(),target,event.getRefInt());
                    game.doAction(new MagicDealDamageAction(damage));
                }
            });
        }
    };
}
