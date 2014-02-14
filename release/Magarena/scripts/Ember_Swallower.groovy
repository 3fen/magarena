[
    new MagicWhenBecomesMonstrousTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicChangeStateAction action) {
            return new MagicEvent(
                permanent,
                this,
                "Each player sacrifices 3 lands."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            for (final MagicPlayer player : game.getPlayers()) {
                game.addEvent(new MagicSacrificePermanentEvent(
                    event.getSource(),
                    player,
                    MagicTargetChoice.SACRIFICE_LAND
                ));
                game.addEvent(new MagicSacrificePermanentEvent(
                    event.getSource(),
                    player,
                    MagicTargetChoice.SACRIFICE_LAND
                ));
                game.addEvent(new MagicSacrificePermanentEvent(
                    event.getSource(),
                    player,
                    MagicTargetChoice.SACRIFICE_LAND
                ));
            }
        }
    }
]
