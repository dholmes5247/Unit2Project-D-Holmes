import React from 'react';
import './Leaderboard.css';
import albertTongue from '../../assets/images/Albert__tongue.jpg';


//  Had a typo with the Leaderboard.css filename so I changed it and need to commit.  Change

/*
 - leaderboard: array of { name, score, schoolName }
 - showImage: whether to show Einstein image (default: false)
 - showQuote: whether to show Einstein quote (default: false)*/ 

const Leaderboard = ({ leaderboard, showImage = false, showQuote = false }) => {
  return (
    <div className="leaderboard-body">
      
{/* Sidebar  quote and image */}
      { (showQuote || showImage) && (
        <div className="sidebar">
          {showQuote && (
            <div className="quote">
              "Logic will get you from A to B, BUT
              imagination will take you EVERYWHERE."
                <div className='signature'>
                  - Albert Einstein
                </div>
            </div>
          )}
          {showImage && (
            <a
              href="https://en.wikipedia.org/wiki/Albert_Einstein"
              target="_blank"
              rel="noopener noreferrer"
              className="albert-link"
              title="Want to learn more about Einstein?--CLICK!"
            >
              <img
                src={albertTongue}
                alt="Albert Einstein tongue"
                className="albert-img"
              />
            </a>
          )}
        </div>
      )}

{/* Leaderboard table section */}
      
      <section className="leaderboard">
        <h2>Leaderboard- Top 10 Results</h2>

{/* empty state */}
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




