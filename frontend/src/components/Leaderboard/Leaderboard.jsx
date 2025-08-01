import React, { useEffect, useState, useContext } from 'react';
import './Leaderboard.css';
import albertTongue from '../../assets/images/Albert__tongue.jpg';
import { AuthContext } from '../../context/AuthContext';
import { secureFetch } from '../../hooks/API';


const Leaderboard = () => {
  // State to hold the fetched leaderboard entries
  const [leaderboard, setLeaderboard] = useState([]);

  // Controls which leaderboard view is active: 'top', 'subject', or 'user'
  const [view, setView] = useState('top');

  // Holds the currently selected subject ID when filtering by subject
  const [subjectId, setSubjectId] = useState('');

  // Holds the list of all subjects for the dropdown
const [subjects, setSubjects] = useState([]);

const [page, setPage] = useState(0);      // Tracks current page
const [pageSize] = useState(10);          // Fixed size per page
const [hasNextPage, setHasNextPage] = useState(true);

  // Get the logged-in user (if any) from context
  const { user } = useContext(AuthContext);

      useEffect(() => {
        let endpoint = `/api/leaderboard/top?page=${page}&size=${pageSize}`;

        if (view === 'subject' && subjectId) {
          endpoint = `/api/leaderboard/subject/${subjectId}`;
                } else if (view === 'user' && user?.id) {
                endpoint = `/api/leaderboard/user/${user.id}?page=${page}&size=${pageSize}`;
                }                 

                  secureFetch(endpoint)
                  .then(res => {
                    if (!res.ok) throw new Error(`Fetch failed: ${res.status}`);
                    return res.json();
                  })            
                    .then(data => {
                      console.log("Paged leaderboard:", data);
                      setLeaderboard(data);
                      setHasNextPage(data.length > 0 || page === 0);
                    })
          .catch(err => {
            console.error("Leaderboard fetch failed:", err);
            setLeaderboard([]);
          });
      }, [view, subjectId, user, page, pageSize]);




  useEffect(() => {
  secureFetch('/api/subjects')
    .then(res => {
      if (!res.ok) throw new Error(res.statusText);
      return res.json();
    })
    .then(data => setSubjects(data))
    .catch(err => console.error('Failed to load subjects:', err));
}, []);


return (
  <div className="leaderboard-body">
    {/* Sidebar with Einstein quote */}
    <div className="sidebar">
      <div className="quote">
        "Logic will get you from A to B, BUT imagination will take you EVERYWHERE."
        <div className="signature">- Albert Einstein</div>
      </div>
      <a
        href="https://en.wikipedia.org/wiki/Albert_Einstein"
        target="_blank"
        rel="noopener noreferrer"
        title="Learn more about Einstein"
        className="albert-link"
      >
        <img
          src={albertTongue}
          alt="Albert Einstein sticking out his tongue"
          className="albert-img"
        />
      </a>
    </div>

    {/* Main leaderboard content */}
    <div className="leaderboard">
      <h2 className="leaderboard-header">🏆 Leaderboard 🏆</h2>

      {/* Controls: view toggles, subject filter, pagination */}
      <div className="leaderboard-controls">
        {/* View toggle buttons */}
        <div className="leaderboard-toggle">
          <button onClick={() => setView('top')}>Top Scores</button>
          <button onClick={() => setView('subject')}>By Subject</button>
          <button onClick={() => setView('user')}>My Stats</button>
        </div>

        {/* Subject selector (only shown in "By Subject" view) */}
        {view === 'subject' && (
          <div className="subject-filter">
            <label htmlFor="subject-select">Select Subject:</label>
            <select
              id="subject-select"
              value={subjectId || ''}
              onChange={e => setSubjectId(e.target.value)}
            >
              <option value="">-- All Subjects --</option>
              {subjects.map(s => (
                <option key={s.id} value={s.id}>
                  {s.name}
                </option>
              ))}
            </select>
          </div>
        )}

        {/* Pagination (shown for Top Scores and My Stats views) */}
        {(view === 'top' || view === 'user') && (
          <div className="leaderboard-pagination">
            <button
              onClick={() => setPage(p => Math.max(p - 1, 0))}
              disabled={page === 0}
            >
              ⬅ Prev
            </button>

            &nbsp;<span>
              Page {page + 1}
              {!hasNextPage ? ' (Last Page)' : ''}
            </span>&nbsp;

            <button
              onClick={() => setPage(p => p + 1)}
              disabled={!hasNextPage}
            >
              Next ➡
            </button>
          </div>
        )}
        <div className="divider-labeled">
  <span>Leaderboard Results</span>
</div>
      </div>

      {/* Leaderboard table or fallback message */}
      {leaderboard.length > 0 ? (
        <table className="leaderboard-table">
          <thead>
            <tr>
              <th>#</th>
              <th>Username</th>
              <th>School</th>
              <th>Subject</th>
              <th>Score</th>
              <th>Duration</th>
            </tr>
          </thead>
          <tbody>
            {leaderboard.map((entry, idx) => (
              <tr key={entry.id}>
                <td>{idx + 1}</td>
                <td>{entry.user?.username || 'Anonymous'}</td>
                <td>{entry.user?.school || 'N/A'}</td>
                <td>{entry.subject?.name || 'Unknown'}</td>
                <td>{entry.score}</td>
                <td title={`${entry.duration} sec`}>
                  {Math.floor(entry.duration / 60)}m {entry.duration % 60}s
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

}

export default Leaderboard;