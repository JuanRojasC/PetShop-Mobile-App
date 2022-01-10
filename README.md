# PetShop Mobile App
Made with Java in Android Studio, this project seeks to simulate a store to purchase products and services for pets, as well as all the management or CRUD of products, services and stores available.

![final project](https://github.com/JuanRojasC/PetShop-Mobile-App/blob/master/PetShop%20Mobile%20App.gif)

<h2>UI</h2>
The UI was built with a Navigation Drawer Activity as the main activity and fragments for the different views. Different custom icons and images were also designed using figma for this. Animations were used for those asynchronous actions that required loading times, as well as those to reflect the final result of an operation. Also with the use of the mapbox dependency, the location of each store can be seen through a map that is displayed by clicking on the "visit" button.

<h2>Data & DB</h2>
SQLite was initially used as a local database, but once the project was scaled, an APEX instance was used in Oracle Cloud, with which we interacted with the volley library for API calls, so we could query, create, modify or delete data from the cloud database, all through our UI.
