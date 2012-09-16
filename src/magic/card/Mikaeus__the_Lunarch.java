package magic.card;

import java.util.Collection;

import magic.model.MagicCounterType;
import magic.model.MagicGame;
import magic.model.MagicPayedCost;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.MagicSource;
import magic.model.action.MagicChangeCountersAction;
import magic.model.condition.MagicCondition;
import magic.model.event.MagicActivationHints;
import magic.model.event.MagicEvent;
import magic.model.event.MagicPermanentActivation;
import magic.model.event.MagicRemoveCounterEvent;
import magic.model.event.MagicTapEvent;
import magic.model.event.MagicTiming;
import magic.model.target.MagicTarget;
import magic.model.target.MagicTargetFilter;

public class Mikaeus__the_Lunarch {
    public static final Object ETB = Primordial_Hydra.ETB;

    public static final MagicPermanentActivation A1 = new MagicPermanentActivation(
            new MagicCondition[]{MagicCondition.CAN_TAP_CONDITION},
            new MagicActivationHints(MagicTiming.Pump),
            "Add") {

        @Override
        public MagicEvent[] getCostEvent(final MagicSource source) {
            return new MagicEvent[]{new MagicTapEvent((MagicPermanent)source)};
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            return new MagicEvent(
                    source,
                    this,
                    "Put a +1/+1 counter on SN.");
        }
        
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object[] data,
                final Object[] choiceResults) {
            game.doAction(new MagicChangeCountersAction(event.getPermanent(),MagicCounterType.PlusOne,1,true));
        }
    };
    
    public static final MagicPermanentActivation A2 = new MagicPermanentActivation( 
            new MagicCondition[]{
                MagicCondition.CAN_TAP_CONDITION,
                MagicCondition.PLUS_COUNTER_CONDITION,
            },
            new MagicActivationHints(MagicTiming.Pump),
            "Remove") {
        @Override
        public MagicEvent[] getCostEvent(final MagicSource source) {
            return new MagicEvent[]{
                new MagicTapEvent((MagicPermanent)source),
                new MagicRemoveCounterEvent((MagicPermanent)source,MagicCounterType.PlusOne,1)};
        }
        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            return new MagicEvent(
                    source,
                    this,
                    "Put a +1/+1 counter on each other creature you control.");
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object[] data,
                final Object[] choiceResults) {
            final MagicPermanent creature = event.getPermanent();
            final Collection<MagicTarget> targets =
                game.filterTargets(creature.getController(),MagicTargetFilter.TARGET_CREATURE_YOU_CONTROL);
            for (final MagicTarget target : targets) {
                if (target != creature) {
                    game.doAction(new MagicChangeCountersAction((MagicPermanent)target,MagicCounterType.PlusOne,1,true));
                }
            }
        }
    };
}
