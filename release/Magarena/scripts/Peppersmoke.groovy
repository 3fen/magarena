[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                MagicTargetChoice.NEG_TARGET_CREATURE,
                new MagicWeakenTargetPicker(1, 1),
                this,
                "Target creature\$ gets -1/-1 until end of turn. " +
                "If you control a Faerie, draw a card."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, {
                game.doAction(new MagicChangeTurnPTAction(it, -1, -1));
                final MagicPlayer you = event.getPlayer();
                if (you.controlsPermanent(MagicSubType.Faerie)) {
                    game.doAction(new MagicDrawAction(you));
                }
            });
        }
    }
]
