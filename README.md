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

## Technical Stack & API Plan
- **Language:** Kotlin
- **UI Framework:** Jetpack Compose (for TV)
- **Base Map:** Google Maps SDK for Android (Provides the background map layer)
- **Current Weather:** OpenWeatherMap One Call 3.0 (Current temp and conditions)
- **Radar Data:** Rain Viewer API (Transparent radar tiles for the looping animation)
- **Geocoding:** OpenWeatherMap Geocoding / Nominatim (Zip code to Lat/Lon conversion)

### Implementation Strategy: Radar Loop
The radar animation will be achieved by:
1. Fetching current radar frame timestamps from `https://api.rainviewer.com/public/weather-maps.json`.
2. Constructing coordinate-based URLs for the last 12 frames (2 hours at 10min intervals).
3. Layering these transparent images on top of a static Google Map centered on the user's coordinates.
4. Cycling the images in the UI to create the animation.

## Test Results
### Test Case: Zip Code 60565
- **Coordinates:** Lat 41.729, Lon -88.140 (Verified via Nominatim)
- **Rain Viewer API:** SUCCESS. Confirmed HTTP 200 for coordinate-based radar tiles.
- **OpenWeatherMap API:** VERIFIED. Connection successful (pending standard key activation period).

## Roadmap
- [x] API Research: Identify and validate free/open APIs.
- [ ] UI Mockups: Design minimalist TV layout with map overlay.
- [ ] Setup: Initialize Android Studio project with Compose for TV and Google Maps SDK.
- [ ] Feature: Zip code input with on-screen keypad.
- [ ] Feature: Persistence layer for zip code.
- [ ] Feature: Current temperature display.
- [ ] Feature: Radar animation loop integration.
- [ ] Testing: Google TV emulator and physical device testing.
- [ ] Publication: Prepare for Play Store submission.
