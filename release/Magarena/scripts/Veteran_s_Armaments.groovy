def act = {
    final MagicGame game, final MagicEvent event ->
    final int amount = event.getPlayer().getOpponent().getNrOfAttackers() + event.getPlayer().getNrOfAttackers();
    game.doAction(new MagicChangeTurnPTAction(event.getPermanent(),amount,amount));
} as MagicEventAction

def evt = {
    final MagicPermanent permanent ->
    return new MagicEvent(
        permanent,
        act,
        "SN gets +X/+X until end of turn, where X is the number of attacking creatures."
    );
}

[
    new MagicWhenBlocksTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent blocker) {
            return (permanent.getEquippedCreature() == blocker) ? evt(blocker) : MagicEvent.NONE;
        }
    },
    new MagicWhenAttacksTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent attacker) {
            return (permanent.getEquippedCreature() == attacker) ? evt(attacker) : MagicEvent.NONE;
        }
    },
	new MagicWhenOtherComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent otherPermanent) {
            return (otherPermanent.isCreature() &&
					otherPermanent.hasSubType(MagicSubType.Soldier)) ?
                new MagicEvent(
                    permanent,
                    new MagicMayChoice(),
					otherPermanent,
                    this,
                    "You may\$ attach SN to RN."
                ) :
                MagicEvent.NONE;
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            if (event.isYes()) {
				game.doAction(new MagicAttachAction(
					event.getPermanent(),
					event.getRefPermanent()
				));
			}
        }
    }
]
