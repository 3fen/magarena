def choice = MagicTargetChoice.Negative("target creature with power 2 or less");

[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                choice,
                MagicExileTargetPicker.create(),
                this,
                "Exile target creature\$. Its controller gains 4 life."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, {
                game.doAction(new MagicRemoveFromPlayAction(it,MagicLocationType.Exile));
                game.doAction(new MagicChangeLifeAction(it.getController(),4));
            });
        }
    }
]
