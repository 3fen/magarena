[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack, final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                MagicTargetChoice.NEG_TARGET_LAND,
                MagicDestroyTargetPicker.Destroy,
                this,
                "Destroy target land\$. " +
                "SN deals 1 damage to each Human creature."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, {
                game.doAction(new MagicDestroyAction(it));
                final Collection<MagicPermanent> targets = game.filterPermanents(
                        event.getPlayer(),
                        MagicTargetFilterFactory.HUMAN_CREATURE);
                for (final MagicPermanent target : targets) {
                    final MagicDamage damage = new MagicDamage(
                        event.getSource(),
                        target,
                        1
                    );
                    game.doAction(new MagicDealDamageAction(damage));
                }
            });
        }
    }
]
