[
    new MagicWhenSelfCombatDamagePlayerTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicDamage damage) {
            final int amount = damage.getDealtAmount();
            return new MagicEvent(
                permanent,
                new MagicMayChoice(MagicTargetChoice.TARGET_CREATURE_YOUR_OPPONENT_CONTROLS),
                new MagicDamageTargetPicker(amount),
                amount,
                this,
                "PN may\$ have SN deal RN damage to target creature\$ your opponent controls."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            if (event.isYes()) {
                event.processTargetPermanent(game, {
                    final MagicDamage damage=new MagicDamage(event.getSource(),it,event.getRefInt());
                    game.doAction(new MagicDealDamageAction(damage));
                });
            }
        }
    }
]
