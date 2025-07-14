import React from 'react';
import darrenMug from '../assets/images/darrenMug.JPG'; // Has to be .JPG rookie mistake, runs locally not on netifly
import './About.css'; 

function About() {

  return (

  <section className="about-section">

      <h2>&nbsp;About Me & This App:&nbsp; </h2>
      <p className="about">
        This app aims to connect learners with materials that can reinforce their learning
        through true/false questions and instant feedback.
        <br /><br />
        In my LaunchCode course, we play a game called <b><em>Kahoot</em></b> to reinforce knowledge
        from the modules — its been incredibly beneficial for me. I built this app to learn
        by doing and to share a tool that might help others too.  I have ideas for many add-ons
        including: more questions, different subject areas, more links to external sites etc.  
        If you have any  ideas please email me using the link below in the footer.
        <br /><br />
        Just like watching film and running practices from my coaching days, building websites helps me
        understand what I have been learning and tie it all together.  You never know what you
        will find within projects...
      </p>

  <div className='about-content'>

{/* Clickable image linking to LinkedIn profile */}
      <a href="https://www.linkedin.com/in/darren-holmes-372737114/" 
      className="secret-link" 
      title='Click Me for my LinkedIn Profile'
      target="_blank">

{/* Headshot image */}      
        <img src={darrenMug} alt="Do you want to take a trip?" />

      </a>

{/* Career experience - unordered list */}

    <div className="career-experience">
      <ul>Career Experience:</ul>
        <li>Strategic Account Manager,&nbsp;&nbsp; -Equifax</li>
        <li>Mortgage Consultant,&nbsp;&nbsp; -State Farm </li> 
        <li>Commercial Diver & Project Manager,&nbsp;&nbsp; -Cal Dive International</li>
        <li>Defensive Coordinator,&nbsp;&nbsp; -French National Team - FFFA</li>
        <li>Head Football Coach,&nbsp;&nbsp; -Les Argonautes d'Aix en Provence, France </li>
        <li>Assistant Coach & Assistant Video Coord,&nbsp;&nbsp; -Kansas State University </li>
        <li>Student Coach & Graduate Assistant,&nbsp;&nbsp; -Kansas State University</li>
    </div> 

  </div>
    </section>


  );
}

export default About;