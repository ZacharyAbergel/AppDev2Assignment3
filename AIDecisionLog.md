# Game Backlog — AI Decision Log

## Document Purpose

This document records the important architectural, design, implementation, and troubleshooting decisions made while developing the Game Backlog application. The entries use an Architecture Decision Record (ADR) format and describe how AI assisted the decision-making process.

The prompts shown below are representative summaries of the conversations used during development. They preserve the intent of the AI interactions but are not presented as word-for-word transcripts.

## Project Context

- **Application:** Game Backlog Tracker
- **Platforms:** Android and Desktop (JVM)
- **Language:** Kotlin
- **UI framework:** Compose Multiplatform with Material 3
- **Navigation:** Navigation 3
- **Image loading:** Coil 3
- **State:** Compose observable state shared through a provider
- **Developer:** Zachary

---

## ADR-001: Select a Game Backlog Tracker

- **Status:** Accepted
- **Date:** October 2026

### Context

The assignment required a multiplatform, multi-screen application that used navigation, images, user interaction, and state. The project also needed to go beyond a basic classroom example through substantial AI-assisted development.

### Representative AI Prompt

> Suggest application ideas that satisfy the assignment requirements and provide enough room for useful features beyond a basic multi-screen application.

### Options Considered

1. Event-focused calendar application
2. Game backlog tracker
3. Habit or personal-goal tracker
4. Media watch-list application

### Decision

Build a Game Backlog Tracker.

### Rationale

I liked the idea of a game backlog tracker, it seemed more unique than the other ones it recommended like another event tracker.

### Consequences

- The application required a `Game` model and shared game collection.
- The UI needed screens for adding games, viewing the backlog, viewing details, and displaying project information.
- Additional features such as search, status filters, sorting, and total-hours calculations could be added without changing the core concept.

---

## ADR-002: Target Android and Desktop

- **Status:** Accepted
- **Date:** October 2026

### Context

The assignment required Android plus one additional platform. The second platform could be Desktop or Web.

### Representative AI Prompt

> Compare Desktop and Web as the second platform for this Compose Multiplatform assignment. Which choice will be simpler and more reliable with the available development environment?

### Options Considered

1. Android and Desktop (JVM)
2. Android and Web

### Decision

Use Android and Desktop (JVM).

### Rationale

I don't like web so I didn't want anything to do with it, desktop just seems more simple to load up

### Consequences

- The project contains separate `androidApp` and `desktopApp` entry-point modules.
- Most screens and business logic remain in the shared module.
- Platform-specific dependencies, such as Coil's network implementation, are configured for Android and JVM separately.

---

## ADR-003: Use Navigation 3 with Serializable Routes

- **Status:** Accepted
- **Date:** October 2026

### Context

The application required navigation between four screens while remaining compatible with both Android and Desktop. The details screen also needed information about the selected game.

### Representative AI Prompt

> Using the router example from class, create a multiplatform router for Add Game, Game Backlog, Game Details, and About screens. The routes should support back navigation and passing game information to the details screen.

### Options Considered

1. Manually switch screens with a single state variable
2. Use Navigation 3 with a shared back stack
3. Create separate navigation implementations for Android and Desktop

### Decision

Use Navigation 3 with a serializable sealed `AppRoute` class, `rememberNavBackStack`, and a small `Navigator` wrapper.

### Rationale

Using navigation 3 is required as it was taught in class

### Consequences

- Every destination must be represented by an `AppRoute` subtype.
- Each route must be registered in `SavedStateConfiguration`.
- `GameDetails` carries the selected game's values as route arguments.
- Navigation behavior is shared across Android and Desktop.

---

## ADR-004: Use a Shared Main Layout

- **Status:** Accepted
- **Date:** October 2026

### Context

Every screen needed a consistent structure and access to the main navigation destinations. Repeating the same scaffold code in every screen would make the UI harder to maintain.

### Representative AI Prompt

> Apply the layout pattern taught in class to the game backlog application. Every screen should use the same top app bar and bottom navigation bar while keeping its own content.

### Options Considered

1. Give every screen its own independent layout
2. Create a reusable `MainLayout` using `Scaffold`
3. Place the top and bottom bars directly inside the router

### Decision

Create a reusable `MainLayout` composable containing `SharedTopBar` and `SharedBottomBar`.

