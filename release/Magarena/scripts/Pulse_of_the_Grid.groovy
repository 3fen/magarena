[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                this,
                "PN draw two cards, then discard a card. Then if an opponent has more cards in hand than PN, "+
                "return SN to its owner's hand."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicPlayer player = event.getPlayer();
            game.doAction(new MagicDrawAction(player,2));
            game.addEvent(new MagicDiscardEvent(event.getSource()));
            if (player.getOpponent().getHandSize() > player.getHandSize()) {
                game.doAction(new MagicChangeCardDestinationAction(event.getCardOnStack(),MagicLocationType.OwnersHand));
            }
        }
    }
]
