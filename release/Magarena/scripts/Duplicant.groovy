def NONTOKEN_CREATURE = new MagicPermanentFilterImpl() {
        public boolean accept(final MagicGame game,final MagicPlayer player,final MagicPermanent target) {
            return target.isCreature() && !target.isToken();
        }
    };
[
    new MagicWhenComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent, final MagicPayedCost payedCost) {
            final MagicTargetChoice targetChoice = new MagicTargetChoice(
                new MagicOtherPermanentTargetFilter(
                    NONTOKEN_CREATURE,
                    permanent
                ),
                MagicTargetHint.None,
                "another target nontoken creature to exile"
            );
            return new MagicEvent(
                permanent,
                new MagicMayChoice(targetChoice),
                MagicExileTargetPicker.create(),
                this,
                "PN may\$ exile another target nontoken creature\$."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            if (event.isYes()) {
                event.processTargetPermanent(game,new MagicPermanentAction() {
                    public void doAction(final MagicPermanent target) {
                        game.doAction(new MagicExileUntilThisLeavesPlayAction(
                            event.getPermanent(), 
                            target
                        ));
                    }
                });
            }
        }
    },
    new MagicStatic(MagicLayer.SetPT) {
        @Override
        public void modPowerToughness(final MagicPermanent source,final MagicPermanent permanent,final MagicPowerToughness pt) {
            final MagicCard card = source.getExiledCards().getCardAtTop();
            pt.set(card.getPower(),card.getToughness());
        }
        @Override
        public boolean condition(final MagicGame game,final MagicPermanent source,final MagicPermanent target) {
            return MagicCondition.HAS_EXILED_CARD.accept(source);
        }
    }, 
    new MagicStatic(MagicLayer.Type) {
        @Override
        public void modSubTypeFlags(final MagicPermanent source, final Set<MagicSubType> flags) {
            final MagicCard card = source.getExiledCards().getCardAtTop();
            flags.addAll(card.getCardDefinition().getSubTypeFlags());
        }
        @Override
        public boolean condition(final MagicGame game,final MagicPermanent source,final MagicPermanent target) {
            return MagicCondition.HAS_EXILED_CARD.accept(source);
        }
    }
]
