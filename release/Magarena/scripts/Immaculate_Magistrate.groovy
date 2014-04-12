[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Pump),
        "Pump"
    ) {
        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [
                new MagicTapEvent(source)
            ];
        }
        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                MagicTargetChoice.Positive("target creature"),
                this,
                "Put a +1/+1 counter on target creature\$ for each Elf you control."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, {
                final MagicPermanent target ->
                final int amount = event.getPlayer().getNrOfPermanents(MagicSubType.Elf);
                game.doAction(new MagicChangeCountersAction(
                    target,
                    MagicCounterType.PlusOne,
                    amount,
                    true
                ));
            });
        }
    }
]
