[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.None),
        "Return"
    ) {

        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [new MagicPayManaCostEvent(source,"{2}{B}"),
                    new MagicSacrificeEvent(source)];
        }
        
        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source, final MagicPayedCost payedCost) {
            final MagicTargetChoice TARGET_OTHER_CREATURE_CARD_FROM_GRAVEYARD=new MagicTargetChoice(
                new MagicOtherCardTargetFilter(
                    MagicTargetFilter.TARGET_CREATURE_CARD_FROM_GRAVEYARD,
                    source.getCard()
                ),
                MagicTargetHint.None,
                "a creature other than "+source.getCard().getName()+" to return to hand"
            );
            return new MagicEvent(
                source,
                TARGET_OTHER_CREATURE_CARD_FROM_GRAVEYARD,
                MagicGraveyardTargetPicker.ReturnToHand,
                this,
                "Return another target creature card\$ from your graveyard to your hand."
            );
        }
        
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicPlayer player = event.getPlayer();
            event.processTargetCard(game, {
                final MagicCard card ->
                game.doAction(new MagicRemoveCardAction(card,MagicLocationType.Graveyard));
                game.doAction(new MagicMoveCardAction(card,MagicLocationType.Graveyard,MagicLocationType.OwnersHand));
            });
        }
    }
]
