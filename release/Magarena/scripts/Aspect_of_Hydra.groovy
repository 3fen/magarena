[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                MagicTargetChoice.POS_TARGET_CREATURE,
                MagicPumpTargetPicker.create(),
                this,
                "Target creature\$ gets +X/+X until end of turn, where X is PN's devotion to green. ("+cardOnStack.getController().getDevotion(MagicColor.Green)+")"
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, {
                final MagicPermanent creature ->
                final int X = event.getCardOnStack().getController().getDevotion(MagicColor.Green);
                game.doAction(new MagicChangeTurnPTAction(creature,X,X));
            });
        }
    }
]
