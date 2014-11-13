def UNTAPPED_DRUID_YOU_CONTROL=new MagicPermanentFilterImpl(){
    public boolean accept(final MagicGame game,final MagicPlayer player,final MagicPermanent target) {
        return target.hasSubType(MagicSubType.Druid) && 
               target.isUntapped() && 
               target.isController(player);
    }
};

def AN_UNTAPPED_DRUID_YOU_CONTROL = new MagicTargetChoice(UNTAPPED_DRUID_YOU_CONTROL,"an untapped Druid you control");

[
    new MagicWhenOtherSpellIsCastTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicCardOnStack cardOnStack) {
            return permanent.isFriend(cardOnStack) && cardOnStack.hasSubType(MagicSubType.Druid) == true ?
                new MagicEvent(
                    permanent,
                    new MagicMayChoice("Draw a card?"),
                    this,
                    "PN may\$ draw a card."
                ):
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            if (event.isYes()) {
                game.doAction(new MagicDrawAction(event.getPlayer(),1));
            }
        }
    },
        new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Removal),
        "Control"
    ) {
        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [
                new MagicTapPermanentEvent(source, AN_UNTAPPED_DRUID_YOU_CONTROL),
                new MagicTapPermanentEvent(source, AN_UNTAPPED_DRUID_YOU_CONTROL),
                new MagicTapPermanentEvent(source, AN_UNTAPPED_DRUID_YOU_CONTROL),
                new MagicTapPermanentEvent(source, AN_UNTAPPED_DRUID_YOU_CONTROL),
                new MagicTapPermanentEvent(source, AN_UNTAPPED_DRUID_YOU_CONTROL),
                new MagicTapPermanentEvent(source, AN_UNTAPPED_DRUID_YOU_CONTROL),
                new MagicTapPermanentEvent(source, AN_UNTAPPED_DRUID_YOU_CONTROL)
            ];
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source, final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                MagicTargetChoice.NEG_TARGET_PLAYER,
                this,
                "Gain control of all lands target player\$ controls."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPlayer(game, {
            final Collection<MagicPermanent> targets = it.filterPermanents(MagicTargetFilterFactory.LAND_YOU_CONTROL);
                for (final MagicPermanent land : targets) {
                game.doAction(new MagicGainControlAction(event.getPlayer(),land));
                }
            });
        }
    }
]
