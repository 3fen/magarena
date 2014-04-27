[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                MagicTargetChoice.NEG_TARGET_CREATURE_OR_PLAYER,
                new MagicDamageTargetPicker(3),
                this,
                "SN deals 3 damage to target creature or player\$. "+
                "That creature can't be regenerated this turn. If the creature would die this turn, exile it instead."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTarget(game, {
                final MagicTarget target ->
                final MagicDamage damage=new MagicDamage(event.getSource(),target,3);
                game.doAction(new MagicDealDamageAction(damage));
            });
            event.processTargetPermanent(game, {
                final MagicPermanent creature ->
                game.doAction(MagicChangeStateAction.Set(creature,MagicPermanentState.CannotBeRegenerated));
                game.doAction(new MagicAddTurnTriggerAction(creature,MagicWhenSelfLeavesPlayTrigger.IfDieExileInstead));
            });
        }
    }
]
