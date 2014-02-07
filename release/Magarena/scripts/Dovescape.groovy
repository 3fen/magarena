[
    new MagicWhenOtherSpellIsCastTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicCardOnStack cardOnStack) {
            return (cardOnStack.hasType(MagicType.Creature)) ?
                MagicEvent.NONE:
                new MagicEvent(
                    permanent,
                    cardOnStack,
                    this,
                    "Counter RN. "+cardOnStack.getCard().getController().toString()+" puts X 1/1 white and blue Bird creature tokens with flying onto the battlefield, where X is RN's converted mana cost. ("+cardOnStack.getConvertedCost()+")"
                );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            game.doAction(new MagicPlayTokensAction(event.getRefCardOnStack().getCard().getController(), TokenCardDefinitions.get("1/1 white and blue Bird creature token with flying"), event.getRefCardOnStack().getConvertedCost()));
            game.doAction(new MagicCounterItemOnStackAction(event.getRefCardOnStack()));
        }
    }
]
