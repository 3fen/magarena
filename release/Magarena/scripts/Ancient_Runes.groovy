[
    new MagicAtUpkeepTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game, final MagicPermanent permanent, final MagicPlayer upkeepPlayer) {
            return new MagicEvent(
                permanent,
                upkeepPlayer,
                this,
                "SN deals damage to PN equal to the number of artifacts he or she controls."
            );
        }
        @Override
        public void executeEvent(final MagicGame game,final MagicEvent event) {
            final int amount = event.getPlayer().getNrOfPermanents(MagicTargetFilterFactory.ARTIFACT_YOU_CONTROL);
            final MagicDamage damage = new MagicDamage(event.getSource(),event.getPlayer(),amount);
            game.doAction(new MagicDealDamageAction(damage));
            game.logAppendMessage(event.getPlayer(),"("+amount+")");
        }
    }
]
