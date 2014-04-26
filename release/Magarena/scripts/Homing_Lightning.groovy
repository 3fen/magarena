[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                MagicTargetChoice.NEG_TARGET_CREATURE,
                new MagicDamageTargetPicker(4),
                this,
                "SN deals 4 damage to target creature\$ and all other creatures " +
                "with the same name as that creature."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, {
                final MagicPermanent targetCreature ->
                final MagicTargetFilter<MagicPermanent> targetFilter = new MagicNameTargetFilter(
                    MagicTargetFilterFactory.CREATURE,
                    targetCreature.getName()
                );
                final Collection<MagicPermanent> targets = game.filterPermanents(event.getPlayer(),targetFilter);
                for (final MagicPermanent creature : targets) {
                    final MagicDamage damage = new MagicDamage(event.getSource(),creature,4);
                    game.doAction(new MagicDealDamageAction(damage));
                }
            });
        }
    }
]
