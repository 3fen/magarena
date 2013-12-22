[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Main),
        "Card"
    ){
        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [
                new MagicPayManaCostEvent(source,"{3}")
            ];
        }
        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                MagicTargetChoice.NEG_TARGET_CARD_FROM_ALL_GRAVEYARDS,
                MagicGraveyardTargetPicker.ExileOpp,
                this,
                "Put target card\$ from a graveyard on the bottom of its owner's library"
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetCard(game, {
                final MagicCard targetCard ->
                game.doAction(new MagicRemoveCardAction(targetCard,MagicLocationType.Graveyard));
                game.doAction(new MagicMoveCardAction(
                    targetCard,
                    MagicLocationType.Graveyard,
                    MagicLocationType.BottomOfOwnersLibrary
                ));
            });
        }
    }
]
