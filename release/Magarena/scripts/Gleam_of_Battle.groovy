[
    new MagicWhenAttacksTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent creature) {
            return (creature.isFriend(permanent)) ?
                new MagicEvent(
                    permanent,
                    creature,
                    this,
                    "PN puts a +1/+1 counter on RN."
                ) :
                MagicEvent.NONE;
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicDamage damage=new MagicDamage(event.getSource(),event.getRefPermanent(),4);
            game.doAction(new MagicChangeCountersAction(event.getRefPermanent(), MagicCounterType.PlusOne, 1, true));
        }
    }
]
