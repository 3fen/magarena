[
    new MagicWhenDamageIsDealtTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicDamage damage) {
        return permanent == damage.getSource() ?
            new MagicEvent(
                permanent,
                new MagicOrChoice(
                    MagicChoice.NONE,
                    MagicChoice.NONE
                ),
                this,
                "Choose one\$ - Each player loses 1 life; or each player gains 1 life."
            ):
            MagicEvent.NONE;
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            for (final MagicPlayer player : game.getAPNAP()) {
                game.doAction(new MagicChangeLifeAction(player, event.isMode(1) ? -1 : +1));
            }
        }
    }
]
