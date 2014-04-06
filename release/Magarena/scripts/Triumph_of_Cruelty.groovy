[
    new MagicAtUpkeepTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game, final MagicPermanent permanent, final MagicPlayer upkeepPlayer) {
            return permanent.isController(upkeepPlayer) ?
                new MagicEvent(
                    permanent,
                    permanent.getController(),
                    permanent.getOpponent(),
                    this,
                    "RN discards a card if PN controls the creature " +
                    "with the greatest power or tied for the greatest power."
                ) :
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicPlayer player = event.getPlayer();
            final Collection<MagicPermanent> targets = game.filterPermanents(
                player,
                MagicTargetFilterFactory.TARGET_CREATURE
            );
            int highestPower = 0;
            boolean controlHighest = false;
            for (final MagicPermanent creature:targets) {
                if (creature.getPower() > highestPower) {
                    highestPower = creature.getPower();
                }
            }
            for (final MagicPermanent highCreature:targets) {
                if (highCreature.getPower() == highestPower && highCreature.isController(player)) {
                    controlHighest=true;
                    break;
                }
            }
            if (controlHighest) {
                game.addEvent(new MagicDiscardEvent(
                    event.getSource(),
                    event.getRefPlayer()
                ));
            }
        }
    }
]
