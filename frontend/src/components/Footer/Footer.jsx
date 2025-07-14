import React, { useContext, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import logo from '../../assets/images/rubeGoldbergStapler.png';
import './Footer.css';
import { AuthContext } from '../../context/AuthContext';

function Footer() {
  const { isAuthenticated, logout } = useContext(AuthContext);

  const [darkMode, setDarkMode] = useState(() => {
    return localStorage.getItem('darkMode') === 'true';
  });

  useEffect(() => {
    if (darkMode) {
      document.body.classList.add('dark-mode');
    } else {
      document.body.classList.remove('dark-mode');
    }
    localStorage.setItem('darkMode', darkMode);
  }, [darkMode]);

  const handleLogout = () => logout();

  return (
    <footer className="footer">
      <div className="footer-content">
        
        <div className="footer-left">
        <a href="https://en.wikipedia.org/wiki/Rube_Goldberg_machine" target="_blank" rel="noopener noreferrer">

          <img src={logo} alt="rube-logo" title="Want to learn about Rube Goldberg machines?"className="footer-logo" />
        </a>

        </div>

                  {/* Toggle Button */}
          <button
            className="dark-toggle"
            onClick={() => setDarkMode(prev => !prev)}
            style={{ marginTop: '1rem' }}
          >
            {darkMode ? '☀️ Light Mode' : '🌙 Dark Mode'}
          </button>

        {/* Right: nav + text + toggle */}
        <div className="footer-right">
          <nav className="footer-links">
            <ul className="nav-links">
              <li><Link to="/">Home</Link></li>
              <li><Link to="/quiz">Quiz</Link></li>
              <li><Link to="/about">About</Link></li>
              <li><Link to="/leaderboard">Leaderboard</Link></li>
              {!isAuthenticated ? (
                <li><Link to="/SignUpForm">Sign In</Link></li>
              ) : (
                <li>
                  <button className="logout-button" onClick={handleLogout}>
                    Signout
                  </button>
                </li>
              )}
            </ul>
          </nav>



          <div className="footer-text">
            <span>&copy; 2025 Holmes Farm LLC || <b><em>Contact me:&nbsp;&nbsp;</em></b></span>
            <span className='email'>
            <a href="mailto:dholmes5247@hotmail.com">dholmes5247@hotmail.com</a>
            </span>
          </div>
        </div>
      </div>
    </footer>
  );
}

export default Footer;

