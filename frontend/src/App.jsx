import './App.css';
import { useState } from 'react';
import { Routes, Route } from 'react-router-dom';
import Header from './components/Header/Header';
import Footer from './components/Footer/Footer';
import Home from './pages/Home';
import Quiz from './pages/Quiz';
import About from './pages/About';
import SignUpForm from './components/SignUpForm/SignUpForm';
import ProtectedRoute from './components/ProtectedRoute'; 
import Leaderboard from './components/Leaderboard/Leaderboard';



function App() {
const [leaderboard, setLeaderboard] = useState(() => {
  return JSON.parse(localStorage.getItem('leaderboard')) || [];
});

  return (
    <div className="app-container">

      <Header />
      <main className="content-area">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/about" element={<About />} />
          <Route path="/SignUpForm" element={<SignUpForm />} />
          <Route path="/leaderboard" element={<Leaderboard leaderboard={leaderboard} showImage={true} showQuote={true} />} />
          {/*  Wrap protected page */}
          <Route
            path="/quiz"
            element={
              <ProtectedRoute>
                <Quiz leaderboard={leaderboard} setLeaderboard={setLeaderboard} />
              </ProtectedRoute>
            }
          />
        </Routes>
      </main>
      <Footer />
    </div>
  );
}

export default App;

