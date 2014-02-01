[
    new MagicWhenDiesTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game, final MagicPermanent permanent, final MagicPermanent died) {
            return permanent.getCounters(MagicCounterType.Charge) == 0 ?
                new MagicEvent(
                    permanent,
                    this,
                    "PN puts a 6/1 green Insect creature token with shroud onto the battlefield."
                ) :
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            game.doAction(new MagicPlayTokenAction(
                event.getPlayer(),
                TokenCardDefinitions.get("6/1 green Insect creature token with shroud")
            ));
        }
    }
]
