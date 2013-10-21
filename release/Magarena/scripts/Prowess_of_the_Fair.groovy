[
    new MagicWhenOtherDiesTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent otherPermanent) {
            return (permanent != otherPermanent && 
                    otherPermanent.isNonToken() &&
                    otherPermanent.hasSubType(MagicSubType.Elf) && 
                    otherPermanent.isFriend(permanent)) ?
                new MagicEvent(
                    permanent,
                    new MagicMayChoice(),
                    this,
                    "PN may\$ put a 1/1 green Elf Warrior creature token onto the battlefield."
                ) :
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            if (event.isYes()) {
                game.doAction(new MagicPlayTokenAction(
                    event.getPlayer(), 
                    TokenCardDefinitions.get("1/1 green Elf Warrior creature token")
                ));
            }
        }
    }
]
