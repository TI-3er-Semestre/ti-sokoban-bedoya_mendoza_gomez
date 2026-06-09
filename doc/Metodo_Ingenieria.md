# Engineering Method

---

# Phase 1: Problem Context and Definition

## Contextual Problem

Sokoban requires an automatic solving mechanism for the expert scenario.

If we process movements without keeping track of the visited states, the algorithm will fall into infinite loops.

Additionally, evaluating every possible game state from the beginning causes memory collapse.

## Problem Definition

We need to design an intelligent system capable of finding a winning sequence of moves automatically.

This system must prevent redundant calculations and ensure an optimal path using graph data structures.

## Functional Requirements

| ID | Requirement |
|----|-------------|
| RF1 | Execute automatic level solution using search algorithms |
| RF2 | Calculate shortest paths between generic interconnected elements |
| RF3 | Prevent infinite cycles during the game state exploration |
| RF4 | Manage memory efficiently during node traversal |

---

# Phase 2: Information Search

To solve the problem we researched graph theory and artificial intelligence applied to pathfinding.

## Topics Investigated

- Implicit vs explicit graphs for game state representation
- Adjacency lists and adjacency matrices
- Breadth-First Search (BFS) and Depth-First Search (DFS)
- Dijkstra algorithm for shortest paths in weighted graphs
- Floyd-Warshall algorithm for all-pairs shortest paths

## Complexity Analysis Found

| Algorithm | Time Complexity | Space Complexity |
|------------|----------------|------------------|
| BFS | O(V + E) | O(V) |
| DFS | O(V + E) | O(V) |
| Dijkstra | O((V + E) log V) | O(V) |
| Floyd-Warshall | O(V³) | O(V²) |

> Where:
>
> - **V** = number of vertices
> - **E** = number of edges

## Sources

- Class notes and lectures on discrete structures
- Official Java documentation for memory management
- Academic resources on state-space search and combinatorial explosion

---

# Phase 3: Idea Generation

## Brainstorming Technique Used

We listed several ways to implement graphs to find the best solution for the game.

## Proposed Ideas

### 1. Explicit Graph
Compute and store every possible board state before starting the search.

### 2. Implicit Graph
Generate game states dynamically on the fly only when a move is valid.

### 3. Random Walk Algorithm
Continue exploring until a winning state is randomly found.

### 4. Generic Graphs
Implement standard generic graphs (list and matrix) to solve secondary routing problems like Dijkstra.

---

# Phase 4: Preliminary Designs and Discarded Ideas

## Discarded Ideas

### Idea 1 — Explicit Graph

Discarded due to state explosion because generating all permutations of boxes consumes all available RAM.

### Idea 3 — Random Walk

Discarded because it is highly inefficient and does not guarantee a victory.

## Kept Ideas

- Idea 2 (Implicit Graph)
- Idea 4 (Generic Explicit Graphs)

## Preliminary Designs

### Implicit BFS Solver

Uses a queue to explore states level by level, ensuring the shortest path and a hash table to remember visited states.

### Implicit DFS Solver

Uses a stack to explore deep paths quickly using less initial memory but not guaranteeing the shortest path.

### Generic Graphs

An adjacency list paired with Dijkstra for sparse networks and an adjacency matrix paired with Floyd-Warshall for dense networks.

---

# Phase 5: Evaluation and Selection

## Evaluation Criteria

1. Memory efficiency: ability to run without causing memory overflow
2. Time efficiency: execution speed based on Big-O complexity
3. Optimality: guarantees finding the shortest sequence of moves
4. Implementation scalability: easy to adapt to other routing problems

## Evaluation Scale

| Value | Meaning |
|---------|---------|
| 1 | Low |
| 2 | Medium |
| 3 | High |

## Evaluation Table

| Alternative | Memory | Time | Optimality | Scalability | Total |
|------------|--------|------|------------|-------------|-------|
| Explicit Graph | 1 | 1 | 3 | 1 | 6 |
| Implicit BFS | 2 | 2 | 3 | 2 | 9 |
| Implicit DFS | 3 | 2 | 1 | 2 | 8 |

## Final Selection

The combination of **Implicit BFS** and **DFS** was selected for the automatic game solver because it provides the best balance between memory safety and optimal solutions.

Generic **adjacency lists** and **adjacency matrices** were selected to implement **Dijkstra** and **Floyd-Warshall** for scalable distance calculations.

---