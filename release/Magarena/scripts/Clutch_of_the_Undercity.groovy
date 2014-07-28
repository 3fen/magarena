[   
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            new MagicEvent(
                cardOnStack,
                MagicTargetChoice.TARGET_PERMANENT,
                MagicBounceTargetPicker.create(),
                this,
                "Return target permanent\$ to its owner's hand. Its controller loses 3 life."
            )
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, {
                game.doAction(new MagicRemoveFromPlayAction(it,MagicLocationType.OwnersHand));
                game.doAction(new MagicChangeLifeAction(it.getController(),-3));
            });
        }
    }
    
]
