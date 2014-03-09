[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Pump),
        "Hexproof"
    ){
        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [
                new MagicPayManaCostEvent(source,"{2}")
            ];
        }
        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent permanent,final MagicPayedCost payedCost) {
            return permanent.getEquippedCreature().isValid() ?
                new MagicEvent(
                    permanent,
                    permanent.getEquippedCreature(),
                    this,
                    "RN gains hexproof until end of turn."
                ) :
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            game.doAction(new MagicGainAbilityAction(event.getRefPermanent(),MagicAbility.Hexproof));
        }
    },
    new MagicAtUpkeepTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPlayer upkeepPlayer) {
            final MagicPermanent equipped = permanent.getEquippedCreature();
            return permanent.isController(upkeepPlayer) && equipped.isValid() && equipped.hasColor(MagicColor.Blue) ?
                new MagicEvent(
                    permanent,
                    this,
                    "PN puts a +1/+1 counter on creature equipped by SN."
                ):
                MagicEvent.NONE;
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicPermanent permanent=event.getPermanent();
            final MagicPermanent equipped=permanent.getEquippedCreature();
            if (equipped.isValid() && equipped.hasColor(MagicColor.Blue)) {
                game.doAction(new MagicChangeCountersAction(equipped,MagicCounterType.PlusOne,1,true));
            }
        }
    }
]
