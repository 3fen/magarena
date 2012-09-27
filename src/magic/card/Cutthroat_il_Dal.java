package magic.card;

import magic.model.MagicAbility;
import magic.model.MagicPermanent;
import magic.model.condition.MagicCondition;
import magic.model.mstatic.MagicLayer;
import magic.model.mstatic.MagicStatic;

public class Cutthroat_il_Dal {
    public static final MagicStatic S = new MagicStatic(MagicLayer.Ability) {
        @Override
        public long getAbilityFlags(final MagicPermanent source,final MagicPermanent permanent,final long flags) {
            return MagicCondition.HELLBENT.accept(permanent) ?
                flags | MagicAbility.Shadow.getMask() : 
                flags;
        }
    };
}
