[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Removal),
        "Damage"
    ) {

        @Override
        public Iterable getCostEvent(final MagicPermanent source) {
            return [
            new MagicPayManaCostEvent(source,"{X}{U}{R}"),
            new MagicTapEvent(source)
         ];
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            final int amount = payedCost.getX();
            return new MagicEvent(
                source,
                MagicTargetChoice.TARGET_CREATURE,
                new MagicDamageTargetPicker(amount),
                amount,
                this,
                "SN deals RN damage to target creature\$. That creature's controller draws RN cards."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, {
                final MagicPermanent creature ->
                final int amount = event.getRefInt();
                game.doAction(new MagicDealDamageAction(
                    new MagicDamage(event.getSource(),creature,amount)
                ));
                game.doAction(new MagicDrawAction(creature.getController(),amount));
            });
        }
    }
]
