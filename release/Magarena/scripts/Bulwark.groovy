[
    new MagicAtYourUpkeepTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game, final MagicPermanent permanent, final MagicPlayer upkeepPlayer) {
                new MagicEvent(
                    permanent,
                    MagicTargetChoice.TARGET_OPPONENT,
                    this,
                    "SN deals X damage to RN, where X is the number of cards in PN's hand minus the number of cards in RN's hand."
                );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPlayer(game, {
            final int amount = event.getPlayer().getHandSize() - it.getHandSize();
            game.doAction(new MagicDealDamageAction(event.getPermanent(),it,amount));
            });
        }
    }
]
