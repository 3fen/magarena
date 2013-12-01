[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Tapping),
        "Paralyze"
    ) {
        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [
                new MagicTapEvent(source), new MagicPayManaCostEvent(source, "{1}{G}")
            ];
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                MagicTargetChoice.NEG_TARGET_CREATURE,
                new MagicNoCombatTargetPicker(true,true,false),
                this,
                "Target creature\$ doesn't untap during its controller's next untap step."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, {
                final MagicPermanent creature ->
                game.doAction(MagicChangeStateAction.Set(
                    creature,
                    MagicPermanentState.DoesNotUntapDuringNext
                ));
            });
        }
    }
]
