import React, { useContext, useState, useEffect } from 'react';
import QuestionList from '../components/QuestionList/QuestionList';
import { AuthContext } from '../context/AuthContext';

import './Quiz.css';


function Quiz() {
  const { user } = useContext(AuthContext);

  // ✅ Core state for scoring, flow, and summary rendering
  const [score, setScore] = useState(0);
  const [quizFinished, setQuizFinished] = useState(false);
  const [selectedSubject, setSelectedSubject] = useState('');
  const [subjectList, setSubjectList] = useState([]);
  const [quizSummary, setQuizSummary] = useState(null); // stores backend response after quiz completes

  // ✅ Load available subjects on mount
  useEffect(() => {
    fetch('http://localhost:8080/api/subjects')
      .then(res => res.json())
      .then(data => setSubjectList(data))
      .catch(err => {
        console.error("Failed to fetch subjects:", err);
        setSubjectList([]);
      });
  }, []);

  const resetQuizState = () => {
  setScore(0);
  setQuizFinished(false);
  setQuizSummary(null);
};
  // ✅ Handle retake (same subject, reset state)
  const handleRetakeQuiz = () => {
    resetQuizState();
  };

  // ✅ Handle switching subjects
  const handleChooseSubject = () => {
    resetQuizState();    
    setSelectedSubject('');
    
  };



  // ✅ Get subject name from subjectList
  const currentSubjectName =
    subjectList.find(s => s.id === parseInt(selectedSubject))?.name || selectedSubject;

  return (
    <section className="quiz-page">
      <h2 className="quiz-header">Boolean || Learning!</h2>
      
      

      {/* ✅ Subject selection dropdown */}
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



      {/* ✅ Conditional rendering based on state */}
      {!selectedSubject && (
        <p>Please select a subject to begin the quiz.</p>
      )}


{selectedSubject && quizFinished && quizSummary && (
  quizSummary.exitedEarly ? (
    <div className="quiz-summary">
      <h2>🚪 You exited the quiz early.</h2>
      <p>No attempt was saved.</p>
      <div className="dashboard-actions">
        <button onClick={handleRetakeQuiz}>🔄 Start Over</button>
        <button onClick={handleChooseSubject}>📚 Choose Another Subject</button>
      </div>
    </div>
  ) : (
    <div className="quiz-summary">
      <h2>🎉 Great work, {user?.userName || user?.name || "Learner"}!</h2>
      <p>
        Subject: <b>{quizSummary.subject?.name || currentSubjectName}</b><br />
        Score: <b>{quizSummary.score}</b> / <b>{quizSummary.totalQuestions || "?"}</b> (<b>{Math.round((quizSummary.score / quizSummary.totalQuestions) * 100)}%</b>)<br />
        Duration: <b>{quizSummary.duration || 0}</b> seconds
      </p>
      <p>
        Started: <b>{quizSummary.startedAt ? new Date(quizSummary.startedAt).toLocaleString() : "N/A"}</b><br />
        Completed: <b>{quizSummary.completedAt ? new Date(quizSummary.completedAt).toLocaleString() : "N/A"}</b>
      </p>
      <div className="dashboard-actions">
        <button onClick={handleRetakeQuiz}>🔄 Retake Quiz</button>
        <button onClick={handleChooseSubject}>📚 Choose Another Subject</button>
        <a href="/leaderboard" className="button-link">🏆 View Leaderboard</a>
      </div>
    </div>
  )
)}


      {selectedSubject && !quizFinished && (
        <QuestionList
          score={score}
          setScore={setScore}
          setQuizFinished={setQuizFinished}
          selectedSubject={selectedSubject}
          user={user}
          showSummary={(attemptObj) => {
             if (!attemptObj) {
            // User exited early — don’t set anything
            setScore(0);
            setQuizSummary(null);
            setQuizFinished(true);
            return;
  }
            // ✅ Called from QuestionList after quiz finishes
            setScore(attemptObj.score || attemptObj.correct || 0);
            setQuizSummary(attemptObj); // full QuizAttempt from backend
            setQuizFinished(true);
          }}
        />
      )}
    </section>
  );
}

export default Quiz;
