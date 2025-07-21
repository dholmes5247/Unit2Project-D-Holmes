import React from 'react';

export default function Leaderboard() {
  const data = [
    { name: 'Alice', score: 95, schoolName: 'Wonder Academy' },
    { name: 'Bob',   score: 88, schoolName: 'Tech High' }
  ];

  return (
    <div>
      <h2>Leaderboard</h2>
      <table border="1" cellPadding="8">
        <thead>
          <tr>
            <th>Name</th>
            <th>Score</th>
            <th>School</th>
          </tr>
        </thead>
        <tbody>
          {data.map((r, i) => (
            <tr key={i}>
              <td>{r.name}</td>
              <td>{r.score}</td>
              <td>{r.schoolName}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}


/*

import React from 'react';
import './Leaderboard.css';
import albertTongue from '../../assets/images/Albert__tongue.jpg';


//  Had a typo with the Leaderboard.css filename so I changed it and need to commit.  Change

 
// NEW signature: always use `leaderboard` array (default to [])
const Leaderboard = ({ leaderboard = [] }) => {
  return (
    <div className="leaderboard-body">
      
// {/* Sidebar with Einstein quote & image
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
      

{/* Leaderboard table section
      
      <section className="leaderboard">
        <h2>Leaderboard- Top 10 Results</h2>

{/* empty state 
          {leaderboard.length === 0 ? (
          <p>No scores yet. Be the first!</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Name</th>
                <th>Score</th>
                <th>School Name</th>
              </tr>
            </thead>
            <tbody>
              {[...leaderboard]
                .sort((a, b) => b.score - a.score)
                .slice(0, 10)  
                .map((entry, index) => (
                  <tr key={index}>
                    <td>{entry.name}</td>
                    <td>{entry.score}</td>
                    <td>{entry.schoolName}</td>
                  </tr>
                ))}
            </tbody>
          </table>
        )}
      </section>
    </div>
  );
};


export default Leaderboard;

*/


