[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Tapping),
        "Tap"
    ) {
        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [
                new MagicPayManaCostEvent(source, "{U}")
            ];
        }
        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source, final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                source.getEnchantedCreature(),
                this,
                "Tap RN."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            game.doAction(new MagicTapAction(event.getRefPermanent(),true));
        }
    },
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Tapping),
        "Unap"
    ) {
        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [
                new MagicPayManaCostEvent(source, "{U}")
            ];
        }
        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source, final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                source.getEnchantedCreature(),
                this,
                "Untap RN."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            game.doAction(new MagicUntapAction(event.getRefPermanent()));
        }
    }
]
