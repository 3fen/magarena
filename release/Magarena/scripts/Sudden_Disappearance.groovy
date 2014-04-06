[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack, final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                MagicTargetChoice.TARGET_PLAYER,
                this,
                "Exile all nonland permanents target player\$ controls."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPlayer(game, {
                final MagicPlayer player ->
                final Collection<MagicPermanent> targets =
                        game.filterPermanents(player,MagicTargetFilterFactory.PERMANENT_YOU_CONTROL);
                for (final MagicPermanent permanent : targets) {
                    if (!permanent.isLand()) {
                        game.doAction(new MagicExileUntilEndOfTurnAction(permanent));
                    }
                }
            });
        }
    }
]
