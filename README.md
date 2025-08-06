#  Boolean || Learning - Fullstack Quiz App

An immersive quiz experience with a broadcast-inspired interface and personalized user stats. Designed for clarity, modularity, and delight. I wanted to learn to use effects so there are a few hidden surprises if you are fast enough & look hard enough.  I would definitely like to expand this out and keep playing.  I tried to create some equences through modals that I enjoyed and would like to do more of as well. Sky is the limit!

##  Tech Stack

- **Frontend**: React (Vite SPA) + JSX/CSS animations  
- **Backend**: Spring Boot + MySQL  
- **Communication**: RESTful APIs with DTO mapping and global exception handling  
- **Tooling**: IntelliJ IDEA, VS Code, Git/GitHub, Trello

## Preview Screenshots
-are available in the main folder of the repository if you want to see the different pages. 
- You can get an ERD Diagram here: https://drive.google.com/file/d/1_UrJYzUcad1nMR9V-wXml4oAxQWvAO7U/view?usp=sharing](https://drive.google.com/file/d/1D8IveDpbxEjF_NctjvCREfDmzkFckoYG/view?usp=sharing)
- Here is a wireframe that I will be improving on [https://drive.google.com/file/d/1rxO2c8DNkHmxNtSQbGvARlimJmCpGFcI/view?usp=sharing](https://drive.google.com/file/d/1IpoVGl_lnvSnZaFIn_oLGc9tQHga43GE/view?usp=sharing)

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
- 🏆 **User Stats Panel** – overview showing quiz performance and progress

>  designed to feel responsive

## Usage & UX Tips

- **Start a Quiz** – Choose a topic, hit “Begin Broadcast,” and dive into the challenge  
- **Answer Flow** – Questions animate in with visual feedback based on accuracy  
- **Ask the Tutor** – Tap for AI explanations when you're stumped or curious  
- **Feedback Effects** – Enjoy themed animations: teletype (text reveal), blipDrop (score blip), and glowPulse (positive reinforcement)  
- **View Your Stats** – Navigate to the user profile to track quiz history and performance highlights

> Designed for responsiveness across screen sizes.

##  Roadmap & Future Enhancements

- Add multiple-choice, fill-in-the-blank, and ranked difficulty question types  
- Implement JWT authentication and secure session handling  
- Build a full-featured admin dashboard for managing users, content, and quiz stats  
- Expand Gemini AI capabilities: let users ask custom questions mid-quiz  
- Enable filtering by quiz history, subject, score, and date  
- More animations: button rings, radar effects, glitch overlays

> At some point I would like to include videos  as well.  

##  Contributing

Boolean Learning thrives on collaboration, whether it’s refining questions, improving UX, or expanding backend features. To contribute:

1. Fork the repository  
2. Create a feature branch (`git checkout -b feature/your-cool-idea`)  
3. Commit your changes with clear messages  
4. Push to your fork and open a pull request

Please check out our [Contributor Guide](CONTRIBUTING.md) for design conventions, issue templates, and review etiquette.

 From frontend flair to backend logic, all signal boosters welcome!

---

## License
2025 Holmes Farm LLC
This project is licensed under the [MIT License](LICENSE).  
You’re free to use, modify, and share — just include proper attribution.

> “Knowledge wants to be shared. Code wants to be read.”

   
