package magic.card;

import magic.data.TokenCardDefinitions;
import magic.model.MagicCard;
import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.action.MagicPlayCardAction;
import magic.model.event.MagicEvent;
import magic.model.target.MagicTargetFilter;
import magic.model.trigger.MagicWhenAttacksTrigger;


public class Kessig_Cagebreakers {
    public static final MagicWhenAttacksTrigger T = new MagicWhenAttacksTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent creature) {
            return permanent == creature ?
                new MagicEvent(
                    permanent,
                    this,
                    "PN puts a 2/2 green Wolf creature token onto " +
                    "the battlefield tapped and attacking for each creature " +
                    "card in his or her graveyard."
                ):
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object[] choiceResults) {
            final MagicPlayer player = event.getPlayer();
            final int amount = game.filterCards(player,MagicTargetFilter.TARGET_CREATURE_CARD_FROM_GRAVEYARD).size();
            for (int count=amount;count>0;count--) {
                final MagicCard card = MagicCard.createTokenCard(TokenCardDefinitions.get("Wolf"),player);
                game.doAction(new MagicPlayCardAction(card,player,MagicPlayCardAction.TAPPED_ATTACKING));
            }
        }        
    };
}
