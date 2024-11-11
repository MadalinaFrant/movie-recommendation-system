# Movie Recommendation System

## Overview
This project implements a **Movie Recommendation System** in Java as part of a study on the effects of using design patterns and architectural patterns in software development. The system provides basic movie recommendations based on user-defined preferences (e.g. genre, rating). The focus is to compare two versions of the system: one using design patterns and architectural patterns, and another without any structured patterns, including anti-patterns. Through this project, we aim to analyze the impact of these patterns on various software quality metrics.

**Project Members:**  
- [@Madalina Frant](https://github.com/MadalinaFrant)  
- [@David Nitu](https://github.com/nitudavid)  

## Objectives
1. **Implement a Movie Recommendation System:** Develop a system that suggests movies based on predefined preferences.
2. **Analyze the Impact of Design Patterns and Architectural Patterns:** Compare the implementation of the system that used patterns and the one that does not.
3. **Compare Results Using Defined Metrics:** Evaluate the differences between implementations in terms of software quality, performance, and maintainability.

## System Functionality
The recommendation system will:
- Allow users to specify preferences, including genres, actors, and other relevant movie attributes.
- Suggest movies from a predefined list based on these preferences.
- Allow for the comparative analysis of recommendation quality and system performance.

## Design Patterns Used
In the version optimized with design patterns, the following patterns will be applied:

1. **Singleton Pattern:** To manage a single instance of shared resources, such as the database connection or the recommendation engine.
2. **Builder Pattern:** To simplify the construction of complex objects such as user profiles or preferences.
3. **Factory Pattern:** For creating different types of recommendation strategies (e.g. genre-based, popularity-based).
4. **Strategy Pattern:** For dynamically selecting recommendation strategies based on user input.
5. **Observer Pattern:** To update the recommendation engine whenever the user profile changes.

In the unstructured version, design patterns are intentionally avoided, and anti-patterns such as **Spaghetti Code** or **God Object** are introduced to compare their impact on maintainability and scalability.

## Architectural Patterns Used
The following architectural patterns will be utilized in the structured implementation:

1. **Layered Architecture:** The system will be organized into distinct layers, to improve modularity and to separate concerns.

2. **Event-Driven Architecture:** Uses events to trigger and communicate between components, allowing the recommendation engine to react to changes in user preferences or movie data asynchronously.

In contrast, the non-patterned version will lack these architectural structures, which is expected to increase code complexity and reduce modularity.

## Comparison Metrics
To objectively evaluate the effectiveness of design and architectural patterns, we will measure the following metrics:

1. **Execution Time:**  
   - Measure the time taken to generate recommendations, providing insights into the efficiency of each version.

2. **Memory Usage:**  
   - Record memory consumption under similar workloads to evaluate memory efficiency.

3. **Code Complexity:**  
   - Measure code complexity to assess maintainability and error risk.

4. **Code Readability (Lines of Code):**  
   - Evaluate the clarity and readability of code, which influences ease of understanding and maintenance.

5. **Modularity and Reusability:**  
   - Assess the modularity of components and the ease with which code can be reused in other projects.

6. **Scalability:**  
   - Quantify the effort required to introduce new features (e.g. additional recommendation types) in each version, reflecting the system’s scalability.

## Project Structure
The project consists of the following main modules:

- **Movie Database:** Manages movie data, including information on genres, ratings, and popularity.
- **User Preferences:** Captures and manages user preferences for recommendations.
- **Recommendation Engine:** Generates movie recommendations based on user preferences and movie data.

The **with-patterns** branch will contain the version of the system that uses design patterns and architectural patterns, while the **without-pattern** branch will contain the unstructured version.

