[
    new MagicStatic(MagicLayer.SetPT) {
        @Override
        public void modPowerToughness(final MagicPermanent source,final MagicPermanent permanent,final MagicPowerToughness pt) {
            final int amt = source.getGame().filterCards(source.getController(), MagicTargetFilterFactory.LAND_CARD_FROM_YOUR_GRAVEYARD).size();
            pt.add(amt, amt);
        }
    }
]
