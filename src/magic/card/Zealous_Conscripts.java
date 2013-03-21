package magic.card;

import magic.model.MagicAbility;
import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.action.MagicGainControlAction;
import magic.model.action.MagicPermanentAction;
import magic.model.action.MagicSetAbilityAction;
import magic.model.action.MagicUntapAction;
import magic.model.choice.MagicTargetChoice;
import magic.model.event.MagicEvent;
import magic.model.mstatic.MagicStatic;
import magic.model.target.MagicExileTargetPicker;
import magic.model.trigger.MagicWhenComesIntoPlayTrigger;

public class Zealous_Conscripts {
    public static final Object T = new MagicWhenComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPlayer player) {
            return new MagicEvent(
                permanent,
                MagicTargetChoice.NEG_TARGET_PERMANENT,
                MagicExileTargetPicker.create(),
                this,
                "Gain control of target permanent$ until end of turn. Untap that permanent. " +
                "It gains haste until end of turn."
            );
        }

        @Override
        public void executeEvent(final MagicGame game,final MagicEvent event,final Object[] choiceResults) {
            event.processTargetPermanent(game,new MagicPermanentAction() {
                public void doAction(final MagicPermanent perm) {
                    game.doAction(new MagicGainControlAction(event.getPlayer(),perm,MagicStatic.UntilEOT));
                    game.doAction(new MagicUntapAction(perm));
                    game.doAction(new MagicSetAbilityAction(perm,MagicAbility.Haste));
                }
            });
        }
    };

}
