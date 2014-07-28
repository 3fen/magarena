[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                MagicTargetChoice.Negative("target nonartifact creature"),
                MagicDestroyTargetPicker.Destroy,
                this,
                "Destroy target nonartifact creature.\$ Its controller puts a number of cards equal to "+
                "that creature's power from the top of his or her library into his or her graveyard."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, {
                game.doAction(new MagicDestroyAction(it));
                game.doAction(new MagicMillLibraryAction(it.getController(),it.getPower()));
                game.logAppendMessage(event.getPlayer(),"("+it.getPower()+")");
            });
        }
    }
]
