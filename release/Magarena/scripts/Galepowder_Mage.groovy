[
    new MagicWhenSelfAttacksTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent creature) {
            return new MagicEvent(
                permanent,
                MagicTargetChoice.Other(
                    "target creature", 
                    permanent, 
                    MagicTargetHint.Negative
                ),
                MagicExileTargetPicker.create(),
                this,
                "Exile another target creature\$. Return that card to the " +
                "battlefield under its owner's control at the beginning of the next end step."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, {
                game.doAction(new MagicExileUntilEndOfTurnAction(it));
            });
        }
    }
]
