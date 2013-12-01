def action = {
    final MagicGame game, final MagicEvent event ->
    event.processTargetPermanent(game, {
        final MagicPermanent permanent ->
        game.doAction(new MagicSacrificeAction(permanent));
        final int toughness = permanent.getToughness();
        game.doAction(new MagicChangeLifeAction(event.getRefPlayer(),toughness));
    });
}

[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                MagicTargetChoice.TARGET_OPPONENT,
                this,
                "Target opponent\$ sacrifices a creature. " +
                "PN gains life equal to that creature's toughness."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPlayer(game, {
                final MagicPlayer opponent ->
                game.addEvent(new MagicEvent(
                    event.getSource(),
                    opponent,
                    MagicTargetChoice.SACRIFICE_CREATURE,
                    MagicSacrificeTargetPicker.create(),
                    event.getPlayer(),
                    action,
                    "Choose a creature to sacrifice\$."
                ));
            });
        }
    }
]
