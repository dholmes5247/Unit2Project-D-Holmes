#  Boolean || Learning - Fullstack Quiz App

An immersive quiz experience with a broadcast-inspired interface and personalized user stats. Designed for clarity, modularity, and delight. I wanted to learn to use effects so there are a few hidden surprises if you are fast enough & look hard enough.  I have plans to build up the subject and questions that can be offered along with the type of question, not just true/false.  I would like to create an interface for users to be able to upload questions (maybe check them with AI or gate with roles) discuss concepts or further research.  Eventually I would like to incorporate video clips and create the ability to offer subjects on football plays, or skydiving techniques etc.  Sky is the limit!

##  Tech Stack

- **Frontend**: React (Vite SPA) + JSX/CSS animations  
- **Backend**: Spring Boot + MySQL  
- **Communication**: RESTful APIs with DTO mapping and global exception handling  
- **Tooling**: IntelliJ IDEA, VS Code, Git/GitHub, Trello

## Preview Screenshots
-are available in the main folder of the repository if you want to see the different pages.  

##  Setup & Installation

> To run locally, clone the repo and follow the setup steps in each folder.

### Backend (Spring Boot + MySQL)
1. Set up MySQL and create a schema (e.g., `quiz_app_db`)
2. Update database credentials in `application.properties`
3. Run the Spring Boot app

### Starter Data
To preview the app or share it locally
1. Ensure your database schema is initialized.
2. Run the seed script:
3. There is script in Seed Folder starter-data.sql
   > “Whether you’re a curious contributor or just Dad checking out the build, load the starter data and jump right into Boolean Learning!”
   
### Frontend (React + Vite)
1. Navigate to `/frontend`
2. Install dependencies  
   ```bash
   npm install
3. Start the dev Server
   npm run dev
   Make sure the front and back are running in compatibel ports for comms in API


## Key Features

- 📺 **Broadcast-Inspired UI** – Glowing dials, transitions, leaderboard & user stats bring game show vibes
- 🎓 **Tutor Integration** – AI-powered explanations enrich learning during quizzes  
- 💡 **Feedback Animations** – Positive reinforcement with custom teletype, blipDrop, and emotion-driven cues  
- 🎯 **Dynamic Question Flow** – Tracks accuracy and offers targeted encouragement  
- 🏆 **User Stats Panel** – ESPN-style overview showing quiz performance and progress

> Every interaction is designed to feel responsive, delightful, and emotionally intelligent.

## Usage & UX Tips

- **Start a Quiz** – Choose a topic, hit “Begin Broadcast,” and dive into the challenge  
- **Answer Flow** – Questions animate in with visual feedback based on accuracy  
- **Ask the Tutor** – Tap for AI explanations when you're stumped or curious  
- **Feedback Effects** – Enjoy themed animations: teletype (text reveal), blipDrop (score blip), and glowPulse (positive reinforcement)  
- **View Your Stats** – Navigate to the user profile to track quiz history and performance highlights

> Designed for responsiveness and delight across screen sizes.

##  Roadmap & Future Enhancements

- Add multiple-choice, fill-in-the-blank, and ranked difficulty question types  
- Implement JWT authentication and secure session handling  
- Build a full-featured admin dashboard for managing users, content, and quiz stats  
- Expand Gemini AI capabilities: let users ask custom questions mid-quiz  
- Enable filtering by quiz history, subject, score, and date  
- More animations: button rings, radar effects, glitch overlays

> Boolean Learning is designed to grow — from capstone to community-driven platform.

##  Contributing

Boolean Learning thrives on collaboration — whether it’s refining questions, improving UX, or expanding backend features. To contribute:

1. Fork the repository  
2. Create a feature branch (`git checkout -b feature/your-cool-idea`)  
3. Commit your changes with clear messages  
4. Push to your fork and open a pull request

Please check out our [Contributor Guide](CONTRIBUTING.md) for design conventions, issue templates, and review etiquette.

 From frontend flair to backend logic — all signal boosters welcome!

---

## License
2024 Holmes Farm LLC
This project is licensed under the [MIT License](LICENSE).  
You’re free to use, modify, and share — just include proper attribution.

> “Knowledge wants to be shared. Code wants to be read.”

   
