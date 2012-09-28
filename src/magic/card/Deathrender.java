package magic.card;

import magic.model.MagicCard;
import magic.model.MagicGame;
import magic.model.MagicLocationType;
import magic.model.MagicPermanent;
import magic.model.action.MagicAttachEquipmentAction;
import magic.model.action.MagicCardAction;
import magic.model.action.MagicPlayCardAction;
import magic.model.action.MagicRemoveCardAction;
import magic.model.choice.MagicMayChoice;
import magic.model.choice.MagicTargetChoice;
import magic.model.event.MagicEvent;
import magic.model.target.MagicGraveyardTargetPicker;
import magic.model.trigger.MagicWhenOtherPutIntoGraveyardFromPlayTrigger;


public class Deathrender {
    public static final MagicWhenOtherPutIntoGraveyardFromPlayTrigger T = new MagicWhenOtherPutIntoGraveyardFromPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent data) {
            return (permanent.getEquippedCreature() == data) ?
                new MagicEvent(
                    permanent,
                    new MagicMayChoice(
                        MagicTargetChoice.TARGET_CREATURE_CARD_FROM_HAND
                    ),
                    new MagicGraveyardTargetPicker(true),
                    this,
                    "PN may$ put a creature card$ from his or her hand " +
                    "onto the battlefield and attach SN to it."):
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object[] data,
                final Object[] choiceResults) {
            if (MagicMayChoice.isYesChoice(choiceResults[0])) {
                event.processTargetCard(game,choiceResults,1,new MagicCardAction() {
                    public void doAction(final MagicCard card) {
                        game.doAction(new MagicRemoveCardAction(card,MagicLocationType.OwnersHand));
                        final MagicPlayCardAction action = new MagicPlayCardAction(card,event.getPlayer(),MagicPlayCardAction.NONE);
                        game.doAction(action);
                        game.doAction(new MagicAttachEquipmentAction(event.getPermanent(),action.getPermanent()));
                    }
                });
            }
        }
    };
}
