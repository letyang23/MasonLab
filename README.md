# MasonLab: Multi-Agent Simulation for Evolutionary Games

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Project Structure](#project-structure)

## Introduction

This project is a Java-based multi-agent simulation framework designed to model and analyze various evolutionary game dynamics and aggregation behaviors. Using the MASON library, it allows for a detailed study of cooperative, competitive, and reproductive strategies among agents in a grid-based environment.

## 

## Features

- **Agent-Based Modeling**: Simulate complex interactions and behaviors among agents with distinct strategies.
- **Evolutionary Game Theory**: Implement agents with cooperative and competitive strategies, including defectors, cooperators, and more.
- **Memory-Based Decision Making**: Enable agents to make decisions based on previous encounters with other agents.
- **Random Movement & Aggregation**: Agents can navigate a grid environment with directional movement and aggregate with nearby agents.
- **Reproduction & Mutation**: Model the evolution of agents with resource-dependent reproduction, various survival strategies, and mutation.
- **Visualizations**: Real-time graphical output using Java Swing for tracking agent distribution, resource allocation, and strategy dynamics.

## Project Structure

- **agents**: Contains core classes defining agent behaviors, including random movement, aggregation, and freezing mechanisms.
- **evolutionaryGamesFinal**: Implements various strategies like cooperator, defector, and tit-for-tat, with files for agent interactions, environment setup, and strategy dynamics.
- **freezingAggregate**: A model where agents exhibit freezing and aggregation behaviors.
- **khModel**: Implements a simulation of the KH Dating Model, with additional frustration and attractiveness dynamics.
- **runTimeFile.txt** & **script.txt**: Configuration and parameter files for running automated simulation sweeps and specifying parameters.