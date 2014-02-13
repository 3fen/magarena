[
    new MagicWhenComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game, final MagicPermanent permanent, final MagicPayedCost payedCost) {
            return new MagicEvent(
                permanent,
                MagicTargetChoice.TARGET_OPPONENT,
                this,
                "PN loses 2 life, PN sacrifices two creatures, and target opponent\$ draws two cards."
            )
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPlayer(game, {
                final MagicPlayer opponent ->
                game.doAction(new MagicChangeLifeAction(event.getSource().getController(),-2));
                game.addEvent(new MagicSacrificePermanentEvent(
                    event.getSource(),
                    MagicTargetChoice.SACRIFICE_CREATURE
                ));
                game.addEvent(new MagicSacrificePermanentEvent(
                    event.getSource(),
                    MagicTargetChoice.SACRIFICE_CREATURE
                ));
                game.doAction(new MagicDrawAction(opponent,2));
            })
        }
    }
]
