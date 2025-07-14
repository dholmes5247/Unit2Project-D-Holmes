import React, { useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { AuthContext } from '../../context/AuthContext';
import './Header.css';

const Header = () => {
  const navigate = useNavigate();
  const { isAuthenticated, userName, logout } = useContext(AuthContext);

  const handleLogout = () => {
    logout();
    navigate('/SignUpForm');
  };

  return (
    <header className="site-header">
      <nav className="navbar">
        {/* Welcome message */}
        <div className="welcome-message">
          <div className="app-title">Welcome to Boolean || Learning</div>
          <br />

          {isAuthenticated ? (
            <div className="userName">Good Luck - {userName}!</div>
          ) : (
            <Link to="/SignUpForm" className="sign-in-prompt">
              Please sign in to continue
            </Link>
          )}
        </div>

        {/* Navigation */}
        <ul className="nav-links">
          <li><Link to="/">Home</Link></li>
          <li><Link to="/quiz">Quiz</Link></li>
          <li><Link to="/about">About</Link></li>
          <li><Link to="/leaderboard">Leaderboard</Link></li>
          {!isAuthenticated ? (
            <li><Link to="/SignUpForm" className='signInLink'>Sign In</Link></li>
          ) : (
            <li>
              <button className="logout-button" onClick={handleLogout}>
                Sign Out
              </button>
            </li>
          )}
        </ul>
      </nav>
    </header>
  );
};

export default Header;







