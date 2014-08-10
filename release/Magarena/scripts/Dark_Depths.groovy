def action = {
    final MagicGame game, final MagicEvent event ->
    game.doAction(new MagicSacrificeAction(event.getPermanent()));
    game.doAction(new MagicPlayTokenAction(
        event.getPlayer(), 
        TokenCardDefinitions.get("legendary 20/20 black Avatar creature token with flying and indestructible named Marit Lage")
    ));
}

[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Token),
        "Token"
    ) {
        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [new MagicPayManaCostEvent(source,"{3}")];
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                this,
                "Remove an ice counter from SN."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            game.doAction(new MagicChangeCountersAction(event.getPermanent(),MagicCounterType.Ice,-1));            
        }
    },
    new MagicStatic(MagicLayer.Game) {
        @Override
        public boolean condition(final MagicGame game,final MagicPermanent source,final MagicPermanent target) {
            return source.getCounters(MagicCounterType.Ice) == 0;
        }
        @Override
        public void modGame(final MagicPermanent source, final MagicGame game) {
            game.doAction(new MagicPutStateTriggerOnStackAction(new MagicEvent(
                source,
                action,
                "Sacrifice SN and put a legendary 20/20 black Avatar creature token with flying and indestructible named Marit Lage onto the battlefield."
            )));
        }
    }
]
