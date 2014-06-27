[
    new MagicStatic(
        MagicLayer.ModPT,
        MagicTargetFilterFactory.CREATURE) {
        @Override
        public void modPowerToughness(final MagicPermanent source,final MagicPermanent permanent,final MagicPowerToughness pt) {
            final int amount = permanent.getCounters(MagicCounterType.Spore);
            pt.add(amount,amount);
        }
        @Override
        public boolean condition(final MagicGame game,final MagicPermanent source,final MagicPermanent target) {
            return target.hasSubType(MagicSubType.Fungus);
        }
    },
    
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Pump),
        "Pump"
    ) {

        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [
                new MagicPayManaCostEvent(source, "{B}{G}"),
                new MagicExileCardEvent(source, new MagicTargetChoice("a Fungus card from a graveyard"))
            ];
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source, final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                this,
                "PN puts a spore counter on each Fungus on the battlefield."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final Collection<MagicPermanent> fungi = game.filterPermanents(MagicTargetFilterFactory.FUNGUS);
            for (final MagicPermanent fungus:fungi) {
                game.doAction(new MagicChangeCountersAction(fungus,MagicCounterType.Spore,1,true));
            }
        }
    }
]
