[
    new MagicAtEndOfTurnTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPlayer eotPlayer) {
            final Collection<MagicPermanent> targets =
                    game.filterPermanents(permanent.getController(),MagicTargetFilter.TARGET_CREATURE);
            return (targets.size() == 0) ?
                new MagicEvent(
                    permanent,
                    this,
                    "Sacrifice SN."
                ):
                MagicEvent.NONE;
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            game.doAction(new MagicSacrificeAction(event.getPermanent()));
        }
    }
]
