package magic.card;

import magic.model.MagicAbility;
import magic.model.MagicGame;
import magic.model.MagicManaCost;
import magic.model.MagicPayedCost;
import magic.model.MagicPermanent;
import magic.model.MagicPowerToughness;
import magic.model.MagicSubType;
import magic.model.MagicType;
import magic.model.action.MagicBecomesCreatureAction;
import magic.model.choice.MagicSimpleMayChoice;
import magic.model.condition.MagicCondition;
import magic.model.condition.MagicConditionFactory;
import magic.model.event.MagicActivationHints;
import magic.model.event.MagicEvent;
import magic.model.event.MagicPayManaCostEvent;
import magic.model.event.MagicPermanentActivation;
import magic.model.event.MagicPlayAbilityEvent;
import magic.model.event.MagicTiming;
import magic.model.mstatic.MagicLayer;
import magic.model.mstatic.MagicStatic;
import magic.model.trigger.MagicWhenOtherComesIntoPlayTrigger;

import java.util.Set;

public class Glint_Hawk_Idol {
    private static final MagicStatic PT = new MagicStatic(MagicLayer.SetPT, MagicStatic.UntilEOT) {
        @Override
        public void modPowerToughness(final MagicPermanent source,final MagicPermanent permanent,final MagicPowerToughness pt) {
            pt.set(2,2);
        }
    };
    private static final MagicStatic AB = new MagicStatic(MagicLayer.Ability, MagicStatic.UntilEOT) {
        @Override
        public void modAbilityFlags(final MagicPermanent source,final MagicPermanent permanent,final Set<MagicAbility> flags) {
            flags.add(MagicAbility.Flying);
        }
    };
    private static final MagicStatic ST = new MagicStatic(MagicLayer.Type, MagicStatic.UntilEOT) {
        @Override
        public void modSubTypeFlags(final MagicPermanent permanent,final Set<MagicSubType> flags) {
            flags.add(MagicSubType.Bird);
        }
        @Override
        public int getTypeFlags(final MagicPermanent permanent,final int flags) {
            return flags|MagicType.Creature.getMask();
        }
    };
    
    public static final MagicWhenOtherComesIntoPlayTrigger T = new MagicWhenOtherComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent otherPermanent) {
            return (otherPermanent != permanent &&
                    otherPermanent.isArtifact() && 
                    otherPermanent.isFriend(permanent)) ?
                new MagicEvent(
                    permanent,
                    new MagicSimpleMayChoice(
                        MagicSimpleMayChoice.BECOME_CREATURE,
                        0,
                        MagicSimpleMayChoice.DEFAULT_YES),
                    this,
                    "PN may$ have SN become a 2/2 Bird " +
                    "artifact creature with flying until end of turn."):
                MagicEvent.NONE;
        }
        
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event) {
            if (event.isYes()) {
                game.doAction(new MagicBecomesCreatureAction(event.getPermanent(),PT,AB,ST));
            }            
        }        
    };
    
    public static final MagicPermanentActivation A = new MagicPermanentActivation(
            new MagicCondition[]{MagicConditionFactory.ManaCost("{W}")},
            new MagicActivationHints(MagicTiming.Animate,false,1),
            "Animate") {

        @Override
        public MagicEvent[] getCostEvent(final MagicPermanent source) {
            return new MagicEvent[]{
                    new MagicPayManaCostEvent(source,"{W}"),
                    new MagicPlayAbilityEvent(source)};
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            return new MagicEvent(
                    source,
                    this, 
                    "SN becomes a 2/2 Bird artifact " +
                    "creature with flying until end of turn.");
        }

        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event) {
            game.doAction(new MagicBecomesCreatureAction(event.getPermanent(),PT,AB,ST));
        }
    };
}
