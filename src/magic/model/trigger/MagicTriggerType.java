package magic.model.trigger;

public enum MagicTriggerType {
    AtUpkeep,               // player
    AtEndOfTurn,            // player
    WhenDamageIsDealt,      // damage
    WhenOtherSpellIsCast,   // card on stack
    WhenSpellIsCast,        // card on stack
    WhenComesIntoPlay,      // controller
    WhenLeavesPlay,         // permanent
    WhenBecomesTapped,      // permanent
    WhenDrawn,              // card
    WhenOtherDrawn,         // card
    WhenLifeIsGained,       // player, life gained
    WhenLifeIsLost,         // player, life lost
    WhenOtherComesIntoPlay, // permanent
    WhenPutIntoGraveyard,       // graveyard trigger data
    WhenOtherPutIntoGraveyard,  // graveyard trigger data
    WhenOtherPutIntoGraveyardFromPlay, // permanent
    WhenAttacks,            // permanent
    WhenBlocks,             // permanent
    WhenBecomesBlocked,     // permanent
    WhenAttacksUnblocked,   // permanent
    WhenTargeted,           // permanent
    WhenLoseControl,        // permanent
    IfDamageWouldBeDealt,   // item on stack
    IfPlayerWouldLose,      // player[]
    ;
    
    public boolean usesStack() {
        return this != IfDamageWouldBeDealt;
    }
}
