def CREATURE_POWER_5_OR_MORE = new MagicPermanentFilterImpl() {
    public boolean accept(final MagicGame game,final MagicPlayer player,final MagicPermanent target) {
        return target.isCreature() && target.getPower() >= 5;
    }
};

[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Pump),
        "Prevent"
    ) {

        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [
                new MagicPayManaCostTapEvent(source, "{1}{W}")
            ];
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source, final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                new MagicTargetChoice(
                    CREATURE_POWER_5_OR_MORE,
                    MagicTargetHint.Positive,
                    "target creature with power 5 or greater"
                ),
                MagicPreventTargetPicker.getInstance(),
                this,
                "Prevent all damage that would be dealt to target creature\$ with power 5 or greater this turn."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, new MagicPermanentAction() {
                public void doAction(final MagicPermanent creature) {
                    game.doAction(MagicChangeStateAction.Set(
                        creature,
                        MagicPermanentState.PreventAllDamage
                    ));
                }
            });
        }
    }
]
