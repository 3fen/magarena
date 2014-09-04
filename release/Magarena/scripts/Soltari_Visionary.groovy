[
    new MagicWhenSelfDamagePlayerTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicDamage damage) {
            return new MagicEvent(
                permanent,
                permanent.isController(damage.getTarget()) ?
                    MagicTargetChoice.TARGET_ENCHANTMENT_YOU_CONTROL :
                    MagicTargetChoice.TARGET_ENCHANTMENT_YOUR_OPPONENT_CONTROLS,
                MagicDestroyTargetPicker.Destroy,
                this,
                "Destroy target enchantment\$."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, {
                game.doAction(new MagicDestroyAction(it));
            });
        }
    }
]
