[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Removal),
        "Damage"
    ) {
        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [
                new MagicPayManaCostEvent(source,"{1}{R}"),
                new MagicSacrificePermanentEvent(source,MagicTargetChoice.SACRIFICE_GOBLIN)
            ];
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                MagicTargetChoice.NEG_TARGET_CREATURE_OR_PLAYER,
                payedCost.getTarget(),
                this,
                "SN deals damage equal to the power of RN to target creature or player\$."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTarget(game, {
                final MagicTarget target ->
                final MagicPermanent sacrificed=event.getRefPermanent();
                final MagicDamage damage=new MagicDamage(event.getSource(),target,sacrificed.getPower());
                game.doAction(new MagicDealDamageAction(damage));
            });
        }
    }
]
