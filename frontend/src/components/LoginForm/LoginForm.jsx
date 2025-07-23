import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';

export default function LoginForm() {
  const [form, setForm] = useState({ email: '', password: '' });
  const [err, setErr] = useState('');
  const { login } = useAuth();
  const nav = useNavigate();

  const handle = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const resetForm = () => {
    setErr('');
    setForm({ email: '', password: '' });
  };

  const submit = async (e) => {
    e.preventDefault();
    try {
      await login(form);
      nav('/quiz');
    } catch (error) {
      // Optional: customize based on error status
      const message =
        error?.message?.includes('401') || error?.message?.includes('Unauthorized')
          ? 'Incorrect credentials. Please try again or sign up.'
          : 'Login failed. Please try again.';
      setErr(message);
    }
  };

  return (
    <div className="login-form-container">
      <form onSubmit={submit}>
        <h2>Log In</h2>

        {err && (
          <div className="login-error">
            <p style={{ color: 'red' }}>{err}</p>
            <button type="button" onClick={resetForm} className="retry-button">
              🔁 Try Again
            </button>
          </div>
        )}

        <label>
          Email
          <input
            name="email"
            type="email"
            required
            value={form.email}
            onChange={handle}
          />
        </label>
        <br />

        <label>
          Password
          <input
            name="password"
            type="password"
            required
            value={form.password}
            onChange={handle}
          />
        </label>
        <br />

        <button type="submit">Log In</button>

        <p title="Create an account to save progress and track your scores">
          Don't have an account yet?{' '}
          <button
            type="button"
            onClick={() => nav('/signup')}
            className="link-button"
            aria-label="Navigate to sign up"
          >
            ✍️ Sign Up Instead
          </button>
        </p>
      </form>
    </div>
  );
}



