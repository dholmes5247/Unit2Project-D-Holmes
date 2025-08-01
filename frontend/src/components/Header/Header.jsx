import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';  
import '../Header/Header.css';

export default function Header() {
  const { user, isAuthenticated, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (

    
    <div className="header-container">
    <svg className="header-shape" viewBox="0 0 100 100" preserveAspectRatio="none">
  <defs>
    <filter id="glow" x="-50%" y="-50%" width="200%" height="200%">
      <feDropShadow dx="0" dy="0" stdDeviation="6" floodColor="#00ffff" floodOpacity="1"/>
    </filter>
    <clipPath id="jagged-clip" clipPathUnits="objectBoundingBox">
      <polygon points="0 0, 1 0, 1 0.85, 0.95 1, 0.9 0.85, 0.85 1, 0.8 0.85, 0.75 1, 0 0.85"/>
    </clipPath>
  </defs>
  <rect width="100%" height="100%" 
      fill="#10052a" 
      clipPath="url(#jagged-clip)" 
      filter="url(#glow)" />

</svg>

    <header className="site-header">
    
  <div className="navbar">
    
    {/* LEFT: Welcome or Sign-in prompt */}
    <div className="header-left">
      <div className="app-title">Boolean || Learning</div>
      {isAuthenticated ? (
        <div className="userName">Hi, {user.username}</div>
      ) : (
        <div className="sign-in-prompt">
          <Link to="/login">Log In</Link> or{' '}
          <Link to="/signup">Sign Up</Link>
        </div>
      )}
    </div>

    {/* RIGHT: Navigation */}
    <ul className="nav-links">
      <li><Link to="/">Home</Link></li>
      <li><Link to="/about">About</Link></li>
      <li><Link to="/quiz">Quiz</Link></li>
      <li><Link to="/leaderboard">Leaderboard</Link></li>
      {isAuthenticated && (
        <li>
          <button className="logout-button" onClick={handleLogout}>Sign Out</button>
        </li>
      )}
    </ul>

  </div>
</header>
</div>


  );
}









