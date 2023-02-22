<p align="center">
    <img align="center" src="/docs/image/logo.png" alt="Barattus icon">
    <h1 align="center">Barattus</h1>
</p>

<h3 align="center">A common place where to barter anything</h3>

## 🔍 Overview

The app currently supports two types of users:

- **Configurator:** defines the categories of the items that can be traded, as well as parameters for said trades (place, day and times).
- **End User:** can make offers and propose trades with other people, by selecting other's currently existing offers.

### 🔑 Login and Registration

The program allows a new user to register, and once registered, allows access to the application with his own profile.

A user can register as a configurator by entering the predefined credentials on the login screen, 
to then be sent to the registration page where he can enter the username and password he wishes to use.

> The predefined credentials for registering as a configurator are:
> - **Username:** *IngSoftware*
> - **Password:** *Barattus2022*

### 🗂️ Category management

In the **"Categories List"** section, a configurator can set the hierarchies and related sub-categories present in the application.

Inserting a subcategory is exactly the same as inserting a hierarchy. The difference is that a category must be unique 
only within the hierarchy it belongs to, while a hierarchy must be unique within the entire program.

Creating a native field for the hierarchy/category requires entering an identifying name, the type of field 
(for now the only one present is `STRING`), and if this is required.

#### 📤 Loading from external file

It is possible to load the whole hierarchical structure of the categories from a single file.
In the **"Category List"** section, select *"Load from file"*. The file must be of type `.json`, otherwise it will not be accepted.

A *hierarchy or subcategory* is characterized by:

- `id:` unique identifier of the category, and **it must be the combination** of both the hierarchy and category names, separated by a dot (e.g. `hierarchyName.categoryName`).
- `hierarchyName:` name of the belonging hierarchy (for a hierarchy it corresponds to itself)
- `parentName:` name of the category to which it belongs (for a hierarchy it is `null`)
- `categoryName:` name of the category
- `description:` description of the category
- `nativeFields:` list of native fields of the category

A *native field* is characterized by:

- `name:` field name
- `fieldType:` field type (currently only `STRING`)
- `isMandatory:` if the field is mandatory

Base structure of a generic file `category-data.json`:

```json
{
    "categories": [ "{Put the categories inside}" ]
}
```

Example of **Hierarchy**:
```json
{
  "id": "Sport.Sport",
  "hierarchyName": "Sport",
  "parentName": null,
  "categoryName": "Sport",
  "description": "Clothing, equipment and accessories",
  "nativeFields": [
    {
      "name": "Conservation status",
      "fieldType": "STRING",
      "isMandatory": true
    },
    {
      "name": "Description",
      "fieldType": "STRING",
      "isMandatory": false
    }
  ]
}
```

Example of **Subcategory**:
```json
{
  "id": "Sport.Bikes",
  "hierarchyName": "Sport",
  "parentName": "Sport",
  "categoryName": "Bikes",
  "description": "Road bikes, mountain bikes, gravel bikes, e-bikes...",
  "nativeFields": [
    {
      "name": "Wheel inches",
      "fieldType": "STRING",
      "isMandatory": false
    },
    {
      "name": "Brake type",
      "fieldType": "STRING",
      "isMandatory": true
    }
  ]
}
```

### ⚙️ Trade parameters management

In the **"Trade Parameters"** section, a configurator can set the parameters relating to the exchanges, such as the place 
and the expiry days of the exchange proposals, the places and days, and the permitted time slots.

#### 📤 Loading from external file

It is possible to load all the parameters of the exchanges from a single file.
Inside the section **“Trade Parameters”**, select *“Load from file”*. The file must be of type `.json`, otherwise it will not be accepted.

The parameters of the exchanges are composed of:

- `square:` the square where the trades are concluded
- `places:` the meeting places for exchanges
- `days:` the days to trade (`MONDAY`, `TUESDAY`, `WEDNESDAY`, `THURSDAY`, `FRIDAY`, `SATURDAY`, `SUNDAY`)
- `hourIntervals:` hourly intervals where trades are allowed (must be reported as demonstrated below)
- `expirationDays:` days for the expiration of the exchange proposals

Example of **Subcategory**:

Basic structure and example of a generic file `trade-params-data.json`:
```json
{
  "tradeParams": {
    "square": "Brescia",
    "places": [
      "Piazzale Kossuth, zona nord del parcheggio",
      "Parco Benedetto Castelli, Mompiano",
      "Parco Nikolajewka, Mompiano"
    ],
    "days": [
      "THURSDAY",
      "FRIDAY"
    ],
    "hourIntervals": [
      "10:00 - 12:00",
      "17:00 - 19:30"
    ],
    "expirationDays": 7
  }
}
```

### 🏷️ Offer management

A user can create exchange offers, and can view his own offers (open and not) and those of others (only open ones).

A new offer can be published through the **“Publish Offer”** section.
The user must choose the hierarchy and category of the product to be exchanged, and fill in all the mandatory fields (indicated with an asterisk).

The offers can be viewed in the **“Offer List”** section.
By setting the hierarchy and the desired category it is possible to filter all the active offers at that moment, while by selecting *“Your offers”* you can see all your current and past offers.

By choosing an offer in the list, you can see the details by selecting *"View selected offer"*. You can then withdraw the offer (if the offer is yours) or propose an exchange (if someone else's).

### 🤝 Trade management

A user can make exchange proposals, and can agree with the other user how the exchange must take place.

All the proposals made and/or received are visible in the **“Trades”** section.
You can filter trade proposals based on their status (`ACCEPTED`, `EXPIRED`, `ONGOING`, `UNCONFIRMED`, `REJECTED`).

#### Proposing a trade

Once the user has chosen an exchange offer he is interested in from the **"Offer List"** section, he must select the item *"Propose exchange"*.<br/>
At this point the user must select one of his compatible offers from the list with the one chosen in order to be able to make the proposal. Once again select *“Propose trade”*.<br/>
The proposal has been made, and now it's up to the other user to either confirm the exchange proposal or reject it.

#### Lifecycle of a trade proposal

The user who receives an exchange proposal can choose whether to reject or confirm it. 
If he wants to confirm it, he must also choose the conditions (place, day and time) to be able to carry out the exchange.

If the proposal is confirmed, it is up to the user who made it to decide whether to accept the conditions chosen 
by the other user or whether to propose alternatives, and so on.

## 🔨 Build

The recommended IDE for building this project is [IntelliJ Idea](https://www.jetbrains.com/idea/).

### Build requirements

* JDK 17 (recommended: [OpenJDK](https://jdk.java.net/17/))

### Build manually (without using an IDE)

If you want to run the project, simply use `gradlew run`. <br/>
To build a fat jar, use `gradlew shadowJar`. <br/>

## 🏋️ Author

Pluda Michael (727389) - a.k.a. MechaMic_38