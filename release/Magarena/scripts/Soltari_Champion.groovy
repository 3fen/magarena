[
    new MagicWhenSelfAttacksTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent attacker) {
            return new MagicEvent(
                permanent,
                this,
                "Other creatures PN controls get +1/+1 until end of turn."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            CREATURE_YOU_CONTROL
            .except(event.getPermanent())
            .filter(event.getPlayer()) each {
                game.doAction(new MagicChangeTurnPTAction(it,1,1));
            }
        }
    }
]
