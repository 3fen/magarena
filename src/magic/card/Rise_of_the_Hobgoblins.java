package magic.card;

import magic.data.TokenCardDefinitions;
import magic.model.MagicAbility;
import magic.model.MagicColor;
import magic.model.MagicGame;
import magic.model.MagicManaCost;
import magic.model.MagicPayedCost;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.MagicSource;
import magic.model.action.MagicPlayTokenAction;
import magic.model.action.MagicSetAbilityAction;
import magic.model.choice.MagicMayChoice;
import magic.model.choice.MagicPayManaCostChoice;
import magic.model.choice.MagicPayManaCostResult;
import magic.model.condition.MagicCondition;
import magic.model.condition.MagicConditionFactory;
import magic.model.event.MagicActivationHints;
import magic.model.event.MagicEvent;
import magic.model.event.MagicPayManaCostEvent;
import magic.model.event.MagicPermanentActivation;
import magic.model.event.MagicPlayAbilityEvent;
import magic.model.event.MagicTiming;
import magic.model.target.MagicTarget;
import magic.model.target.MagicTargetFilter;
import magic.model.trigger.MagicWhenComesIntoPlayTrigger;

import java.util.Collection;

public class Rise_of_the_Hobgoblins {

    public static final MagicPermanentActivation A1 = new MagicPermanentActivation(
            new MagicCondition[]{MagicConditionFactory.ManaCost("{R/W}")},
            new MagicActivationHints(MagicTiming.Block,true,1),
            "First strike") {

        @Override
        public MagicEvent[] getCostEvent(final MagicPermanent source) {
            return new MagicEvent[]{
                new MagicPayManaCostEvent(
                    source,
                    source.getController(),
                    MagicManaCost.create("{R/W}")
                ),
                new MagicPlayAbilityEvent(source)
            };
        }

        @Override
        public MagicEvent getPermanentEvent(
                final MagicPermanent source,
                final MagicPayedCost payedCost) {
            final MagicPlayer player=source.getController();
            return new MagicEvent(
                    source,
                    player,
                    this,
                    "Red creatures and white creatures you control gain first strike until end of turn.");
        }

        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object[] choiceResults) {
            final MagicPlayer player=event.getPlayer();
            final Collection<MagicPermanent> targets=game.filterPermanents(
                    player,
                    MagicTargetFilter.TARGET_CREATURE_YOU_CONTROL);
            for (final MagicPermanent creature : targets) {
                if (creature.hasColor(MagicColor.Red) ||
                    creature.hasColor(MagicColor.White)) {
                    game.doAction(new MagicSetAbilityAction(creature,MagicAbility.FirstStrike));
                }
            }
        }
    };

    public static final MagicWhenComesIntoPlayTrigger T1 = new MagicWhenComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPlayer player) {
            return new MagicEvent(
                    permanent,
                    player,
                    new MagicMayChoice(
                        new MagicPayManaCostChoice(MagicManaCost.create("{X}"))
                    ),
                    this,
                    "You may pay$ {X}$. If you do, put X 1/1 red and white Goblin Soldier creature tokens onto the battlefield.");
        }
        
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object[] choiceResults) {
            if (event.isYes()) {
                final MagicPlayer player=event.getPlayer();
                final MagicPayManaCostResult payedManaCost=(MagicPayManaCostResult)choiceResults[1];
                for (int count=payedManaCost.getX();count>0;count--) {
                    game.doAction(new MagicPlayTokenAction(
                                player,
                                TokenCardDefinitions.get("Goblin Soldier")));
                }
            }
        }
    };
}
