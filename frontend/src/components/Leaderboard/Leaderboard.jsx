import React, { useEffect, useState } from 'react';
import './Leaderboard.css';
import albertTongue from "../../assets/images/Albert__tongue.jpg";


const Leaderboard = () => {
  const [leaderboard, setLeaderboard] = useState([]);
  const [selectedSubject, setSelectedSubject] = useState('');

  // ✅ Fetch leaderboard data when subject changes
  useEffect(() => {
    if (selectedSubject) {
      fetch(`http://localhost:8080/api/quiz-attempts/top?subjectId=${selectedSubject}`)
        .then(res => res.json())
        .then(data => setLeaderboard(data))
        .catch(err => {
          console.error("Leaderboard fetch failed:", err);
          setLeaderboard([]);
        });
    }
  }, [selectedSubject]);

  return (
    <div className="leaderboard-page">
      {/* ✅ Einstein Sidebar: your existing inspirational quote block */}
      <div className="sidebar">
        <div className="quote">
          "Logic will get you from A to B, BUT imagination will take you EVERYWHERE."
          <div className="signature">- Albert Einstein</div>
        </div>
        <a
          href="https://en.wikipedia.org/wiki/Albert_Einstein"
          target="_blank"
          rel="noopener noreferrer"
          className="albert-link"
          title="Learn more about Einstein"
        >
          <img
            src={albertTongue}
            alt="Albert Einstein sticking out his tongue"
            className="albert-img"
          />
        </a>
      </div>

      {/* ✅ Leaderboard Content */}
      <div className="leaderboard-body">
        <h2>🏆 Leaderboard</h2>

        {/* Subject Filter Dropdown */}
        <select
          value={selectedSubject}
          onChange={(e) => setSelectedSubject(e.target.value)}
        >
          <option value="">-- Choose a Subject --</option>
          <option value="1">JavaScript</option>
          <option value="2">Spring Boot</option>
          {/* 🔧 You can fetch and render subjects dynamically if needed */}
        </select>

        {/* Score List */}
        {selectedSubject && (
          <ul className="leaderboard-list">
            {leaderboard.length ? (
              leaderboard.map((entry, idx) => (
                <li key={idx}>
                  🧑 Attempt #{entry.id} — Score: <b>{entry.score}</b> — ⏱ {entry.timeTakenInSeconds} sec
                </li>
              ))
            ) : (
              <p>No scores available for this subject.</p>
            )}
          </ul>
        )}
      </div>
    </div>
  );
};

export default Leaderboard;





