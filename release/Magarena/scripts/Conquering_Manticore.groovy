[
    new MagicWhenComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game, final MagicPermanent permanent, final MagicPayedCost payedCost) {
            return new MagicEvent(
                permanent,
                MagicTargetChoice.TARGET_CREATURE_YOUR_OPPONENT_CONTROLS,
                MagicExileTargetPicker.create(),
                this,
                "PN gains control of target creature an opponent controls\$ until end of turn. Untap that creature. " +
                "It gains haste until end of turn."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, {
                game.doAction(new MagicGainControlAction(event.getPlayer(),it,MagicStatic.UntilEOT));
                game.doAction(new MagicUntapAction(it));
                game.doAction(new MagicGainAbilityAction(it,MagicAbility.Haste));
            });
        }
    }
]
