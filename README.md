# Calebto_Problem

Description:

You are tasked with helping a user plan a trip through a collection of cities. Each city has a different cost to visit, potential reward for visiting, and may be connected to other cities with a certain travel cost. Your goal is to generate the best possible plan for the user to maximize rewards within a given timeframe.


Solution Approach:

Considering the given cities and its related costs been given I can visualize this in the form of Graph data structure wherein:

1. It has vertex
2. It has edges between the nodes (Representing cities)
3. It has weights (Represents costs between cities)


The algorithm that seems plausible to be used is Djikstras Algorithm which calculates the shortest path (in this case the cost) with additional maximum reward startegy.

Following are the components used:

ArrayList

Priority Queue
