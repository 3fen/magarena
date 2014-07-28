[
    new MagicWhenSelfCombatDamagePlayerTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicDamage damage) {
            final MagicTarget target = damage.getTarget();
            return new MagicEvent(
                permanent,
                permanent.isController(target) ?
                    MagicTargetChoice.TARGET_CREATURE_CARD_FROM_GRAVEYARD :
                    MagicTargetChoice.TARGET_CREATURE_CARD_FROM_OPPONENTS_GRAVEYARD,
                MagicGraveyardTargetPicker.PutOntoBattlefield,
                this,
                "PN may put target creature card from ${target.toString()}'s graveyard\$ onto the battlefield under PN's control."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetCard(game, {
                game.doAction(new MagicReanimateAction(
                    it,
                    event.getPlayer()
                ));
            });
        }
    }
]
