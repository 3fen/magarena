[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                new MagicMayChoice(MagicTargetChoice.TARGET_CREATURE_CARD_FROM_ALL_GRAVEYARDS),
                MagicGraveyardTargetPicker.PutOntoBattlefield,
                this,
                "Put SN onto the battlefield. You may\$ have SN enter the battlefield as a copy of any creature\$ card in a graveyard."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            if (event.isYes()) {
              event.processTargetCard(game, {
                  final MagicCard chosen ->
                  game.doAction(MagicPlayCardFromStackAction.EnterAsCopy(
                      event.getCardOnStack(),
                      chosen
                  ));
              } as MagicCardAction);
            } else {
                game.doAction(new MagicPlayCardFromStackAction(
                    event.getCardOnStack()
                ));
            }
        }
    }
]
