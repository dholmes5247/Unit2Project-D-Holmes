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
        
        {/* Left side: Logo and copyright */}
        <div className="footer-left">
          <img src={logo} alt="Holmes Farm Logo" className="footer-logo" />
          <div className="footer-text">
            &copy; {new Date().getFullYear()} Holmes Farm LLC
          </div>
        </div>

        {/* Right side: Nav links and contact */}
        <div className="footer-right">
          <div className="footer-links">
            <ul className="nav-links">
              <li><Link to="/">Home</Link></li>
              <li><Link to="/quiz">Quiz</Link></li>
              <li><Link to="/leaderboard">Leaderboard</Link></li>
              <li><Link to="/about">About</Link></li>
            </ul>
          </div>

          <div className="footer-text email">
            Contact us: <a href="mailto:info@holmesfarm.dev">info@holmesfarm.dev</a>
          </div>

          <button className="dark-toggle" onClick={toggleTheme}>
            {isDark ? '☀ Light Mode' : '🌙 Dark Mode'}
          </button>
        </div>
      </div>
    </footer>
  );
}







