package magic.card;

import magic.model.MagicGame;
import magic.model.MagicPayedCost;
import magic.model.MagicPermanent;
import magic.model.MagicSource;
import magic.model.action.MagicPermanentAction;
import magic.model.action.MagicUntapAction;
import magic.model.choice.MagicTargetChoice;
import magic.model.condition.MagicCondition;
import magic.model.event.MagicActivationHints;
import magic.model.event.MagicEvent;
import magic.model.event.MagicPermanentActivation;
import magic.model.event.MagicTapEvent;
import magic.model.event.MagicTiming;
import magic.model.target.MagicTapTargetPicker;

public class Arbor_Elf {
    public static final MagicPermanentActivation A = new MagicPermanentActivation(
        new MagicCondition[]{
            MagicCondition.CAN_TAP_CONDITION
        },
        new MagicActivationHints(MagicTiming.Tapping),
        "Untap"
    ) {

        @Override
        public MagicEvent[] getCostEvent(final MagicSource source) {
            return new MagicEvent[]{new MagicTapEvent((MagicPermanent)source)};
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                MagicTargetChoice.POS_TARGET_FOREST,
                new MagicTapTargetPicker(false,true),
                this,
                "Untap target Forest$."
            );
        }

        @Override
        public void executeEvent(final MagicGame game,final MagicEvent event,final Object[] choiceResults) {
            event.processTargetPermanent(game,choiceResults,0,new MagicPermanentAction() {
                public void doAction(final MagicPermanent forest) {
                    game.doAction(new MagicUntapAction(forest));
                }
            });
        }
    };
}
