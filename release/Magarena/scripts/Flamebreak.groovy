[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                this,
                "SN deals 3 damage to each creature without flying and each player." + "Creatures dealt damage this way can't be regenerated this turn."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicSource source=event.getSource();
            final Collection<MagicPermanent> targets=
                game.filterPermanents(event.getPlayer(),MagicTargetFilterFactory.CREATURE_WITHOUT_FLYING);
            for (final MagicPermanent target : targets) {
                final MagicDamage damage=new MagicDamage(source,target,3);
			damage.setNoRegeneration();
                game.doAction(new MagicDealDamageAction(damage));
            }
            for (final MagicPlayer player : game.getAPNAP()) {
                final MagicDamage damage=new MagicDamage(source,player,3);
                game.doAction(new MagicDealDamageAction(damage));
            }
        }
    }
]
