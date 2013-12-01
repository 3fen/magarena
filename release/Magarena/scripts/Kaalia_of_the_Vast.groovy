def TARGET_ANGEL_OR_DEMON_OR_DRAGON_CARD_FROM_HAND = new MagicCardFilterImpl() {
    public boolean accept(final MagicGame game,final MagicPlayer player,final MagicCard target) {
        return target.hasType(MagicType.Creature) &&
               (target.hasSubType(MagicSubType.Angel) ||
               target.hasSubType(MagicSubType.Demon) ||
               target.hasSubType(MagicSubType.Dragon));
    }
    public boolean acceptType(final MagicTargetType targetType) {
        return targetType == MagicTargetType.Hand;
    }
}; 
def ANGEL_OR_DEMON_OR_DRAGON_CARD_FROM_HAND = new MagicTargetChoice(
    TARGET_ANGEL_OR_DEMON_OR_DRAGON_CARD_FROM_HAND,  
    MagicTargetHint.None,
    "an Angel, Demon or Dragon creature card from your hand"
);
[
    new MagicWhenAttacksTrigger(1) {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent attacker) {
            return permanent == attacker ?
                new MagicEvent(
                    permanent,
                    new MagicMayChoice(
                        ANGEL_OR_DEMON_OR_DRAGON_CARD_FROM_HAND
                    ),
                    MagicGraveyardTargetPicker.PutOntoBattlefield,
                    this,
                    "PN may\$ put an Angel, Demon or Dragon creature card\$ from his or her hand onto the battlefield tapped and attacking."
                ):
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            if(event.isYes()){
                event.processTargetCard(game, {
                    final MagicCard card ->
                    game.doAction(new MagicRemoveCardAction(card,MagicLocationType.OwnersHand));
                    game.doAction(new MagicPlayCardAction(
                        card,
                        event.getPlayer(), 
                        [MagicPlayMod.TAPPED, MagicPlayMod.ATTACKING]
                    ));
                });
            }
        }
    }
]
