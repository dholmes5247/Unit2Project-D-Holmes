import React from 'react';
import { Link } from 'react-router-dom';
// import { useContext } from 'react'; moving to login
import { AuthContext } from '../context/AuthContext';
import EditProfileForm from '../components/EditProfileForm/EditProfileForm';
import trueFalseImage from '../assets/images/trueFalseImage.jpg'; // 
import './home.css';


export default function Home() {
  // const user = useContext(AuthContext); // authcontext moving update user to login
  return (
    <div>
      <h2>Welcome to Boolean || Learning</h2>
      <p>
        <Link to="/login">Log in</Link> or{' '}
        <Link to="/signup">Sign up</Link> to begin.
      </p>

          <div>
      <h1></h1>
      <p>
-The Quiz App!
Please feel free to use any of our materials for your own review. 
We only ask that you sign in using your name, email, and school or study course.
We don't send junk mail, sell your data or generally make a nuisance of ourselves.
 We use your info to track progress and show leaderboard results. Thanks for using our quiz app!
If you prefer a lighter or darker mode there is a toggle in the footer you can use!
  </p> 

{/* Clickable image linking to LinkedIn profile */}
      <a href="https://en.wikipedia.org/wiki/Boolean_expression" 
      className="secret-link" 
      title='Wanna learn about Booleans?'
      target="_blank">

{/* True False image */}      
        <img src={trueFalseImage} alt="The History of Boolean?" />

      </a>


      
      {/* Other homepage content */}
    </div>
    </div>
  );
}





