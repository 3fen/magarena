package magic.card;

import magic.model.MagicAbility;
import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.action.MagicChangeTurnPTAction;
import magic.model.event.MagicEvent;
import magic.model.target.MagicTarget;
import magic.model.target.MagicTargetFilter;
import magic.model.trigger.MagicWhenAttacksTrigger;

import java.util.Collection;

public class Telim_Tor {
    public static final MagicWhenAttacksTrigger T = new MagicWhenAttacksTrigger() {
        @Override
        public MagicEvent executeTrigger(
                final MagicGame game,
                final MagicPermanent permanent,
                final MagicPermanent data) {
            final MagicPlayer player = permanent.getController();
            return (permanent == data) ?
                new MagicEvent(
                    permanent,
                    player,
                    this,
                    "Attacking creatures with flanking get +1/+1 until end of turn."):
                MagicEvent.NONE;
        }
        
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object data[],
                final Object[] choiceResults) {
            final Collection<MagicTarget> targets = game.filterTargets(
                    event.getPlayer(),
                    MagicTargetFilter.TARGET_ATTACKING_CREATURE);
            for (final MagicTarget target : targets) {
                final MagicPermanent creature = (MagicPermanent)target;
                if (creature.hasAbility(MagicAbility.Flanking)) {
                    game.doAction(new MagicChangeTurnPTAction(creature,1,1));    
                }
            }
        }        
    };
}
