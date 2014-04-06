[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                MagicTargetChoice.NEG_TARGET_NONLAND_PERMANENT,
                MagicDestroyTargetPicker.Destroy,
                this,
                "Destroy target nonland permanent\$ and all other permanents with the same name as that permanent."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, {
                final MagicPermanent targetPermanent ->
                final MagicTargetFilter<MagicPermanent> targetFilter =
                    new MagicNameTargetFilter(targetPermanent.getName());
                final Collection<MagicPermanent> targets =
                    game.filterPermanents(event.getPlayer(),targetFilter);
                for (final MagicPermanent target : targets) {
                    game.doAction(new MagicDestroyAction(target));
                }
            });
        }
    }
]
