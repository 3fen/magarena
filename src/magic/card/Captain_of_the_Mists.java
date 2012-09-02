package magic.card;

import magic.model.MagicGame;
import magic.model.MagicManaCost;
import magic.model.MagicPayedCost;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.MagicSource;
import magic.model.MagicSubType;
import magic.model.action.MagicPermanentAction;
import magic.model.action.MagicUntapAction;
import magic.model.choice.MagicTargetChoice;
import magic.model.condition.MagicCondition;
import magic.model.event.MagicActivationHints;
import magic.model.event.MagicEvent;
import magic.model.event.MagicPayManaCostTapEvent;
import magic.model.event.MagicPermanentActivation;
import magic.model.event.MagicTapCreatureActivation;
import magic.model.event.MagicTiming;
import magic.model.target.MagicTapTargetPicker;
import magic.model.trigger.MagicWhenOtherComesIntoPlayTrigger;

public class Captain_of_the_Mists {
    public static final MagicWhenOtherComesIntoPlayTrigger T = new MagicWhenOtherComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(
                final MagicGame game,
                final MagicPermanent permanent,
                final MagicPermanent otherPermanent) {
            final MagicPlayer player = permanent.getController();
            return (otherPermanent != permanent &&
                    otherPermanent.isCreature() && 
                    otherPermanent.getController() == player &&
                    otherPermanent.hasSubType(MagicSubType.Human)) ?
                new MagicEvent(
                    permanent,
                    player,
                    this,
                    "Untap " + permanent + ".") :
                MagicEvent.NONE;
        }
        
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object data[],
                final Object[] choiceResults) {
            game.doAction(new MagicUntapAction(event.getPermanent()));            
        }        
    };
    
    public static final MagicPermanentActivation A1 = new MagicTapCreatureActivation(
            new MagicCondition[]{
                    MagicCondition.CAN_TAP_CONDITION,
                    MagicManaCost.ONE_BLUE.getCondition()
            },
            new MagicActivationHints(MagicTiming.Tapping),
            "Tap") {
        @Override
        public MagicEvent[] getCostEvent(final MagicSource source) {
            return new MagicEvent[]{
                new MagicPayManaCostTapEvent(
                        source,
                        source.getController(),
                        MagicManaCost.ONE_BLUE)
            };
        }
    };
    
    public static final MagicPermanentActivation A2 = new MagicPermanentActivation(
            new MagicCondition[]{
                    MagicCondition.CAN_TAP_CONDITION,
                    MagicManaCost.ONE_BLUE.getCondition()
            },
            new MagicActivationHints(MagicTiming.Tapping),
            "Untap") {
        @Override
        public MagicEvent[] getCostEvent(final MagicSource source) {
            return new MagicEvent[]{
                new MagicPayManaCostTapEvent(
                        source,
                        source.getController(),
                        MagicManaCost.ONE_BLUE)
            };
        }

        @Override
        public MagicEvent getPermanentEvent(
                final MagicPermanent source,
                final MagicPayedCost payedCost) {
            return new MagicEvent(
                    source,
                    source.getController(),
                    MagicTargetChoice.POS_TARGET_CREATURE,
                    new MagicTapTargetPicker(false,true),
                    this,
                    "Untap target creature$.");
        }

        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object[] data,
                final Object[] choiceResults) {
            event.processTargetPermanent(game,choiceResults,0,new MagicPermanentAction() {
                public void doAction(final MagicPermanent creature) {
                    game.doAction(new MagicUntapAction(creature));
                }
            });
        }
    };
}
