package magic.card;

import magic.model.MagicCard;
import magic.model.MagicColor;
import magic.model.MagicDamage;
import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.action.MagicChangeLifeAction;
import magic.model.action.MagicDealDamageAction;
import magic.model.action.MagicPlayerAction;
import magic.model.choice.MagicTargetChoice;
import magic.model.event.MagicEvent;
import magic.model.stack.MagicCardOnStack;
import magic.model.trigger.MagicWhenOtherSpellIsCastTrigger;
import magic.model.MagicPowerToughness;
import magic.model.mstatic.MagicLayer;
import magic.model.mstatic.MagicStatic;
import magic.model.target.MagicTargetFilter;

public class Balefire_Liege {
    public static final MagicStatic S1 = new MagicStatic(
        MagicLayer.ModPT, 
        MagicTargetFilter.TARGET_RED_CREATURE_YOU_CONTROL) {
        @Override
        public void modPowerToughness(final MagicPermanent source,final MagicPermanent permanent,final MagicPowerToughness pt) {
            pt.add(1,1);
        }
        @Override
        public boolean condition(final MagicGame game,final MagicPermanent source,final MagicPermanent target) {
            return source != target;
        }
    };
    public static final MagicStatic S2 = new MagicStatic(
        MagicLayer.ModPT, 
        MagicTargetFilter.TARGET_WHITE_CREATURE_YOU_CONTROL) {
        @Override
        public void modPowerToughness(final MagicPermanent source,final MagicPermanent permanent,final MagicPowerToughness pt) {
            pt.add(1,1);
        }
        @Override
        public boolean condition(final MagicGame game,final MagicPermanent source,final MagicPermanent target) {
            return source != target;
        }
    };
    public static final MagicWhenOtherSpellIsCastTrigger T = new MagicWhenOtherSpellIsCastTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicCardOnStack cardOnStack) {
            final MagicPlayer player=permanent.getController();
            final MagicCard card=cardOnStack.getCard();
            return (card.getOwner()==player&&MagicColor.Red.hasColor(card.getColorFlags())) ?
                new MagicEvent(
                    permanent,
                    player,
                    MagicTargetChoice.NEG_TARGET_PLAYER,
                    this,
                    permanent + " deals 3 damage to target player$."):
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object data[],
                final Object[] choiceResults) {
            event.processTargetPlayer(game,choiceResults,0,new MagicPlayerAction() {
                public void doAction(final MagicPlayer player) {
                    final MagicDamage damage=new MagicDamage(event.getSource(),player,3,false);
                    game.doAction(new MagicDealDamageAction(damage));
                }
            });
        }        
    };
    
    public static final MagicWhenOtherSpellIsCastTrigger T2 = new MagicWhenOtherSpellIsCastTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicCardOnStack cardOnStack) {
            final MagicPlayer player=permanent.getController();
            final MagicCard card=cardOnStack.getCard();
            return (card.getOwner()==player&&MagicColor.White.hasColor(card.getColorFlags())) ?
                new MagicEvent(
                    permanent,
                    player,
                    this,
                    player + " gains 3 life."):
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object data[],
                final Object[] choiceResults) {
            game.doAction(new MagicChangeLifeAction(event.getPlayer(),3));
        }
    };
}
