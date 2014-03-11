[
    new MagicWhenComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent, final MagicPayedCost payedCost) {
            return new MagicEvent(
                permanent,
                new MagicMayChoice(
                    MagicTargetChoice.CREATURE_YOU_CONTROL
                ),
                MagicBounceTargetPicker.create(),
                this,
                "You may\$ return a creature you control to its owner's hand. " +
                "If you don't, sacrifice SN."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            if (event.isNo()) {
                game.doAction(new MagicSacrificeAction(event.getPermanent()));
            } else {
                event.processTargetPermanent(game, {
                    final MagicPermanent creature ->
                    game.doAction(new MagicRemoveFromPlayAction(creature,MagicLocationType.OwnersHand));
                });
            }
        }
    }
]
