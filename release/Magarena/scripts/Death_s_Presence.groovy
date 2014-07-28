[
    new MagicWhenOtherDiesTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent died) {
            return died.hasType(MagicType.Creature) && died.isFriend(permanent) ?
                new MagicEvent(
                    permanent,
                    MagicTargetChoice.Positive("target creature you control"),
                    died.getPower(),
                    this,
                    "PN puts X +1/+1 counters on target creature he or she controls\$, where X is the power of "+died.getName()+". (X="+died.getPower()+")"
                ):
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, {
                game.doAction(new MagicChangeCountersAction(it,MagicCounterType.PlusOne,event.getRefInt(),true));
            });
        }
    }
]
