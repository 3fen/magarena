[
    new MagicAtBeginOfCombatTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent, final MagicPlayer upkeepPlayer) {
            return new MagicEvent(
                permanent,
                permanent.getOpponent(),
                new MagicMayChoice("Sacrifice a creature?"),
                this,
                "PN may\$ sacrifice a creature. If you do, tap SN and put a +1/+1 counter on it."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicPermanent perm = event.getPermanent();
            final MagicEvent sac = new MagicSacrificePermanentEvent(perm,event.getPlayer(),MagicTargetChoice.SACRIFICE_CREATURE)
            if (event.isYes() && sac.isSatisfied()) {
                game.addEvent(sac);
                game.doAction(new MagicTapAction(perm));
                game.doAction(new MagicChangeCountersAction(perm,MagicCounterType.PlusOne,1));
            }
        }
    }
]
