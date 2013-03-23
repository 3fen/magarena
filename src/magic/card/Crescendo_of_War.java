package magic.card;

import magic.model.MagicCounterType;
import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.MagicPowerToughness;
import magic.model.action.MagicChangeCountersAction;
import magic.model.event.MagicEvent;
import magic.model.mstatic.MagicLayer;
import magic.model.mstatic.MagicStatic;
import magic.model.target.MagicTargetFilter;
import magic.model.trigger.MagicAtUpkeepTrigger;

public class Crescendo_of_War {
    
    
    public static final MagicStatic S1 = new MagicStatic(
            MagicLayer.ModPT, 
            MagicTargetFilter.TARGET_CREATURE_YOU_CONTROL) {
        @Override
        public void modPowerToughness(
                final MagicPermanent source,
                final MagicPermanent permanent,
                final MagicPowerToughness pt) {
            final int amount = source.getCounters(MagicCounterType.Charge);
            pt.add(amount,0);
        }
        @Override
        public boolean condition(final MagicGame game,final MagicPermanent source,final MagicPermanent target) {
            return target.isBlocking();
        }
    };
    
    public static final MagicStatic S2 = new MagicStatic(
            MagicLayer.ModPT, 
            MagicTargetFilter.TARGET_CREATURE) {
        @Override
        public void modPowerToughness(
                final MagicPermanent source,
                final MagicPermanent permanent,
                final MagicPowerToughness pt) {
            final int amount = source.getCounters(MagicCounterType.Charge);
            pt.add(amount,0);
        }
        @Override
        public boolean condition(final MagicGame game,final MagicPermanent source,final MagicPermanent target) {
            return target.isAttacking();
        }
    };
    
    public static final MagicAtUpkeepTrigger T2 = new MagicAtUpkeepTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPlayer upkeepPlayer) {
            return new MagicEvent(
                permanent,
                this,
                "PN puts a strife counter on SN.");
        }    
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event) {
            game.doAction(new MagicChangeCountersAction(event.getPermanent(),MagicCounterType.Charge,1,true));
        }
    };
}
