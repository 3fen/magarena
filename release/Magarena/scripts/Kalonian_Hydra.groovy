[
    new MagicWhenAttacksTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent creature) {
            return (permanent==creature) ?
                new MagicEvent(
                    permanent,
                    this,
                    "Double the number of +1/+1 counters on each creature PN controls."
                ):
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final Collection<MagicPermanent> targets = game.filterPermanents(
                    event.getPlayer(),
                    MagicTargetFilterFactory.CREATURE_YOU_CONTROL
                );
            for (final MagicPermanent creature : targets) {
                game.doAction(new MagicChangeCountersAction(creature,MagicCounterType.PlusOne,creature.getCounters(MagicCounterType.PlusOne)));
            }
        }
    }
]
