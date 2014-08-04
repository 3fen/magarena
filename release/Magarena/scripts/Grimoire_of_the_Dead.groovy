[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Token),
        "Reanimate"
    ) {
        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [
                new MagicTapEvent(source),
                new MagicRemoveCounterEvent(source,MagicCounterType.Study,3),
                new MagicSacrificeEvent(source)
            ];
        }
        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                this,
                "Put all creature cards from all graveyards onto the " +
                "battlefield under PN's control. They're black Zombies " +
                "in addition to their other colors and types."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicPlayer player = event.getPlayer();
            final Collection<MagicCard> targets = game.filterCards(
                player,
                MagicTargetFilterFactory.CREATURE_CARD_FROM_ALL_GRAVEYARDS
            );
            for (final MagicCard card : targets) {
                game.doAction(new MagicReanimateAction(
                    card, 
                    player, 
                    [MagicPlayMod.BLACK, MagicPlayMod.ZOMBIE]
                ));
            }
        }
    }
]
