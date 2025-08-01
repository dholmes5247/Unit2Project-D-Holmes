import { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";
import trueFalseImage from "../assets/images/trueFalseImage.jpg"; // Adjust path if needed
import './Home.css';
import { secureFetch } from "../hooks/API";

export default function Home() {
  const { user } = useContext(AuthContext);

  //  Local state
  const [loginStats, setLoginStats] = useState(null);
  const [userStats, setUserStats] = useState(null);

  //  Fetch both login stats and user stats on mount
  useEffect(() => {
    if (!user?.id) return;

    // Fetch login stats
    const fetchLoginStats = async () => {
      try {
        const res = await secureFetch(`/api/stats/${user.id}`);
        const data = await res.json();
        setLoginStats(data);
      } catch (err) {
        console.error("Error fetching login stats:", err);
      }
    };

    // Fetch user stats
    const fetchUserStats = async () => {
      try {
        const res = await secureFetch(`/api/user/stats/${user.id}`);
        const data = await res.json();
        setUserStats(data);
      } catch (err) {
        console.error("Error fetching full user stats:", err);
      }
    };

    fetchLoginStats();
    fetchUserStats();
  }, [user?.id]);

  //  Page layout and rendering
  return (
  <div className="full-home-page">
    {/* LEFT SIDE: Intro, Info, Image */}
    <div className="left-panel">
    <div className="screen-box">
    
      <h2>Welcome to Boolean || Learning</h2>
      <p>
        <Link to="/login">Log in</Link> or{" "}
        <Link to="/signup">Sign up</Link> to begin.
      </p>
    </div>
    <div className="screen-box">
    <div className="screen-label">INTEL FEED</div>

      <h1>-The Quiz App!</h1>
      <p>
        Please feel free to use any of our materials for your own review.
        We only ask that you sign in using your name, email, and school or study course.
        We don't send junk mail, sell your data or generally make a nuisance of ourselves.
        We use your info to track progress and show leaderboard results. Thanks for using our quiz app!
        If you prefer a lighter or darker mode there is a toggle in the footer you can use!
      </p>
    </div>
    <div className="screen-box">
    <div className="screen-label">Investigate</div>

      <a
        href="https://en.wikipedia.org/wiki/Boolean_expression"
        className="link"
        title="Wanna learn about Booleans?"
        target="_blank"
        rel="noreferrer"
      >
        <img src={trueFalseImage} alt="The History of Boolean?" className="home-image" />
      </a>
      </div>
    </div>


    {/* RIGHT SIDE: Stats Panel */}
    {user && loginStats && userStats ? (
  <div className="right-panel">
    <div className="stats-panel">
      <h2 className="panel-title">🎙️ {user.username}'s Performance</h2>
      <div className="stat-list">
        <div className="stat-item"><span>🔥 Login Streak:</span> {loginStats.loginStreak} days</div>
        <div className="stat-item"><span>🧠 Quizzes Taken:</span> {userStats.totalQuizzesTaken}</div>
        <div className="stat-item"><span>💡 Questions Answered:</span> {userStats.totalQuestionsAnswered}</div>
        <div className="stat-item"><span>📊 Avg # Correct:</span> {userStats.averageScore.toFixed(2)}</div>
        <div className="stat-item"><span>📈 Improvement:</span> {userStats.improvementRate.toFixed(2)}%</div>
        <div className="stat-item"><span>🎯 This Week:</span> {userStats.quizzesTakenThisWeek}</div>
        <div className="stat-item"><span>🌐 Categories Explored:</span> {userStats.categoriesExplored}</div>
      </div>
    </div>
  </div>
) : (
  <div className="right-panel login-teaser">
    <div className="screen-box">
      <h2>🎤 User Feed Offline</h2>
      <Link to="/login" className="login-button">Activate Feed</Link>
    </div>
  </div>
)}


      </div>
    )}
  









