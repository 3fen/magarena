[
    new MagicAtUpkeepTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPlayer upkeepPlayer) {
            return permanent.isController(upkeepPlayer) ?
                new MagicEvent(
                    permanent,
                    upkeepPlayer,
                    this,
                    "PN gains 1 life for each card in his or her hand."
                ) :
                MagicEvent.NONE;
        }
 
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicPlayer player = event.getPlayer();
            game.doAction(new MagicChangeLifeAction(player, player.getHandSize()));
        }
    }
]
