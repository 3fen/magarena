def PT = new MagicStatic(MagicLayer.SetPT, MagicStatic.UntilEOT) {
    @Override
    public void modPowerToughness(final MagicPermanent source,final MagicPermanent permanent,final MagicPowerToughness pt) {
        pt.set(2,5);
    }
};
def LifeGainAbility = new MagicWhenDamageIsDealtTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicDamage damage) {
            return (damage.getSource() == permanent) ?
                new MagicEvent(
                    permanent,
                    damage.getDealtAmount(),
                    this,
                    "PN gains RN life and " 
                ) :
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            game.doAction(new MagicChangeLifeAction(event.getPlayer(), event.getRefInt()));
        }
    }
    

def AB = new MagicStatic(MagicLayer.Ability, MagicStatic.UntilEOT) {
    @Override
    public void modAbilityFlags(final MagicPermanent source,final MagicPermanent permanent,final Set<MagicAbility> flags) {
        permanent.addAbility(LifeGainAbility);
    }
};
def ST = new MagicStatic(MagicLayer.Type, MagicStatic.UntilEOT) {
    @Override
    public int getTypeFlags(final MagicPermanent permanent,final int flags) {
        return flags | MagicType.Creature.getMask();
    }
    @Override
    public void modTypeFlags(final MagicPermanent permanent, final Set<MagicType> flags) {    
        flags.add(MagicType.Creature);
    }
    @Override
    public void modSubTypeFlags(final MagicPermanent permanent, final Set<MagicSubType> flags) {
        flags.add(MagicSubType.Spirit);
    }
};
def LC = new MagicStatic(MagicLayer.Color, MagicStatic.UntilEOT) {
    @Override
    public int getColorFlags(final MagicPermanent permanent,final int flags) {
        return MagicColor.White.getMask();
    }
};
[
    new MagicPermanentActivation(
        [
            new MagicArtificialCondition(
                MagicConditionFactory.ManaCost("{3}")
            )
        ],
        new MagicActivationHints(MagicTiming.Animate),
        "Animate"
    ) {

        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [new MagicPayManaCostEvent(source,"{2}")];
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            return new MagicEvent(
                source.getEnchantedPermanent(),
                this,
                "Until end of turn, SN becomes a 2/5 white Spirit creature with 'Whenever this creature deals damage, its controller gains that much life.'" +
                "It's still a land."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            game.doAction(new MagicBecomesCreatureAction(event.getPermanent(),PT,AB,ST,LC));
        }
    },
    new MagicWhenOtherDiesTrigger() {
        @Override
        public MagicEvent executeTrigger(
                final MagicGame game,
                final MagicPermanent permanent,
                final MagicPermanent died) {
            return (permanent.getEnchantedPermanent() == died) ?
                new MagicEvent(
                    permanent,
                    permanent.getCard(),
                    this,
                    "Return RN to its owner's hand."
                ):
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicCard card = event.getRefCard();
            if (card.isInGraveyard()) {
                game.doAction(new MagicRemoveCardAction(card,MagicLocationType.Graveyard));
                game.doAction(new MagicMoveCardAction(card,MagicLocationType.Graveyard,MagicLocationType.OwnersHand));
            }
        }
    }
]
