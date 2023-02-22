<p align="center">
    <img align="center" src="/docs/image/logo.png" alt="Barattus icon">
    <h1 align="center">Barattus</h1>
</p>

<h3 align="center">A common place where to barter anything</h3>

## 🔍 Overview

The app currently supports two types of users:

- **Configurator:** defines the categories of the items that can be traded, as well as parameters for said trades (place, day and times).
- **End User:** can make offers and propose trades with other people, by selecting other's currently existing offers.

## 📚 How to use

You first need to set up both categories and trade parameters with a configurator account.
To create one you need to use the predefined configurator credentials from the login page:

- **Username:** *IngSoftware*
- **Password:** *Barattus2022*

After that you'll be prompted to create a new configurator account with a username and password of your choice.

To create a end user account, you can simply do that through the registration page.

## 🔨 Build

The recommended IDE for building this project is [IntelliJ Idea](https://www.jetbrains.com/idea/).

### Build requirements

* JDK 17 (recommended: [OpenJDK](https://jdk.java.net/17/))

### Build manually (without using an IDE)

If you want to run the project, simply use `gradlew run`. <br/>
To build a fat jar, use `gradlew shadowJar`. <br/>

## 🏋️ Author

Pluda Michael (727389) - a.k.a. MechaMic_38