### Rationale

Again, using the shared folder is required as it was taught in class

### Consequences

- Changes to the app bars apply to every screen.
- Scaffold padding prevents content from being hidden under the bars.
- The current route is passed to the bottom bar so the active destination can be highlighted.
- The top bar only displays a back button when a previous route exists.

---

## ADR-005: Share Game State Through a Provider

- **Status:** Accepted
- **Date:** October 2026

### Context

Games added on the form screen needed to appear immediately on the backlog screen. Passing the game list through every composable and route would create unnecessary parameters.

### Representative AI Prompt

> Create a shared provider that stores games and can be accessed from all screens. Adding or removing a game should automatically update the Compose interface.

### Options Considered

1. Store a separate list inside each screen
2. Pass the list and callbacks through every composable
3. Store a `GameProvider` in a composition local
4. Introduce a full dependency-injection framework

### Decision

Create one `GameProvider` in the router, store games in `mutableStateListOf`, and expose it through `LocalGameProvider`.

### Rationale

This approach provided observable shared state with relatively little code.

### Consequences

- Screens recompose automatically when games are added or removed.
- The provider remains shared while the router stays in composition.
- Attempting to use `LocalGameProvider` outside its provider produces a clear error.
- Games currently exist only for the active application session.

---

## ADR-006: Store Hours Played as an Integer

- **Status:** Accepted
- **Date:** October 2026

### Context

The application needed to record playtime. Allowing arbitrary text or fractional values would complicate validation and total-hours calculations.

### Representative AI Prompt

> Make hours played an `Int` and only allow the user to enter the number of hours they have in the game. How should the Compose form validate this?

### Options Considered

1. Store the value as unrestricted text
2. Store fractional hours as a `Double`
3. Store whole hours as an `Int`

### Decision

Store `hoursPlayed` as an `Int` and accept only digit characters in the form.

### Rationale

Using whole hours with an int instead of a string makes it easier to sort by the hours played across all games

### Consequences

- Negative and decimal values cannot be entered.
- The Add Game button remains disabled until the value is valid.
- Games can be sorted by hours played.
- Total playtime can be calculated with `sumOf`.

---

## ADR-007: Render the Backlog with a LazyColumn

- **Status:** Accepted
- **Date:** October 2026

### Context

The backlog could grow as the user added games. The list needed to remain usable while supporting expandable cards, stable item identity, search, filtering, sorting, and removal.

### Representative AI Prompt

> Display the game collection using a LazyColumn similar to the list examples taught in class. Include search, status filtering, sorting, expandable cards, and an empty state.

### Options Considered

1. Use a regular `Column` with `verticalScroll`
2. Use a `LazyColumn`
3. Display only one game at a time

### Decision

Use a `LazyColumn` and identify each item with the game's unique ID.

### Rationale

LazyColumn is easy to use and we've gone over it before so it was an easy choice

### Consequences

- Search and status filters are applied before the list is rendered.
- Games can be sorted alphabetically or by descending hours played.
- Only one card's ID is stored as the expanded item.
- Separate messages are shown for an empty backlog and for filters with no matches.

---

## ADR-008: Load Cover Images with Coil 3

- **Status:** Accepted
- **Date:** October 2026

### Context

The assignment required images, and users needed to provide a cover-image URL for each game. Compose's standard resource painter does not download remote images.

### Representative AI Prompt

> Add remote cover images to the details and backlog screens in this Compose Multiplatform project. The image should show a local fallback when loading fails.

### Options Considered

1. Include only bundled local images
2. Require users to select local files
3. Load image URLs using Coil 3

### Decision

Use Coil 3 `AsyncImage` with a local game-controller drawable as the placeholder and error image.

### Rationale

The AI recommended using coil3 to store the images used by the games inside of the backlog list, which allows the user to just put a link to whatever image they want to use instead of needing to download it to use in the app.

### Consequences

- `coil-compose` is included in shared code.
- Network implementations are included for Android and Desktop/JVM.
- Android requires the `INTERNET` permission.
- Invalid URLs do not crash the UI because a fallback drawable is displayed.

---

## ADR-009: Use Android Vector XML Instead of SVG

- **Status:** Accepted after troubleshooting
- **Date:** October 7, 2026

