[
    new MagicStatic(MagicLayer.Game) {
        @Override
        public boolean condition(final MagicGame game,final MagicPermanent source,final MagicPermanent target) {
            return source.getController().getNrOfPermanents(MagicType.Creature) <= 1;
        }
        @Override
        public void modGame(final MagicPermanent source, final MagicGame game) {
            game.doAction(new MagicPutStateTriggerOnStackAction(
                MagicRuleEventAction.create(
                    source,
                    "Sacrifice SN."
                )
            ));
        }
    }
]
