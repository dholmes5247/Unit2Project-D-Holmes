import React, { useEffect, useState, useContext } from 'react';
import { Link } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import EditProfileForm from '../components/EditProfileForm/EditProfileForm';
import trueFalseImage from '../assets/images/trueFalseImage.jpg';
import './home.css';

//  Component
export default function Home() {
  //  Get user from context
  const { user } = useContext(AuthContext);

  //  Local state for login stats
  const [loginStats, setLoginStats] = useState(null);

  //  Effect hook to fetch stats on mount
  useEffect(() => {
    const fetchLoginStats = async () => {
      try {
        const res = await fetch(`http://localhost:8080/api/stats/${user.id}`);
        const data = await res.json();
        setLoginStats(data);
      } catch (err) {
        console.error('Error fetching login stats:', err);
      }
    };

    if (user?.id) {
      fetchLoginStats();
    }
  }, [user?.id]);

  // ✅ Main render
  return (
    <div className="full-home-page"> {/* ⬅ classname ➜ className */}
      <h2>Welcome to Boolean || Learning</h2>
      <p>
        <Link to="/login">Log in</Link> or{' '}
        <Link to="/signup">Sign up</Link> to begin.
      </p>

      <div>
        <h1>-The Quiz App!</h1>
        <p>
          -The Quiz App!
          Please feel free to use any of our materials for your own review.
          We only ask that you sign in using your name, email, and school or study course.
          We don't send junk mail, sell your data or generally make a nuisance of ourselves.
          We use your info to track progress and show leaderboard results. Thanks for using our quiz app!
          If you prefer a lighter or darker mode there is a toggle in the footer you can use!
        </p>

        {/*  Clickable image linking to Boolean info */}
        <a
          href="https://en.wikipedia.org/wiki/Boolean_expression"
          className="secret-link"
          title="Wanna learn about Booleans?"
          target="_blank"
          rel="noreferrer"
        >
          <img src={trueFalseImage} alt="The History of Boolean?" />
        </a>

        {/*  conditionally render stats here later */}
        {/* {loginStats && <StatsDisplay stats={loginStats} />} */}
        {user && loginStats && (
        <div className="right-panel">
          <h3>Your Weekly Highlights</h3>

        <ul>
            <li><strong>Last Active:</strong> {loginStats.lastActiveDate}</li>
            <li><strong>Login Streak:</strong> {loginStats.loginStreak} days</li>
            {/* Add more user stats as I expand */}
        </ul>
  </div>
)}

      </div>
    </div>
  );
}






