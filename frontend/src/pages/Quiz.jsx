import React, { useContext, useState, useEffect } from 'react';
import QuestionList from '../components/QuestionList/QuestionList';
import { AuthContext } from '../context/AuthContext';
import './Quiz.css';

function Quiz() {
  const { user } = useContext(AuthContext);
  const [score, setScore] = useState(0);
  const [quizFinished, setQuizFinished] = useState(false);
  const [selectedSubject, setSelectedSubject] = useState('');
  const [subjectList, setSubjectList] = useState([]);
  const [quizSummary, setQuizSummary] = useState(null); // stores summary details
 

  // Load subjects dynamically
  useEffect(() => {
    fetch('http://localhost:8080/api/subjects')
      .then(res => res.json())
      .then(data => setSubjectList(data))
      .catch(err => {
        console.error("Failed to fetch subjects:", err);
        setSubjectList([]);
      });
  }, []);



  // Retake quiz with same subject
  const handleRetakeQuiz = () => {
    setScore(0);
    setQuizFinished(false);
    setQuizSummary(null);
  };

  // Switch subjects
  const handleChooseSubject = () => {
    setScore(0);
    setQuizFinished(false);
    setSelectedSubject('');
    setQuizSummary(null);
  };

  // Get subject name for display
  const currentSubjectName = subjectList.find(s => s.id === parseInt(selectedSubject))?.name || selectedSubject;

  return (
    <section className="quiz-page">
      <h2 className="quiz-header">Boolean || Learning!</h2>

      <div className="subject-select">
        <label htmlFor="subject">Choose a subject:</label>
        <select
          value={selectedSubject}
          onChange={(e) => setSelectedSubject(e.target.value)}
        >
          <option value="">-- Select a Subject --</option>
          {subjectList.map(subject => (
            <option key={subject.id} value={subject.id}>
              {subject.name}
            </option>
          ))}
        </select>
      </div>

      <br />

      {!selectedSubject ? (
        <p>Please select a subject to begin the quiz.</p>
      ) : quizFinished && quizSummary ? (
        <div className="quiz-summary">
          <h2>🎉 Great work, {user.userName}!</h2>
          <p>
            You completed the <b>{currentSubjectName}</b> quiz.<br />
            Final Score: <b>{quizSummary.correct}</b> out of {quizSummary.total} questions<br />
            {/* Duration: <b>{quizSummary.duration}</b> seconds */}
          </p>

          <div className="dashboard-actions">
            <button onClick={handleRetakeQuiz}>🔄 Retake Quiz</button>
            <button onClick={handleChooseSubject}>📚 Choose Another Subject</button>
            <a href="/leaderboard" className="button-link">🏆 View Full Leaderboard</a>
          </div>


        </div>
      ) : (
        <QuestionList
          score={score}
          setScore={setScore}
          setQuizFinished={setQuizFinished}
          selectedSubject={selectedSubject}
          user={user}
          showSummary={(data) => {
            setQuizFinished(true);
            setScore(data.correct);
            setQuizSummary(data); // contains correct, total, duration
          }}
        />
      )}

      <br /><br />
    </section>
  );
}

export default Quiz;



