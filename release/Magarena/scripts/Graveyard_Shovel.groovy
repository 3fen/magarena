def action = {
    final MagicGame game, final MagicEvent event ->
    event.processTargetCard(game, {
        game.doAction(new MagicRemoveCardAction(it,MagicLocationType.Graveyard));
        game.doAction(new MagicMoveCardAction(it,MagicLocationType.Graveyard,MagicLocationType.Exile));
        if (it.hasType(MagicType.Creature)) {
            game.doAction(new MagicChangeLifeAction(event.getRefPlayer(),2));
        }
    });
}

[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Main),
        "Exile"
    ) {
        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [new MagicTapEvent(source), new MagicPayManaCostEvent(source, "{2}")];
        }
        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                MagicTargetChoice.TARGET_PLAYER,
                this,
                "Target player\$ exiles a card from his or her graveyard. " +
                "If it's a creature card, PN gains 2 life."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPlayer(game, {
                final MagicPlayer targetPlayer ->
                if (targetPlayer.getGraveyard().size() > 0) {
                    final MagicPlayer player = event.getPlayer();
                    game.addEvent(new MagicEvent(
                        event.getSource(),
                        targetPlayer,
                        MagicTargetChoice.TARGET_CARD_FROM_GRAVEYARD,
                        MagicGraveyardTargetPicker.ExileOwn,
                        player,
                        action,
                        "PN exiles a card\$ from his or her graveyard."
                    ));
                }
            });
        }
    }
]
