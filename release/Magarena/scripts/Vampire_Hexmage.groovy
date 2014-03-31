def TARGET_PERMANENT_COUNTERS = new MagicPermanentFilterImpl() {
    public boolean accept(final MagicGame game,final MagicPlayer player,final MagicPermanent target) {
        return target.hasCounters();
    }
};

def TARGET_PERMANENT_WITH_COUNTERS = new MagicTargetChoice(
    TARGET_PERMANENT_COUNTERS,
    "target permanent with counters"
)

[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Removal),
        "Remove"
    ) {
        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [new MagicSacrificeEvent(source)];
        }
        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                TARGET_PERMANENT_WITH_COUNTERS,
                MagicCountersTargetPicker.create(),
                this,
                "Remove all counters from target permanent\$."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, {
                final MagicPermanent permanent ->
                for (final MagicCounterType counterType : permanent.getCounterTypes()) {
                    game.doAction(new MagicChangeCountersAction(
                        permanent,
                        counterType,
                        -permanent.getCounters(counterType),
                        true
                    ));
                }
            });
        }
    }
]
