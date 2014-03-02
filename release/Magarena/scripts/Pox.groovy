[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack, final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                this,
                "Each player loses a third of his or her life, discards a " +
                "third of his or her cards, sacrifices a third of his or " +
                "her creatures, then sacrifices a third of his or her lands;" +
                " rounded up each time."
            );
        }

        private int oneThird(int value) {
            if (value <= 0) {
                // Don't lose negative life.
                return 0;
            } else {
                return (int) ((value + 2) / 3);
            }
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            for (final MagicPlayer player : game.getPlayers()) {
                game.doAction(new MagicChangeLifeAction(player,
                    -oneThird(player.getLife())
                ));
            }
            for (final MagicPlayer player : game.getPlayers()) {
                game.addEvent(new MagicDiscardEvent(
                    event.getSource(),
                    player,
                    oneThird(player.getHandSize())
                ));
            }
            for (final MagicPlayer player : game.getPlayers()) {
                final int numCreatures =
                    game.filterPermanents(player,MagicTargetFilter.TARGET_CREATURE_YOU_CONTROL).size();
                for (int i = 0; i < oneThird(numCreatures); ++i) {
                    game.addEvent(new MagicSacrificePermanentEvent(
                        event.getSource(),
                        player,
                        MagicTargetChoice.SACRIFICE_CREATURE
                    ));
                }
            }
            for (final MagicPlayer player : game.getPlayers()) {
                final int numLands =
                    game.filterPermanents(player,MagicTargetFilter.TARGET_LAND_YOU_CONTROL).size();
                for (int i = 0; i < oneThird(numLands); ++i) {
                    game.addEvent(new MagicSacrificePermanentEvent(
                        event.getSource(),
                        player,
                        MagicTargetChoice.SACRIFICE_LAND
                    ));
                }
            }
        }
    }
]