### Context

Material icons were originally downloaded as SVG files. The Desktop application could display them, but the Android application closed immediately after launch.

### Representative AI Prompt

> The Android emulator starts, but Assignment3 cannot open. Examine the Logcat output and identify the cause of the crash.

### Evidence

Logcat reported:

```text
FATAL EXCEPTION: main
java.lang.IllegalStateException: Android platform doesn't support SVG format.
```

### Options Considered

1. Remove the icons
2. Use Material icon dependencies instead of drawable resources
3. Convert the SVG files to Android Vector Drawable XML files
4. Use PNG images

### Decision

Replace the SVG icons with Android Vector Drawable XML resources while preserving their resource names.

### Rationale

Tried using svg icons, which worked for the desktop app but now the android version, so they needed to be replaced with xml versions so the android app would load

### Consequences

- Android starts successfully.
- The shared UI continues to use the same icon references.
- Future icons must be added in a format supported by every target.
- Logcat filtering was established as the preferred method for diagnosing runtime crashes.

---

## ADR-010: Keep Version 1 Data In Memory

- **Status:** Accepted for the assignment version
- **Date:** October 2026

### Context

Persistent storage was considered so games could remain after the application closed. A shared text or XML file initially appeared to be a simple solution, but Android and Desktop use different safe storage locations and file APIs.

### Representative AI Prompt

> Can the games be retained when the app is loaded again, possibly using a text or XML file? If persistence is not already present, how much additional work would it require across Android and Desktop?

### Options Considered

1. Keep the collection in memory
2. Implement platform-specific text or XML files
3. Serialize games as JSON with platform-specific storage paths
4. Add a multiplatform settings or database library

### Decision

Keep the assignment version in memory and document persistence as a future improvement.

### Rationale

Persistence was not required but it would be easy to implement later on, probably with a text or xml file that just saves the users previous entries and loads that on start-up

### Consequences

- Games are cleared when the application process closes.
- The limitation is stated in the README and provider documentation.
- A future version could add JSON serialization, multiplatform settings, or a database without redesigning the screens.

---

## ADR-011: Use KDoc and a Project README

- **Status:** Accepted
- **Date:** October 8, 2026

### Context

The final submission required documentation. Important architecture and behavior needed to be understandable without adding comments to every obvious UI statement.

### Representative AI Prompt

> Review the project and identify what documentation should be added to each important file. Document the architecture, parameters, state, navigation, and non-obvious logic without over-commenting basic Compose layout code.

### Options Considered

1. Add comments to nearly every line
2. Document only the README
3. Add KDoc to important classes and functions plus focused inline comments

### Decision

Use KDoc for models, providers, navigation, layouts, screens, and helper composables. Use inline comments only for validation, filtering, stable list keys, and shared-state creation. Expand the README with features, technology, structure, run instructions, and known limitations.

### Rationale

KDoc explains the purpose and contract of important components while keeping implementation code readable. 

### Consequences

- The project is easier to review and maintain.
- Public functions and important private helpers explain their parameters and behavior.
- The README provides a single overview for running and evaluating the project.

---

## Overall AI Contribution

AI was used throughout the project for:

- Generating and comparing application concepts
- Producing the WBS and Gantt-chart plan
- Designing shared navigation and route serialization
- Adapting classroom layout examples into a reusable application scaffold
- Designing observable shared state
- Implementing form validation and integer-only playtime
- Building dynamic list filtering, sorting, expansion, and removal
- Configuring Coil for Android and Desktop
- Diagnosing Gradle, dependency, and Android runtime problems
- Interpreting Logcat and resolving the unsupported SVG crash
- Reviewing source files and preparing KDoc and README documentation
- Comparing estimated effort with actual implementation work

The developer remained responsible for selecting the final design, applying the generated code, testing both platforms, reporting errors, evaluating proposed fixes, and confirming that the completed application behaved correctly.

## Final Outcome

The completed application exceeds the basic assignment requirements by providing:

- Four navigable screens
- Shared Android and Desktop UI
- Shared observable game state
- Validated user input
- Remote images with failure handling
- Dynamic list rendering
- Search, filtering, and sorting
- Expandable game cards
- Game removal
- Reusable application layout
- Accessible icon descriptions
- Detailed source and project documentation