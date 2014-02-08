def action = {
    final MagicGame game, final MagicEvent event ->
    if (event.isYes()) {
        event.payManaCost(game);
    } else {
        game.addEvent(new MagicBouncePermanentEvent(event.getSource(), event.getRefPermanent()));
    }
}

[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Removal),
        "Bounce"
    ) {

        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [
                new MagicTapEvent(source),
                new MagicPayManaCostEvent(source,"{1}")
            ];
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source, final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                MagicTargetChoice.TARGET_CREATURE,
                MagicBounceTargetPicker.create(),
                this,
                "Return target creature\$ to its owner's hand unless its controller pays {1}."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, {
                final MagicPermanent permanent ->
                game.addEvent(new MagicEvent(
                    event.getSource(),
                    permanent.getController(),
                    new MagicMayChoice(
                        new MagicPayManaCostChoice(MagicManaCost.create("{1}"))
                    ),
                    permanent,
                    action,
                    "PN may\$ pay {1}."
                ));
            });
        }
    }
]
