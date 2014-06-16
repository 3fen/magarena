[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                MagicTargetChoice.NEG_TARGET_CREATURE_OR_PLAYER,
                this,
                "SN deals damage to target creature or player\$ equal to the number of attacking creatures you control."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final int amount = event.getPlayer().getNrOfPermanents(MagicTargetFilterFactory.ATTACKING_CREATURE_YOU_CONTROL);
            event.processTarget(game, {
                final MagicTarget target ->
                final MagicDamage damage = new MagicDamage(event.getSource(),target,amount);
                game.doAction(new MagicDealDamageAction(damage));
            });
        }
    }
]
