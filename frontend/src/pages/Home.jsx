
import React, { useState, useEffect } from 'react';
import Leaderboard from '../components/Leaderboard/Leaderboard';  
import trueFalseImage from '../assets/images/trueFalseImage.jpg'; 
import './Home.css'; 

function Home() {
  // State to hold leaderboard data 
  const [leaderboard, setLeaderboard] = useState([]);

  // useEffect runs to load leaderboard data
  useEffect(() => {
    const stored = JSON.parse(localStorage.getItem('leaderboard')) || [];
    setLeaderboard(stored); // Set state with stored leaderboard or empty array
  }, []);

  // reset the leaderboard with user confirmation
  const resetLeaderboard = () => {
    const confirmed = window.confirm("Are you ABSOLUTELY sure you want to reset the Leaderboard?");
    if (confirmed) {
      localStorage.removeItem('leaderboard'); // Clear leaderboard 
      setLeaderboard([]); // Reset state
    }
  };

  return (
    <div className="home-page"> 

{/* Left side Welcome message, image, and reset button */}
      <div className="home-left">
        <h2 className="home-heading">
          Welcome to<br/>
          <span className="highlighted">Boolean || Learning</span><br />
          -The Quiz App!
        </h2>

        <p>
          
          Please feel free to use any of our materials for your own review.
          We only ask that you sign in using your name, email, and school or study course.
          <br /><br />
          We don't send junk mail, sell your data or generally make a nuisance of ourselves. 
          We use your info to track progress and show leaderboard results.
          Thanks for using our quiz app!
          <br /><br />
          If you want to reset the leaderboard and challenge your friends, click the "Reset Leaderboard" button below.

        </p>

{/* True/false image */}
    <a href="https://en.wikipedia.org/wiki/Boolean_data_type" target="_blank" rel="noopener noreferrer">
        <img
          src={trueFalseImage}
          alt="TrueFalse checked"
          title="Want to learn about BooLeans?"
          className="home-image"
        />
    </a>

{/* reset leaderboard button */}
        <button className="reset-button" onClick={resetLeaderboard}>
          Reset Leaderboard
        </button>
      </div>

{/* LeaderBoard Right Side */}
      <div className="home-right"> 
        <Leaderboard leaderboard={leaderboard} showImage={false} showQuote={false} />
      </div>

    </div>
  );
}

export default Home;





