def tappedPower = {
    final MagicTargetFilter<MagicPermanent> filter ->
    new MagicStatic(MagicLayer.ModPT, filter) {
        @Override
        public void modPowerToughness(final MagicPermanent source, final MagicPermanent permanent, final MagicPowerToughness pt) {
            pt.add(2,2);
        }
        @Override
        public boolean condition(final MagicGame game,final MagicPermanent source,final MagicPermanent target) {
            if (source.isUntapped()) {
                //remove this static after the update
                game.addDelayedAction(new MagicRemoveStaticAction(source, this));
                return false;
            } else {
                return true;
            }
        }
    };
}

def tappedAbility = {
    final MagicTargetFilter<MagicPermanent> filter ->
    new MagicStatic(MagicLayer.Ability, filter) {
        @Override
        public void modAbilityFlags(final MagicPermanent source,final MagicPermanent permanent,final Set<MagicAbility> flags) {
            permanent.addAbility(MagicAbility.Vigilance, flags);
        }
        @Override
        public boolean condition(final MagicGame game,final MagicPermanent source,final MagicPermanent target) {
            if (source.isUntapped()) {
                //remove this static after the update
                game.addDelayedAction(new MagicRemoveStaticAction(source, this));
                return false;
            } else {
                return true;
            }
        }
    };
}

def choice = MagicTargetChoice.Positive("target soldier creature");

[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Pump),
        "Pump"
    ) {
        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [
                new MagicTapEvent(source), new MagicPayManaCostEvent(source, "{2}{W}")
            ];
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source, final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                choice,
                MagicPumpTargetPicker.create(),
                this,
                "Target Soldier creature\$ gets +2/+2 and has vigilance for as long as SN remains tapped."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, {
                final MagicPermanent source = event.getPermanent();
                final MagicTargetFilter<MagicPermanent> filter = new MagicPermanentTargetFilter(it);
                game.doAction(new MagicAddStaticAction(source, tappedPower(filter)));
                game.doAction(new MagicAddStaticAction(source, tappedAbility(filter)));
            });
        }
    }
]
