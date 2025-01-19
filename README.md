# Movie Recommendation System

## Overview
This project implements a **Movie Recommendation System** in Java, featuring a minimal web-based interface. Users can input their movie preferences, including likes, dislikes, preferred actors, genres, and directors. Based on this input, the system generates recommendations, supporting two modes:

1. **Classic Recommendation**: A single user's preferences are considered to generate recommendations.
2. **Movie Night**: Two users enter their preferences, and recommendations are tailored to accommodate both.

The project explores the impact of using **design patterns** and **architectural patterns** on software quality by comparing two implementations: one employing structured patterns and another without structured design, including anti-patterns.

**Project Members:**  
- [@Madalina Frant](https://github.com/MadalinaFrant)  
- [@David Nitu](https://github.com/nitudavid)  

## Objectives
1. **Implement a Movie Recommendation System:** Create a system that provides movie suggestions based on user-defined preferences entered through a web interface.
2. **Define and Implement Two Versions of the System:** Develop one version using design patterns and architectural patterns, and another without structured patterns, to analyze their impact on quality metrics.
3. **Compare Results Using Defined Metrics:** Evaluate the differences between implementations in terms of software quality, performance, and maintainability.

## System Functionality
The recommendation system will:
- Allow users to specify preferences:
  - **Movie Likes and Dislikes**: Ordered lists indicating the strength of preference for each movie.
  - **Preferred Attributes**: Genres, actors, and directors.
- Provide movie suggestions based on:
  - The entered preferences for a single user (**Classic**).
  - A combination of preferences for two users (**Movie Night**).

## Design Patterns Used
In the version optimized with design patterns, the following patterns will be applied:

1. **Chain of Responsibility**: To process user preferences in a modular way, allowing each preference type (e.g. genre, actors) to be handled by dedicated handlers.
2. **Strategy Pattern**: To select and apply different recommendation algorithms (e.g. genre-based, popularity-based) dynamically based on user inputs.
3. **Builder Pattern**: To construct and manage complex objects like user preference profiles efficiently within a single request.

In the unstructured version, design patterns will be intentionally avoided, and anti-patterns such as **Spaghetti Code** or **God Object** will be introduced to compare their impact on maintainability and scalability.

## Architectural Patterns Used
The **Layered Architecture** will be employed in the structured implementation to:
- Separate concerns into layers (e.g. data access, business logic, and presentation).
- Ensure modularity and simplify testing.

The unstructured version will not use this architecture, highlighting the difficulties of working with tightly-connected, monolithic systems.

## Comparison Metrics
To assess the impact of design and architectural patterns, the following metrics will be used to compare the two implementations:

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

- **Movie Database:** Stores movie data, including information on genres, ratings, and popularity. The IMDb Movies Dataset Based on Genre dataset will be used to obtain movie information (available on Kaggle).
- **User Preferences:** Captures and manages user preferences for recommendations.
- **Recommendation Engine:** Generates movie recommendations based on user preferences and movie data.

The **with-patterns** branch will contain the version of the system that uses design patterns and architectural patterns, while the **without-pattern** branch will contain the unstructured version.
