package magic.card;

import magic.model.MagicGame;
import magic.model.MagicManaCost;
import magic.model.MagicPayedCost;
import magic.model.MagicPermanent;
import magic.model.action.MagicChangeLifeAction;
import magic.model.action.MagicPermanentAction;
import magic.model.action.MagicSacrificeAction;
import magic.model.choice.MagicTargetChoice;
import magic.model.condition.MagicCondition;
import magic.model.condition.MagicConditionFactory;
import magic.model.event.MagicActivationHints;
import magic.model.event.MagicEvent;
import magic.model.event.MagicPayManaCostEvent;
import magic.model.event.MagicPermanentActivation;
import magic.model.event.MagicTiming;
import magic.model.target.MagicSacrificeTargetPicker;

public class Disciple_of_Griselbrand {
    public static final MagicPermanentActivation A = new MagicPermanentActivation(
            new MagicCondition[]{
                    MagicConditionFactory.ManaCost("{1}"),
                    MagicCondition.ONE_CREATURE_CONDITION
            },
            new MagicActivationHints(MagicTiming.Pump),
            "Pump") {
        @Override
        public MagicEvent[] getCostEvent(final MagicPermanent source) {
            return new MagicEvent[]{
                new MagicPayManaCostEvent(source,source.getController(),MagicManaCost.create("{1}"))
            };
        }
        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                MagicTargetChoice.SACRIFICE_CREATURE,
                MagicSacrificeTargetPicker.create(),
                this,
                "Sacrifice a creature. PN gains life " +
                "equal to sacrificed creature's toughness."
            );
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event) {
            event.processTargetPermanent(game,new MagicPermanentAction() {
                public void doAction(final MagicPermanent creature) {
                    final int toughness=creature.getToughness();
                    game.doAction(new MagicSacrificeAction(creature));
                    game.doAction(new MagicChangeLifeAction(event.getPlayer(),toughness));
                }
            });
        }
    };
}
