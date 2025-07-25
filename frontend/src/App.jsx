import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Header from './components/Header/Header';
import Footer          from './components/Footer/Footer';
import ProtectedRoute  from './components/ProtectedRoute';
import Home            from './pages/Home';
import Quiz            from './pages/Quiz';
import About           from './pages/About';
import LoginForm       from './components/LoginForm/LoginForm';
import SignUpForm      from './components/SignUpForm/SignUpForm';
import Leaderboard     from './components/Leaderboard/Leaderboard';
import './App.css';



export default function App() {
  return (
    <>
    <div className="app-container">
      <Header />
      
      <div className="content-area">
      <main style={{ padding: '1rem' }}>
      
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/about" element={<About />} />
          <Route path="/login" element={<LoginForm />} />
          <Route path="/signup" element={<SignUpForm />} />
          
          <Route
            path="/quiz"
            element={
              <ProtectedRoute>
                <Quiz />
              </ProtectedRoute>
            }
          />
          <Route path="/leaderboard" element={<Leaderboard />} />
          
        </Routes>
      </main>
      </div>
      </div>
      <Footer />
    </>
  );
}



