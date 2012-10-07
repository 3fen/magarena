package magic.card;

import magic.data.TokenCardDefinitions;
import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.action.MagicPlayTokenAction;
import magic.model.event.MagicEvent;
import magic.model.target.MagicTarget;
import magic.model.target.MagicTargetFilter;
import magic.model.trigger.MagicAtUpkeepTrigger;

import java.util.Collection;

public class Endless_Ranks_of_the_Dead {
    public static final MagicAtUpkeepTrigger T = new MagicAtUpkeepTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPlayer upkeepPlayer) {
            return permanent.isController(upkeepPlayer) ?
                new MagicEvent(
                    permanent,
                    this,
                    "PN puts X 2/2 black Zombie creature tokens onto the " +
                    "battlefield, where X is half the number of Zombies you control, rounded down"
                ):
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object[] choiceResults) {
            final MagicPlayer player = event.getPlayer();
            final Collection<MagicTarget> targets =
                    game.filterTargets(player,MagicTargetFilter.TARGET_ZOMBIE_YOU_CONTROL);
            final int amount = targets.size() / 2;
            for (int count=amount;count>0;count--) {
                game.doAction(new MagicPlayTokenAction(player,TokenCardDefinitions.get("Zombie")));;
            }
        }        
    };
}
