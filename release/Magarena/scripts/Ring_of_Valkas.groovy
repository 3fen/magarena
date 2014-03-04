[
    new MagicAtUpkeepTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPlayer upkeepPlayer) {
            final MagicPermanent equipped = permanent.getEquippedCreature();
            return permanent.isController(upkeepPlayer) && equipped.isValid() && equipped.hasColor(MagicColor.Red) ?
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
            final MagicPermanent equipped=permanent.getEnchantedPermanent();
            if (equipped.isValid() && equipped.hasColor(MagicColor.Red)) {
                game.doAction(new MagicChangeCountersAction(equipped,MagicCounterType.PlusOne,1,true));
            }
        }
    }
]
