# Android Data Fetching and Registration App

## Description

This Android project consists of three main features:
1. Fetching and displaying data from a URL.
2. A registration page to store user data in an SQLite Database.
3. Displaying registered data and making a phone call to a registered number.

## Features

- **Fetch and Display Data**: Fetches data from `https://jsonplaceholder.typicode.com/posts` and displays it in a RecyclerView.
- **User Registration**: A registration page where users can enter their Name, Email, and Mobile number, which gets stored in an SQLite Database.
- **Display Registered Data**: A page that displays the registered users' data and provides an option to make a phone call to the registered number.


## Installation

### Prerequisites

- Android Studio
- JDK 8 or above
- Android SDK

### Steps

1. **Clone the repository**:


2. **Open the project** in Android Studio:
    - Open Android Studio.
    - Select `File > Open...`.
    - Navigate to the cloned project directory and click `OK`.

3. **Build the project**:
    - Click on the `Build` menu and select `Rebuild Project`.

4. **Run the app**:
    - Connect your Android device or start an emulator.
    - Click the `Run` button or select `Run > Run 'app'`.

## Usage

1. **Fetching and Displaying Data**:
    - Open the app.
    - Navigate to the data fetching section to view data from `https://jsonplaceholder.typicode.com/posts`.

2. **User Registration**:
    - Go to the registration page.
    - Enter Name, Email, and Mobile number.
    - Click the `Register` button to save the data.

3. **Display Registered Data**:
    - Navigate to the page displaying registered data.
    - The data will be displayed in a list.
    - Click on a phone number to initiate a phone call.

## Code Structure

- **MainActivity**: Handles the fetching and displaying of data from the URL.
- **RegistrationActivity**: Manages user registration and saving data to SQLite.
- **DisplayActivity**: Displays registered data and allows making phone calls.
- **DatabaseHelper**: Handles SQLite database operations.

## Dependencies

- RecyclerView
- SQLite

