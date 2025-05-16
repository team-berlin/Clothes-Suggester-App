# Weather-Based Clothes Suggestion CLI App

This is a command-line interface (CLI) application that suggests what to wear based on the current weather conditions.  
It's designed as a simple and fun side project for squads to collaborate on, while applying practical development skills like API consumption, clean architecture, and logic-based decision making.

---

## Purpose

To help users decide what to wear by providing smart suggestions based on live weather data (temperature, wind, rain, etc.).

---

## Weather API

We use the [Open-Meteo API](https://open-meteo.com) to fetch real-time weather information including:

- Temperature
- Weather conditions (sunny, rainy, etc.)
- Wind speed
- Time of day

You can replace it with any alternative **free** weather API if needed.

---

## How It Works

1. The app reads the user’s location (latitude and longitude).
2. It fetches current weather data from the weather API.
3. Based on the data, it applies logic to recommend clothing items.

Example:
- **Cold (≤ 10°C)** → Wear: Jacket, hoodie, boots
- **Mild (11–20°C)** → Wear: Light sweater, jeans
- **Hot (≥ 21°C)** → Wear: T-shirt, shorts, sunglasses
- **Rainy** → Add: Umbrella, waterproof jacket

---

## Features

- Live weather integration using external API
- Rule-based outfit suggestions
- Simple and clean CLI interaction
- Easily extendable with more weather factors or outfit options

---

## Architecture

- **UI Layer**: Command-line input/output
- **Logic Layer**: Decision-making based on weather rules
- **Data Layer**: API communication to get weather data

All layers are separated for better maintainability and testability.

---
## 🎥 Demo Video

[Click here to watch the demo](https://drive.google.com/file/d/1qtS74bGKY3KDgpf1gMkwKfqM7nifttRo/view?usp=drive_link)

## How to Run

> Make sure you have Kotlin and an internet connection

```bash
# Compile the app
./gradlew build

# Run the CLI
./gradlew run
