import React, { useContext, useState, useEffect } from 'react';
import QuestionList from '../components/QuestionList/QuestionList';
import { AuthContext } from '../context/AuthContext';

import './Quiz.css';


function Quiz() {
  const { user } = useContext(AuthContext);

  // ✅ Core state for scoring, flow, and summary rendering
  const [ score, setScore] = useState(0);
  const [quizFinished, setQuizFinished] = useState(false);
  const [selectedSubject, setSelectedSubject] = useState('');
  const [subjectList, setSubjectList] = useState([]);
  const [quizSummary, setQuizSummary] = useState(null); // stores backend response after quiz completes
  const [isLoading, setIsLoading] = useState(false);

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

  useEffect(() => {
  if (selectedSubject && !quizFinished) {
    setIsLoading(true);
    const timer = setTimeout(() => {
      setIsLoading(false);
    }, 1500); // 1.5 seconds static flicker

    return () => clearTimeout(timer);
  }
}, [selectedSubject, quizFinished]);


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
  setSelectedSubject('');
  setQuizFinished(false);
  setQuizSummary(null);
};



  // ✅ Get subject name from subjectList
  const currentSubjectName =
    subjectList.find(s => s.id === parseInt(selectedSubject))?.name || selectedSubject;


return (
  <section className="quiz-page">
    <h2 className="quiz-header">Boolean || Learning!</h2>
    <div className="quiz-wrapper">
      <div className="tv-frame">
        {isLoading ? (
          <div className="static-screen">
            <p className="flicker-text">📡 Tuning in...</p>
          </div>
        ) : (
          <>
            {/* ✅ Subject Selection OR Quiz Content */}
            {!selectedSubject ? (
              <div className="tv-static-wrapper">
              <div className="static-background" />

              <div className="subject-overlay">
                <div className="channel-select-panel">
                  <label htmlFor="subject" className="channel-label">📡 Choose Your Channel:</label>
                  <select
                    id="subject"
                    className="channel-dropdown"
                    value={selectedSubject}
                    onChange={(e) => setSelectedSubject(e.target.value)}
                  >
                    <option value="">-- Select a Subject --</option>
                    {subjectList.map((subject) => (
                      <option key={subject.id} value={subject.id}>
                        {subject.name}
                      </option>
                    ))}
                  </select>
                  <p className="channel-helper-text">Please select a subject to begin the quiz.</p>
                </div>
              </div>
</div>

            ) : (
              <>
                

                {/* ✅ Subject Info */}
                <p className="selected-subject">
                  Subject: <b>{quizSummary?.subject?.name || currentSubjectName}</b>
                </p>
              </>
            )}

            {/* ✅ Prompt if subject not chosen yet */}
            {!selectedSubject && (
              <p>TUNE IN the subject selector! Good Luck & Enjoy!</p>
            )}

            {/* ✅ Questions while quiz is active */}
            {selectedSubject && !quizFinished && (
              <QuestionList
                score={score}
                setScore={setScore}
                setQuizFinished={setQuizFinished}
                selectedSubject={selectedSubject}
                user={user}
                showSummary={(attemptObj) => {
                  if (!attemptObj) {
                    setScore(0);
                    setQuizSummary(null);
                    setQuizFinished(true);
                    return;
                  }
                  setScore(attemptObj.score || attemptObj.correct || 0);
                  setQuizSummary(attemptObj);
                  setQuizFinished(true);
                }}
              />
            )}

            {/* ✅ Quiz Summary after completion */}
            {selectedSubject && quizFinished && quizSummary && (
              quizSummary.exitedEarly ? (
                <div className="quiz-summary">
                  <h2>🚪 You exited the quiz early.</h2>
                  <p>No attempt was saved.</p>
                  <div className="dashboard-actions">
                    <button onClick={handleRetakeQuiz}>🔄 Start Over</button>
                    <button onClick={handleChooseSubject}>
                      📚 Choose Another Subject
                    </button>
                  </div>
                </div>
              ) : (
                <div className="screen-content">
                  <div className="quiz-summary">
                    <h2>
                      Great work, {user?.userName || user?.name || "Learner"}!
                    </h2>

                    <div className="score-display">
                      <p>
                        Subject:{" "}
                        <b>{quizSummary.subject?.name || currentSubjectName}</b>
                        <br />
                        Score: <b>{quizSummary.score}</b> /{" "}
                        <b>{quizSummary.totalQuestions || "?"}</b> (
                        <b>
                          {quizSummary.totalQuestions &&
                          quizSummary.totalQuestions > 0
                            ? `${Math.round(
                                (quizSummary.score /
                                  quizSummary.totalQuestions) *
                                  100
                              )}%`
                            : "N/A%"}
                        </b>
                        )<br />
                        Duration: <b>{quizSummary.duration || 0}</b> seconds
                      </p>
                      <p>
                        Started:{" "}
                        <b>
                          {quizSummary.startedAt
                            ? new Date(
                                quizSummary.startedAt
                              ).toLocaleString()
                            : "N/A"}
                        </b>
                        <br />
                        Completed:{" "}
                        <b>
                          {quizSummary.completedAt
                            ? new Date(
                                quizSummary.completedAt
                              ).toLocaleString()
                            : "N/A"}
                        </b>
                      </p>
                    </div>
                  </div>

                  <div className="dashboard-actions">
                    <button onClick={handleRetakeQuiz}>🔄 Retake Quiz</button>
                    <button onClick={handleChooseSubject}>
                      📚 Another Subject?
                    </button>
                    <a href="/leaderboard" className="button-link">
                      🏆 View Leaderboard 🏆
                    </a>
                  </div>
                </div>
              )
            )}
          </>
        )}
      </div>
    </div>
  </section>
);


}

export default Quiz;

