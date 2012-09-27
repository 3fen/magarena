
package magic.card;

import magic.model.MagicAbility;
import magic.model.MagicPermanent;
import magic.model.mstatic.MagicLayer;
import magic.model.mstatic.MagicStatic;

public class Soltari_Lancer {
    public static final MagicStatic S = new MagicStatic(MagicLayer.Ability) {
        @Override
        public long getAbilityFlags(
                final MagicPermanent source,
                final MagicPermanent permanent,
                final long flags) {
            return permanent.isAttacking() ? 
                flags|MagicAbility.FirstStrike.getMask():
                flags;
        }
    };
}
