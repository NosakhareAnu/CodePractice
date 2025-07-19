# ----------------------------------------
# Prisoner's Dilemma Payoff Matrix
# ----------------------------------------
# Each tuple key represents the choices of two players: (Player1, Player2)
# 'C' means Cooperate, 'D' means Defect
# The values are the points each player earns for that combination.
payoffs = {
    ('C', 'C'): (3, 3),  # Both cooperate -> both get 3 points
    ('C', 'D'): (0, 5),  # Player1 cooperates, Player2 defects -> P1 gets 0, P2 gets 5
    ('D', 'C'): (5, 0),  # Player1 defects, Player2 cooperates -> P1 gets 5, P2 gets 0
    ('D', 'D'): (1, 1)   # Both defect -> both get 1 point
}

# ----------------------------------------
# Tit for Tat Strategy
# ----------------------------------------
# Always cooperate on the first move.
# Then, mimic the opponent's previous move.


def tit_for_tat(opponent_history):
    if not opponent_history:
        return 'C'  # No history = first move = cooperate
    return opponent_history[-1]  # Copy opponent's last move

# ----------------------------------------
# Tit for Two Tats Strategy
# ----------------------------------------
# Cooperate unless the opponent defected twice in a row.


def tit_for_two_tats(opponent_history):
    if len(opponent_history) < 2:
        return 'C'  # Not enough history = assume good = cooperate
    if opponent_history[-1] == 'D' and opponent_history[-2] == 'D':
        return 'D'  # Defect only if opponent defected twice in a row
    return 'C'  # Otherwise, cooperate


# ----------------------------------------
# Simulation Setup
# ----------------------------------------
rounds = 20               # Total number of rounds to simulate
history_1 = []            # History of moves made by Bot 1 (Tit for Tat)
history_2 = []            # History of moves made by Bot 2 (Tit for Two Tats)
score_1 = 0               # Total score for Bot 1
score_2 = 0               # Total score for Bot 2

# ----------------------------------------
# Game Loop: Simulate each round
# ----------------------------------------
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
    print(f"  Scores: Bot1 = {score_1}, Bot2 = {score_2}\n")

# ----------------------------------------
# Final Result
# ----------------------------------------
print("Final Scores:")
print(f"  Tit for Tat = {score_1}")
print(f"  Tit for Two Tats = {score_2}")
