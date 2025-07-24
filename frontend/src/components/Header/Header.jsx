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
    <header className="site-header">
  <div className="navbar">
    
    {/* LEFT: Welcome or Sign-in prompt */}
    <div className="header-left">
      <div className="app-title">Boolean || Learning</div>
      {isAuthenticated ? (
        <div className="userName">Hi, {user.name}</div>
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


  );
}









