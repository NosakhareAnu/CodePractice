# the rules for the game

payoffs = {
    ('C', 'C'): (3, 3),
    ('C', 'D'): (0, 5),
    ('D', 'C'): (5, 0),
    ('D', 'D'): (1, 1)
}

# creating tit for tat's strategy
# cooperate on the first move, mimic the opponents previous move


def tit_for_tat(opponent_history):
    if not opponent_history:
        return 'C'
    return opponent_history[-1]


# creating tit for two tats strategy
# cooperate unless the opponent defects twice in a row
def tit_for_two_tats(opponent_history):
    if len(opponent_history) < 2:
        return 'C'
    if opponent_history[-1] == 'D' and opponent_history[-2] == 'D':
        return 'D'
    return 'C'


# simulation


rounds = 20
history_1 = []
history_2 = []
score_1 = 0
score_2 = 0


# game loop

for i in range(rounds):
    # Each bot chooses a move based on the opponent's history
    move_1 = tit_for_tat(history_2)         # Bot 1 reacts to Bot 2's history
    move_2 = tit_for_two_tats(history_1)    # Bot 2 reacts to Bot 1's history

    # Record the moves to their respective histories
    history_1.append(move_1)
    history_2.append(move_2)

    # Look up the payoff for this pair of moves and update scores
    s1, s2 = payoffs[(move_1, move_2)]
    score_1 += s1
    score_2 += s2

    # Print round result
    print(f"Round {i+1}:")
    print(f"  Tit for Tat        -> {move_1}")
    print(f"  Tit for Two Tats   -> {move_2}")


print("Final Scores:")
print(f"  Tit for Tat = {score_1}")
print(f"  Tit for Two
 Tats = {score_2}")

# ----------------------------------------
# Final Result
# ----------------------------------------