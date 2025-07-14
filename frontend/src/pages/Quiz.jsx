import QuestionList from '../components/QuestionList/QuestionList';
import React, { useContext, useState, useEffect, useRef } from 'react';
import { AuthContext } from '../context/AuthContext';
import './Quiz.css'; 

function Quiz() {
  const { userName, schoolName } = useContext(AuthContext); // COntext -take user imfo from AuthContext-glabal state
  const [score, setScore] = useState(0);  // Uses state for the Score state
  const [quizFinished, setQuizFinished] = useState(false); // Uses State for the quiz complete
  const audioRef = useRef(null); // play audio while quizing
  const [selectedSubject, setSelectedSubject] = useState('');


useEffect(() => {
  const audio = new Audio('/music/to-frighten-121407.mp3');
  audio.loop = true;
  audio.volume = 0.8;
  audioRef.current = audio;

  audio.play().catch((err) => {
    if (err.name !== 'AbortError') {
      console.warn('Audio play failed:', err);
    }
  });

  return () => {
    if (audioRef.current) {
      audioRef.current.pause();
      audioRef.current.currentTime = 0;
    }
  };
}, []);

// useEffect will run at quizFinished, score, UserName, or schoolName changes

useEffect(() => {
  if (quizFinished) {

    const leaderboard = JSON.parse(localStorage.getItem('leaderboard')) || []; // get existing leaderboard or empty array

// Create new leaderboard entry
    const newEntry = {
        name: userName || 'Anonymous',
        score,
        schoolName: schoolName || 'Unknown'
      };

      const updated = [...leaderboard, newEntry];  // Add new entry to the leaderboard

    localStorage.setItem('leaderboard', JSON.stringify(updated)); // Save updated leaderboard to localStorage
  }
}, [quizFinished, score, userName, schoolName]);

  
// Function resets quiz state
const handleRetakeQuiz = () => {
    setScore(0);
    setQuizFinished(false);
  };

  return (
    <section className="quiz-page">
      <h2 className='quiz-header'>Boolean || Learning!</h2>
      <div className="subject-select">
  <label htmlFor="subject">Choose a subject:</label>
  <select
    id="subject"
    value={selectedSubject}
    onChange={(e) => setSelectedSubject(e.target.value)}
  >
    <option value="">-- Select a Different Subject --</option>
    <option value="JavaScript">JavaScript</option>
    <option value="Java">Java</option>
    <option value="HTML/CSS">HTML/CSS</option>
  </select>
</div>
      <br/>

{/* Show completion screen if quiz is finished */}

{!selectedSubject ? (
  <p>Please select a subject to begin the quiz.</p>
) : quizFinished ? (
  <div className='quiz-goodbye'>
    <p>Felicitaions, {userName}, Quiz completed!<br /> You had: <b>{score}</b>, answers Correct!</p>
    <br/>
    <br/>
    <button onClick={handleRetakeQuiz}>Retake Quiz?</button>
    <a href="/leaderboard" className="button-link">
      View Leaderboard
    </a>
  </div>
) : (
  <QuestionList
    score={score}
    setScore={setScore}
    setQuizFinished={setQuizFinished}
    selectedSubject={selectedSubject}
  />
)}


      <br/>
      <br/>

    </section>
  
  );
}

export default Quiz;

