[
    new MagicWhenSelfBecomesUntappedTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent untapped) {
            return new MagicEvent(
                permanent,
                permanent.getOpponent(),
                MagicTargetChoice.A_CARD_FROM_HAND,
                this,
                "PN discards a card\$, then puts the top card of his or her library into his or her graveyard."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetCard(game, {
                final MagicCard targetCard ->
                game.doAction(new MagicDiscardCardAction(event.getPlayer(),targetCard));
                game.doAction(new MagicMillLibraryAction(event.getPlayer(),1));
            });
        }
    }
]
