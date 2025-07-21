import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';  
import './Header.css';

export default function Header() {
  const { user, isAuthenticated, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <header className="site-header">
      <nav>
        <Link to="/">Home</Link> |{' '}
        <Link to="/">About</Link> |{' '}
        <Link to="/quiz">Quiz</Link> |{' '}
        <Link to="/leaderboard">Leaderboard</Link> |{' '}
        {isAuthenticated ? (
          <>
            <span>Hi, {user.name}</span>{' '}
            <button onClick={handleLogout}>Sign Out</button>
          </>
        ) : (
          <>
            <Link to="/login">Log In</Link> |{' '}
            <Link to="/signup">Sign Up</Link>
          </>
        )}
      </nav>
    </header>
  );
}









