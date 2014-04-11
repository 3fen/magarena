def action = {
    final MagicGame game, final MagicEvent event ->
    event.processTargetPermanent(game, {
        final MagicPermanent permanent ->
        game.doAction(new MagicSacrificeAction(permanent));
        if (permanent.hasSubType(MagicSubType.Goblin)){
            for (int i = 0; i < 2; i++) {
                final MagicPlayTokenAction act = new MagicPlayTokenAction(
                    event.getPlayer(),
                    TokenCardDefinitions.get("1/1 black Goblin Rogue creature token")
                );
                game.doAction(act);
                final MagicPermanent token = act.getPermanent();
                game.doAction(new MagicGainAbilityAction( token, MagicAbility.Haste ));
            }
        }
    });
}

[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                MagicTargetChoice.TARGET_PLAYER,
                this,
                "Target player\$ sacrifices a creature. " +
                "If a Goblin is sacrificed this way, that player puts two 1/1 black Goblin Rogue creature tokens onto the battlefield, " + 
                "and those tokens gain haste until end of turn."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPlayer(game, {
                final MagicPlayer player ->
                game.addEvent(new MagicEvent(
                    event.getSource(),
                    player,
                    MagicTargetChoice.SACRIFICE_CREATURE,
                    MagicSacrificeTargetPicker.create(),
                    action,
                    "Choose a creature to sacrifice\$."
                ));
            });
        }
    }
]
