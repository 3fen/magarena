def NONBLACK_CREATURE_YOUR_OPPONENT_CONTROLS=new MagicPermanentFilterImpl() {
    public boolean accept(final MagicGame game,final MagicPlayer player,final MagicPermanent target) {
        return target.isCreature() && !target.hasColor(MagicColor.Black) && target.isOpponent(player);
    } 
};

def TARGET_NONBLACK_CREATURE_YOUR_OPPONENT_CONTROLS = new MagicTargetChoice(
    NONBLACK_CREATURE_YOUR_OPPONENT_CONTROLS,
    "target nonblack creature your opponent controls"
);

[
    new MagicWhenSelfCombatDamagePlayerTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicDamage damage) {
            return new MagicEvent(
                permanent,
                TARGET_NONBLACK_CREATURE_YOUR_OPPONENT_CONTROLS,
                MagicDestroyTargetPicker.Destroy,
                this,
                "Destroy target nonblack creature defending player controls."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, {
                final MagicPermanent permanent ->
                game.doAction(new MagicDestroyAction(permanent));
            });
        }
    }
]
