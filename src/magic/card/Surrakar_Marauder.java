package magic.card;

import magic.model.MagicAbility;
import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.action.MagicSetAbilityAction;
import magic.model.event.MagicEvent;
import magic.model.trigger.MagicLandfallTrigger;

public class Surrakar_Marauder {
    public static final MagicLandfallTrigger T = new MagicLandfallTrigger() {
        @Override
        public MagicEvent getEvent(final MagicPermanent permanent) {
            return new MagicEvent(
                    permanent,
                    permanent.getController(),
                    this,
                    permanent + " gains intimidate until end of turn.");
        }
        
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object data[],
                final Object[] choiceResults) {
            game.doAction(new MagicSetAbilityAction(
                    event.getPermanent(),
                    MagicAbility.Intimidate));
        }        
    };
}
