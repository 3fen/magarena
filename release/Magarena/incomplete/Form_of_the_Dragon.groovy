[
    new MagicAtEndOfTurnTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPlayer eotPlayer) {
            new MagicEvent(
                permanent,
                this,
                "PN's life total becomes 5."
            )
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            game.doAction(event.getPlayer().setLife(5));
        }
    }
]
