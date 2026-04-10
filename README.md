# Google TV Weather App

A minimalist weather application for Android/Google TV.

## Project Vision
Allow users to enter a US zip code and view the current temperature (Fahrenheit) and a looping radar animation for the last 2 hours.

## Requirements
- **Platform:** Android / Google TV (Play Store target)
- **Input:** US Zip Code (On-screen numeric keypad)
- **Output:**
    - Current Temperature (°F)
    - Looping Radar Animation (Last 2 hours)
- **Persistence:** Remember the last entered zip code for quick startup.
- **Design:** Minimalist visual style.

## Technical Stack
- **Language:** Kotlin
- **UI Framework:** Jetpack Compose (for TV)
- **Weather API:** OpenWeatherMap (Current Temp) & Rain Viewer (Radar Animation) [Tentative]

## Roadmap
- [ ] API Research: Identify free/open API providing both temp and looping radar.
- [ ] UI Mockups: Design minimalist TV layout.
- [ ] Setup: Initialize Android Studio project with Compose for TV.
- [ ] Feature: Zip code input with on-screen keypad.
- [ ] Feature: Persistence layer for zip code.
- [ ] Feature: Current temperature display.
- [ ] Feature: Radar animation integration.
- [ ] Testing: Google TV emulator and physical device testing.
- [ ] Publication: Prepare for Play Store submission.
