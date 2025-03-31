# Support Bot

Support Bot is a Telegram bot that allows users to contact administrators for assistance. The bot forwards user messages to the admin and enables the admin to reply directly.

## Features

- Users can send messages to the bot, which are automatically forwarded to the admin.
- Admin can reply to forwarded messages, and the bot will send the response back to the user.
- Users can share their contact details.
- Language selection support.
- User data is stored in a database for better interaction.

## Technologies Used

- Java
- Telegram API (com.pengrad.telegrambot)
- PostgreSQL (JPA/Hibernate)
- Jakarta Persistence API (JPA)
- Maven
- Lombok

## Installation & Setup

### Prerequisites

- Java 17 or later
- PostgreSQL database
- Telegram Bot Token

### Steps to Run

1. **Clone the repository:**

   ```sh
   git clone https://github.com/your-repo/support-bot.git
   cd support-bot
   ```

2. **Configure the database:**

   - Create a PostgreSQL database.
   - Update `DB` configuration in `src/main/java/org/exp/config/DB.java`:
     ```java
     public static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("YourPersistenceUnit");
     ```

3. **Set the Telegram Bot Token:**

   - Replace `YOUR_BOT_TOKEN` in `BotRunner.java`:
     ```java
     TelegramBot bot = new TelegramBot("YOUR_BOT_TOKEN");
     ```

4. **Build and Run the bot:**

   ```sh
   mvn clean package
   java -jar target/support-bot.jar
   ```

## Usage

- **Users**: Send a message to the bot, and it will be forwarded to the admin.
- **Admin**: Reply to forwarded messages, and the bot will deliver the response to the user.
- **Language Selection**: Users can change their preferred language using commands.
- **Contact Sharing**: Users can share their phone numbers.

## Commands

| Command     | Description                       |
| ----------- | --------------------------------- |
| `/start`    | Start the bot and access the menu |
| `/help`     | Show available commands           |
| `/info`     | Get bot information               |
| `/language` | Change language preference        |

## Contributing

1. Fork the repository.
2. Create a new branch (`feature/your-feature`).
3. Commit your changes (`git commit -m 'Add new feature'`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contact

For support or inquiries, contact:

- Telegram: https\://t.me/HowdySupportBot
- Email: [suhrobqalandarov27@gmail.com](mailto\:suhrobqalandarov27@gmail.com)

