[
    new MagicAtUpkeepTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPlayer upkeepPlayer) {
            return permanent.isController(upkeepPlayer) && permanent.getEnchantedPermanent().getEquippedCreature().isValid() ?
                new MagicEvent(
                    permanent,
                    permanent.getEnchantedPermanent().getEquippedCreature(),
                    this,
                    "Destroy RN."
                ):
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            game.doAction(new MagicDestroyAction(event.getRefPermanent()));
        }
    }
]
