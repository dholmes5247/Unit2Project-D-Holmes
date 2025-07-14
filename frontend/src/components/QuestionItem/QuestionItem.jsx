import React from 'react';
import './QuestionItem.css';

function QuestionItem({ question, onAnswer, isAnswered, userAnswer }) {
  return (
    <div className="question-item">
      <h3>{question.text}</h3>
      <div className="button-group">
        <button
          onClick={() => onAnswer(true)}
          disabled={isAnswered}
          className={userAnswer === true ? 'selected' : ''}
        >
          True
        </button>
        <button
          onClick={() => onAnswer(false)}
          disabled={isAnswered}
          className={userAnswer === false ? 'selected' : ''}
        >
          False
        </button>
      </div>
      {isAnswered && (
        <p className={userAnswer === question.answer ? 'correct' : 'incorrect'}>
          {userAnswer === question.answer ? 'CORRECT!' : 'INCORRECT.'}
        </p>
      )}
    </div>
  );
}

export default QuestionItem;
