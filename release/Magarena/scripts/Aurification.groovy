def AB = new MagicStatic(MagicLayer.Ability) {
    @Override
    public void modAbilityFlags(
            final MagicPermanent source,
            final MagicPermanent permanent,
            final Set<MagicAbility> flags) {
        permanent.addAbility(MagicAbility.Defender, flags);
    }
    @Override
    public boolean condition(
            final MagicGame game,
            final MagicPermanent source,
            final MagicPermanent target) {
        return target.getCounters(MagicCounterType.Gold) > 0;
    }
};

def ST = new MagicStatic(MagicLayer.Type) {
    @Override
    public void modSubTypeFlags(
            final MagicPermanent permanent,
            final Set<MagicSubType> flags) {
        flags.add(MagicSubType.Wall);
    }
    @Override
    public boolean condition(
            final MagicGame game,
            final MagicPermanent source,
            final MagicPermanent target) {
        return target.getCounters(MagicCounterType.Gold) > 0;
    }
};

[
    new MagicWhenDamageIsDealtTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicDamage damage) {
            return (permanent.isController(damage.getTarget()) &&
                    damage.getSource().isCreature()) ?
                new MagicEvent(
                    permanent,
                    damage.getSource(),
                    this,
                    "PN puts a gold counter on RN."
                ) :
                MagicEvent.NONE;
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processRefPermanent(game, {
                final MagicPermanent creature ->
                game.doAction(new MagicChangeCountersAction(creature,MagicCounterType.Gold,1,true));
                game.doAction(new MagicAddStaticAction(creature, AB));
                game.doAction(new MagicAddStaticAction(creature, ST));
            });
        }
    },
    new MagicWhenLeavesPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicRemoveFromPlayAction act) {
            return act.isPermanent(permanent) ?
                new MagicEvent(
                    permanent,
                    this,
                    "Remove all gold counters from all creatures."
                ):
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final Collection<MagicPermanent> targets =
                    game.filterPermanents(event.getPlayer(),MagicTargetFilterFactory.CREATURE);
            for (final MagicPermanent permanent : targets) {
                game.doAction(new MagicChangeCountersAction(
                    permanent,
                    MagicCounterType.Gold,
                    -permanent.getCounters(MagicCounterType.Gold),
                    true
                ));
            }
        }
    }
]
