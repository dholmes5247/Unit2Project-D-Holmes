import React from 'react';
import { Link } from 'react-router-dom';

export default function Home() {
  return (
    <div>
      <h2>Welcome to Boolean || Learning</h2>
      <p>
        <Link to="/login">Log in</Link> or{' '}
        <Link to="/signup">Sign up</Link> to begin.
      </p>
    </div>
  );
}





