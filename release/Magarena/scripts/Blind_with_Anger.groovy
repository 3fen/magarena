[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                new MagicTargetChoice("target nonlegendary creature an opponent controls"),
                MagicExileTargetPicker.create(),
                this,
                "Untap target nonlegendary creature\$ and gain control of it until end of turn. "+
                "That creature gains haste until end of turn."
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
