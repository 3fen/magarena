def NONBLUE_CREATURE=new MagicPermanentFilterImpl() {
        public boolean accept(final MagicGame game,final MagicPlayer player,final MagicPermanent target) {
            return target.isCreature() && !target.hasColor(MagicColor.Blue);
        }
    };
[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                this,
                "Return all nonblue creatures to their owners' hands."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final Collection<MagicPermanent> targets=
                game.filterPermanents(event.getPlayer(),NONBLUE_CREATURE);
            for (final MagicPermanent target : targets) {
                game.doAction(new MagicRemoveFromPlayAction(target,MagicLocationType.OwnersHand));
            }
        }
    }
]
