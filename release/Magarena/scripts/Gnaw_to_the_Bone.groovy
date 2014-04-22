[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack, final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                this,
                "PN gains 2 life for each creature card in PN's graveyard."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicPlayer player = event.getPlayer();
            final int amount = game.filterCards(player, MagicTargetFilterFactory.CREATURE_CARD_FROM_GRAVEYARD).size();
            game.doAction(new MagicChangeLifeAction(player,2 * amount));
        }
    }
]
