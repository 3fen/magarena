[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Removal),
        "Switch"
    ) {

        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [
                new MagicPayManaCostEvent(source, "{1}{R}")
            ];
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source, final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                this,
                "Switch each creature's power and toughness until end of turn."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final Collection<MagicPermanent> targets=
                game.filterPermanents(event.getPlayer(),MagicTargetFilterFactory.CREATURE);
            for (final MagicPermanent creature : targets) {
                game.doAction(new MagicAddStaticAction(creature,MagicStatic.SwitchPT));
            }
        }
    }
]
