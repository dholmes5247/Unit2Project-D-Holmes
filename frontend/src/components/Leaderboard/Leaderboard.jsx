import React, { useEffect, useState } from 'react';
import './Leaderboard.css';
import albertTongue from "../../assets/images/Albert__tongue.jpg";


const Leaderboard = () => {
  const [leaderboard, setLeaderboard] = useState([]);
  

  //  Fetch leaderboard data 
useEffect(() => {
  fetch("http://localhost:8080/api/quiz-attempts/top")
    .then(res => res.json())    
    .then(data => {
      console.log("Fetched leaderboard:", data); // log this to see whats there
      setLeaderboard(data)
})
    .catch(err => {
      console.error("Leaderboard fetch failed:", err);
      
      setLeaderboard([]);
    });
}, []); // No subject dependency


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


        {/* Score List */}
        {leaderboard.length ? (
<table className="leaderboard-table">
  <thead>
    <tr>
      <th>#</th>
      <th>Username</th>
      <th>School</th>
      <th>Subject</th>
      <th>Score</th>
      <th>Time (sec)</th>
    </tr>
  </thead>
  <tbody>
    {leaderboard.map((entry, idx) => (
      <tr key={entry.id}>
        <td>{idx + 1}</td>
        <td>{entry.user?.username || "Anonymous"}</td>
        <td>{entry.user?.school || "N/A"}</td>
        <td>{entry.subject?.name || "Unknown"}</td>
        <td>{entry.score}</td>
        <td>
            {Math.floor(entry.timeTakenInSeconds / 60)}m {entry.timeTakenInSeconds % 60}s
        </td>

      </tr>
    ))}
  </tbody>
</table>

) : (
  <p>No leaderboard data available yet.</p>
)}

          
        
      </div>
    </div>
  );
};

export default Leaderboard;





