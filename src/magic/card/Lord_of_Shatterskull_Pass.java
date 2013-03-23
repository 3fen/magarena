package magic.card;

import magic.model.MagicCounterType;
import magic.model.MagicDamage;
import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.MagicPowerToughness;
import magic.model.MagicSource;
import magic.model.action.MagicDealDamageAction;
import magic.model.event.MagicEvent;
import magic.model.mstatic.MagicLayer;
import magic.model.mstatic.MagicStatic;
import magic.model.target.MagicTargetFilter;
import magic.model.trigger.MagicWhenAttacksTrigger;

import java.util.Collection;

public class Lord_of_Shatterskull_Pass {
    public static final MagicStatic S = new MagicStatic(MagicLayer.SetPT) {
        @Override
        public void modPowerToughness(final MagicPermanent source,final MagicPermanent permanent,final MagicPowerToughness pt) {
            if (permanent.getCounters(MagicCounterType.Charge)>0) {
                pt.set(6,6);
            } 
        }        
    };
        
    public static final MagicWhenAttacksTrigger T = new MagicWhenAttacksTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent creature) {
            return (permanent==creature&&permanent.getCounters(MagicCounterType.Charge)>=6) ?
                new MagicEvent(
                        permanent,
                        this,
                        "SN deals 6 damage to each creature defending player controls."):
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event) {
            final MagicSource source=event.getPermanent();
            final MagicPlayer defendingPlayer=event.getPlayer().getOpponent();
            final Collection<MagicPermanent> creatures=
                game.filterPermanents(defendingPlayer,MagicTargetFilter.TARGET_CREATURE_YOU_CONTROL);
            for (final MagicPermanent creature : creatures) {
                final MagicDamage damage=new MagicDamage(source,creature,6);
                game.doAction(new MagicDealDamageAction(damage));
            }
        }
    };
}
