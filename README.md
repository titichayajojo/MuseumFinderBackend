
# Museum Finder

Museum Finder is a web application developed using Java and Spring Boot, with MariaDB as the database. The application allows users to find museums based on location and other search criteria.

## Features

- **Museum Search**: Users can search for museums by name, location, and other filters.
- **Museum Details**: Provides detailed information about each museum, including location, hours of operation, and exhibits.
- **User Authentication**: Secure user registration and login.
- **Responsive Design**: The application is designed to be responsive, working seamlessly across various devices.

## Installation

### Prerequisites

- Java 8 or higher
- Apache Maven
- MariaDB

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/titichayajojo/museum-finder.git
   cd museum-finder
   ```

2. Configure the database:
   - Set up a MariaDB instance.
   - Create a database for the application.
   - Update the `application.properties` file with your database credentials.

3. Build the application:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

5. Access the application:
   Open your browser and go to `http://localhost:8080`.

## Usage

1. **Search Museums**: Use the search bar on the homepage to find museums.
2. **View Details**: Click on any museum from the search results to view more details.
3. **User Accounts**: Register or log in to save your favorite museums and write reviews.


## Acknowledgements

- [Spring Boot](https://spring.io/projects/spring-boot) - The framework used.
- [MariaDB](https://mariadb.org/) - The database used.

