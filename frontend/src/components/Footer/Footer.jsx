import React from 'react';
import { Link } from 'react-router-dom';
import logo from '../../assets/images/rubeGoldbergStapler.png';
import { useTheme } from '../../hooks/useTheme';
import './Footer.css';

export default function Footer() {
  const { toggleTheme, isDark } = useTheme(); // ✅ Moved outside return

  return (
    <footer className="footer">
  <div className="footer-content">
    
    {/* Left side */}
    <div className="footer-left">
    <a
            href="https://en.wikipedia.org/wiki/Rube_Goldberg"
            className="link"
            title="Want the easy way?"
            target="_blank"
            rel="noreferrer"
            alt="Rube_Goldberg image"
          >
            <img src={logo} alt="Holmes Farm Logo" className="footer-logo" />
          </a>
      
      
    </div>

    {/* Center */}
    <div className="footer-center">
      <button className="dark-toggle" onClick={toggleTheme}>
        {isDark ? '💡 Light Mode' : '🌙 Dark Mode'}
      </button>
    </div>

    {/* Right side */}
    <div className="footer-right">
      <ul className="nav-links">
        <li><Link to="/">Home</Link></li>
        <li><Link to="/quiz">Quiz</Link></li>
        <li><Link to="/leaderboard">Leaderboard</Link></li>
        <li><Link to="/about">About</Link></li>
      </ul>
      <div className="footer-text email">
        Contact us: <a href="mailto:dholmes5247@hotmail.com">dholmes5247@hotmail.com</a>
      </div>
      <div className="footer-text">
        &copy; {new Date().getFullYear()} Holmes Farm LLC
      </div>
    </div>
    
  </div>
</footer>

  );
}







