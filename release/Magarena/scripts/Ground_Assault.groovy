[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                MagicTargetChoice.NEG_TARGET_CREATURE,
                this,
                "SN deals damage to target creature\$ equal to the number of lands PN controls."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicPlayer player = event.getPlayer();
            final int amount = player.getNrOfPermanents(MagicType.Land);
            event.processTargetPermanent(game, {
                final MagicPermanent creature ->
                final MagicDamage damage = new MagicDamage(
                    event.getSource(),
                    creature,
                    amount
                );
                game.doAction(new MagicDealDamageAction(damage));
            });
        }
    }
]
