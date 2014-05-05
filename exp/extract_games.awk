BEGIN {
    FS = "\t"
}

function genName(name) {
           if (name == "minimax (cheat)") {
        return "MMAB-C"
    } else if (name == "vegas (cheat)") {
        return "VEGAS-C"
    } else if (name == "monte carlo tree search (cheat)") {
        return "MCTS-C"
    } else if (name == "minimax") {
        return "MMAB-H"
    } else if (name == "vegas") {
        return "VEGAS-H"
    } else if (name == "monte carlo tree search") {
        return "MCTS-H"
    } else {
        return "UNKNOWN"
    }
}

/^\t/ {
    getline
    if ($8 == "0") {
        C = "1"
    } else {
        C = "0"
    }
    print genName($2) "-" $3 " " genName($5) "-" $6  " " C
}
