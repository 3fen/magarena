package magic.card;

import magic.model.MagicCard;
import magic.model.MagicCardList;
import magic.model.MagicGame;
import magic.model.MagicLocationType;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.action.MagicDestroyAction;
import magic.model.action.MagicMoveCardAction;
import magic.model.action.MagicPermanentAction;
import magic.model.action.MagicRemoveCardAction;
import magic.model.choice.MagicTargetChoice;
import magic.model.event.MagicEvent;
import magic.model.stack.MagicCardOnStack;
import magic.model.target.MagicDestroyTargetPicker;
import magic.model.trigger.MagicGraveyardTriggerData;
import magic.model.trigger.MagicWhenPutIntoGraveyardTrigger;
import magic.model.trigger.MagicWhenSpellIsCastTrigger;

public class Ulamog__the_Infinite_Gyre {
    public static final MagicWhenSpellIsCastTrigger T1 = new MagicWhenSpellIsCastTrigger() {
        @Override
        public MagicEvent executeTrigger(
                final MagicGame game,
                final MagicPermanent permanent,
                final MagicCardOnStack spell) {
            return new MagicEvent(
                spell,
                MagicTargetChoice.TARGET_PERMANENT,
                new MagicDestroyTargetPicker(false),
                this,
                "Destroy target permanent$."
            );
        }
        
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object data[],
                final Object[] choiceResults) {
            event.processTargetPermanent(game,choiceResults,0,new MagicPermanentAction() {
                public void doAction(final MagicPermanent creature) {
                    game.doAction(new MagicDestroyAction(creature));
                }
            });
        }
    };
    
    public static final MagicWhenPutIntoGraveyardTrigger T2 = new MagicWhenPutIntoGraveyardTrigger() {
        @Override
        public MagicEvent executeTrigger(
                final MagicGame game,
                final MagicPermanent permanent,
                final MagicGraveyardTriggerData triggerData) {
            final MagicPlayer owner = triggerData.card.getOwner();
            return new MagicEvent(
                //source may be permanent if on battlefield or card (exile, hand)
                permanent.isValid() ? permanent : triggerData.card,
                owner,
                this,
                "PN shuffles his or her graveyard into his or her library."
            );
        }
        
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object data[],
                final Object[] choiceResults) {
            final MagicPlayer player = event.getPlayer();
            final MagicCardList graveyard = new MagicCardList(player.getGraveyard());
            for (final MagicCard card : graveyard) {
                game.doAction(new MagicRemoveCardAction(
                        card,
                        MagicLocationType.Graveyard));
                game.doAction(new MagicMoveCardAction(
                        card,
                        MagicLocationType.Graveyard,
                        MagicLocationType.OwnersLibrary));            
            }
        }
    };
}
