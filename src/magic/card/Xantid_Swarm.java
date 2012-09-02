package magic.card;

import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.MagicPlayerState;
import magic.model.action.MagicChangePlayerStateAction;
import magic.model.event.MagicEvent;
import magic.model.trigger.MagicWhenAttacksTrigger;


public class Xantid_Swarm {
    public static final MagicWhenAttacksTrigger T = new MagicWhenAttacksTrigger() {
        @Override
        public MagicEvent executeTrigger(
                final MagicGame game,
                final MagicPermanent permanent,
                final MagicPermanent creature) {
            final MagicPlayer player = permanent.getController();
            return (permanent == creature) ?
                new MagicEvent(
                        permanent,
                        player,
                        this,
                        player.getOpponent() + " can't cast spells this turn."):
                MagicEvent.NONE;           
        }
        
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object[] data,
                final Object[] choiceResults) {
            game.doAction(new MagicChangePlayerStateAction(
                    event.getPlayer().getOpponent(),
                    MagicPlayerState.CantCastSpells,
                    true));
        }
    };
}
