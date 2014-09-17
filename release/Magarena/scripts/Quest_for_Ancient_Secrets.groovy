[
    new MagicWhenOtherPutIntoGraveyardTrigger() {
        @Override
        public boolean accept(final MagicPermanent permanent, final MagicMoveCardAction act) {
            return act.getToLocation() == MagicLocationType.Graveyard;
        }
    
        @Override
        public MagicEvent executeTrigger(final MagicGame game, final MagicPermanent permanent, final MagicMoveCardAction act) {
            final MagicCard card = act.card;
            return (card.isFriend(permanent) &&
                    act.to(MagicLocationType.Graveyard)) ?
                new MagicEvent(
                    permanent,
                    new MagicSimpleMayChoice(
                        MagicSimpleMayChoice.ADD_POS_COUNTER,
                        1,
                        MagicSimpleMayChoice.DEFAULT_YES
                    ),
                    this,
                    "PN may\$ put a Quest counter on SN."
                ) :
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            if (event.isYes()){
                game.doAction(new MagicChangeCountersAction(event.getPermanent(),MagicCounterType.Quest,1));
            }
        }
    },
    
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Removal),
        "Shuffle"
    ) {

        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [
                new MagicRemoveCounterEvent(source,MagicCounterType.Quest,5), new MagicSacrificeEvent(source)
            ];
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source, final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                MagicTargetChoice.TARGET_PLAYER,
                this,
                "Target player\$ shuffles his or her graveyard into his or her library."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPlayer(game, {
                final MagicCardList graveyard = new MagicCardList(it.getGraveyard());
                for (final MagicCard card : graveyard) {
                    game.doAction(new MagicRemoveCardAction(card,MagicLocationType.Graveyard));
                    game.doAction(new MagicMoveCardAction(card,MagicLocationType.Graveyard,MagicLocationType.OwnersLibrary));
                }
            });
        }
    }
]
