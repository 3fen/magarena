def LoseControlTap = {
    final MagicPlayer you ->
    return new MagicWhenLoseControlTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent, final MagicPermanent target) {
            if (permanent == target && target.getController().getId() != you.getId()) {
                game.addDelayedAction(new MagicRemoveTriggerAction(permanent, this));
                return new MagicEvent(
                    permanent,
                    this,
                    "Tap SN"
                );
            }
            return MagicEvent.NONE;
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            game.doAction(new MagicTapAction(event.getPermanent(), true));
        }
    };
}

[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                MagicTargetChoice.TARGET_CREATURE_YOUR_OPPONENT_CONTROLS,
                MagicExileTargetPicker.create(),
                this,
                "Untap target creature\$ an opponent controls and gain control of it until end of turn. " + 
                "That creature gains haste until end of turn. When you lose control of the creature, tap it."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, {
                final MagicPermanent creature ->
                game.doAction(new MagicGainControlAction(event.getPlayer(),creature,MagicStatic.UntilEOT));
                game.doAction(new MagicUntapAction(creature));
                game.doAction(new MagicGainAbilityAction(creature,MagicAbility.Haste));
                game.doAction(new MagicAddTriggerAction(creature, LoseControlTap(event.getPlayer())));
            });
        }
    }
]
