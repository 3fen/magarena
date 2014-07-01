[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                this,
                "PN puts a 1/1 green Saproling creature token onto the battlefield "+
                "for each basic land type among lands he or she controls."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicPlayer player = event.getPlayer();
            final int domain = player.getDomain();
            game.logAppendMessage(player," ("+domain+")");
            game.doAction(new MagicPlayTokensAction(player,TokenCardDefinitions.get("1/1 green Saproling creature token"),domain));
        }
    }
]
