package magic.data;

import magic.model.MagicAbility;
import magic.model.MagicCardDefinition;
import magic.model.MagicColor;
import magic.model.MagicManaCost;
import magic.model.MagicStaticType;
import magic.model.MagicSubType;
import magic.model.MagicType;
import magic.model.event.MagicPlayAuraEvent;
import magic.model.event.MagicSpellCardEvent;
import magic.model.event.MagicTiming;
import magic.model.mstatic.MagicStatic;

public enum CardProperty {
    IMAGE() {
        void setProperty(final MagicCardDefinition card, final String value) {
            card.setImageURL(value);
        }
    },
    URL() {
        void setProperty(final MagicCardDefinition card, final String value) {
            card.setCardInfoURL(value);
        }
    },
    NUM_IMAGES() {
        void setProperty(final MagicCardDefinition card, final String value) {
            card.setImageCount(Integer.parseInt(value));
        }
    },
    VALUE() {
        void setProperty(final MagicCardDefinition card, final String value) {
            card.setValue(Double.parseDouble(value));
        }
    },
    REMOVAL() {
        void setProperty(final MagicCardDefinition card, final String value) {
            card.setRemoval(Integer.parseInt(value));
        }
    },
    RARITY() {
        void setProperty(final MagicCardDefinition card, final String value) {
            card.setRarity(value.charAt(0));
        }
    },
    TYPE() {
        void setProperty(final MagicCardDefinition card, final String value) {
            final String[] names=value.split(",");
            for (final String name : names) {
                card.addType(MagicType.getType(name));
            }
        }
    },
    SUBTYPE() {
        void setProperty(final MagicCardDefinition card, final String value) {
            card.setSubTypes(value.split(","));
        }
    },
    COLOR() {
        void setProperty(final MagicCardDefinition card, final String value) {
            card.setColors(value);
        }
    },
    CONVERTED() {
        void setProperty(final MagicCardDefinition card, final String value) {
            //not needed, derive from mana cost
            //left here for backward compatibility with old card script format
        }
    },
    COST() {
        void setProperty(final MagicCardDefinition card, final String value) {
            card.setCost(MagicManaCost.create(value));
        }
    },
    EQUIP() {
        void setProperty(final MagicCardDefinition card, final String value) {
            card.setEquipCost(MagicManaCost.create(value));
        }
    },
    MANA() {
        void setProperty(final MagicCardDefinition card, final String value) {
            card.setManaSourceText(value);
        }
    },
    PT() {
        void setProperty(final MagicCardDefinition card, final String value) {
            final String[] pt = value.split("/");
            card.setPowerToughness(Integer.parseInt(pt[0]),Integer.parseInt(pt[1]));
        }
    },
    ABILITY() {
        void setProperty(final MagicCardDefinition card, final String value) {
            final String[] names=value.split(",(?! )");
            for (final String name : names) {
                final MagicAbility ability = MagicAbility.getAbility(name);
                final String arg = name.substring(ability.toString().length()).trim();
                card.setAbility(ability, arg);
            }
        }
    },
    GIVEN_PT() {
        void setProperty(final MagicCardDefinition card, final String value) {
            final String[] pt = value.replace('+','0').split("/");
            card.add(MagicStatic.genPTStatic(Integer.parseInt(pt[0]), Integer.parseInt(pt[1])));
        }
    },
    GIVEN_ABILITY() {
        void setProperty(final MagicCardDefinition card, final String value) {
            card.add(MagicStatic.genABStatic(MagicAbility.getAbilities(value.split(","))));
        }
    },
    GIVEN_SUBTYPE() {
        void setProperty(final MagicCardDefinition card, final String value) {
            card.add(MagicStatic.genSTStatic(MagicSubType.getSubTypes(value.split(","))));
        }
    },
    GIVEN_COLOR() {
        void setProperty(final MagicCardDefinition card, final String value) {
            card.add(MagicStatic.genCOStatic(MagicColor.getFlags(value)));
        }
    },
    STATIC() {
        void setProperty(final MagicCardDefinition card, final String value) {
            card.setStaticType(MagicStaticType.getStaticTypeFor(value));
        }
    },
    TIMING() {
        void setProperty(final MagicCardDefinition card, final String value) {
            card.setTiming(MagicTiming.getTimingFor(value));
        }
    },
    IGNORE() {
        void setProperty(final MagicCardDefinition card, final String value) {
            final String[] sizes=value.split(",");
            for (final String size : sizes) {
                card.addIgnore(Long.parseLong(size));
            }
        }
    },
    MANA_OR_COMBAT() {
        void setProperty(final MagicCardDefinition card, final String value) {
            card.setExcludeManaOrCombat();
        }
    },
    ENCHANT() {
        void setProperty(final MagicCardDefinition card, final String value) {
            card.add(MagicPlayAuraEvent.create(value));
        }
    },
    TOKEN() {
        void setProperty(final MagicCardDefinition card, final String value) {
            card.setToken();
            card.setName(value);
        }
    },
    NAME() {
        void setProperty(final MagicCardDefinition card, final String value) {
            assert card.getName() == null;
            assert card.getFullName() == null;
            card.setName(value);
            card.setFullName(value);
        }
    },
    EFFECT() {
        void setProperty(final MagicCardDefinition card, final String value) {
            card.add(MagicSpellCardEvent.create(value));
        }
    },
    REQUIRES_CARD_CODE() {
        void setProperty(final MagicCardDefinition card, final String value) {
            final String cardName = !value.isEmpty() ? value : card.getFullName();
            CardDefinitions.addCardSpecificCode(card, cardName);
        }
    },
    REQUIRES_GROOVY_CODE() {
        void setProperty(final MagicCardDefinition card, final String value) {
            final String cardName = !value.isEmpty() ? value : card.getFullName();
            CardDefinitions.addCardSpecificGroovyCode(card, cardName);
        }
    };
    ;
    
    void setProperty(final MagicCardDefinition card, final String value) {
        //do nothing
    }
}
