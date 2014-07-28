def action = {
    final MagicGame game, final MagicEvent event ->
    event.processTargetPlayer(game, {
        game.addEvent(new MagicSacrificePermanentEvent(
            event.getSource(),
            it,
            MagicTargetChoice.SACRIFICE_CREATURE
        ));
    });
}

[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack, final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                MagicTargetChoice.TARGET_CREATURE_CARD_FROM_GRAVEYARD,
                MagicGraveyardTargetPicker.ReturnToHand,
                this,
                "Return target creature card\$ from your graveyard to your hand."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetCard(game, {
                game.doAction(new MagicRemoveCardAction(
                    it,
                    MagicLocationType.Graveyard
                ));
                game.doAction(new MagicMoveCardAction(
                    it,
                    MagicLocationType.Graveyard,
                    MagicLocationType.OwnersHand
                ));
            });
            game.doAction(new MagicPutItemOnStackAction(new MagicTriggerOnStack(new MagicEvent(
                event.getSource(),
                MagicTargetChoice.NEG_TARGET_PLAYER,
                action,
                "Target player\$ sacrifices a creature."
            ))));
        }
    }
]
