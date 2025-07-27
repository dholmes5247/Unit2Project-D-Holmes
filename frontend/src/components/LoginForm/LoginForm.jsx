import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';
import { AuthContext } from '../../context/AuthContext'; // Make sure this path matches your setup
import EditProfileForm from '../EditProfileForm/EditProfileForm'; // Adjust path if needed

export default function LoginForm() {
  const [form, setForm] = useState({ email: '', password: '' });
  const [err, setErr] = useState('');
  const [showEditForm, setShowEditForm] = useState(false);
  const [editingUser, setEditingUser] = useState(null);
  const { login } = useAuth();
  // const user = useContext(AuthContext); not using now
  const nav = useNavigate();

  const handle = (e) => setForm({ ...form, [e.target.name]: e.target.value });

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
      const message =
        error?.message?.includes('401') || error?.message?.includes('Unauthorized')
          ? 'Incorrect credentials. Please try again or sign up.'
          : 'Login failed. Please try again.';
      setErr(message);
    }
  };

const handleUpdateClick = async () => {
  try {
    const loggedInUser = await login(form); // Assuming login returns user data
    setEditingUser(loggedInUser); // Save user info for prefill
    setShowEditForm(true);
  } catch (error) {
    console.error('Update profile failed:', error); // Uses 'error'
    setErr('Unable to load profile for update. Please double-check credentials.');
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

      <button type="submit">Log In</button>

      <button
        type="button"
        onClick={handleUpdateClick}
        className="update-button"
      >
        ✏️ Update Info Instead
      </button>

      <p>
        Don’t have an account yet?{' '}
        <button
          type="button"
          onClick={() => nav('/signup')}
          className="link-button"
        >
          ✍️ Sign Up Instead
        </button>
      </p>
    </form>

    {/* ✅ Clean conditional */}
    {showEditForm && editingUser && (
      <>
        <p>You can update your profile details below.</p>
        <EditProfileForm user={editingUser} />
      </>
    )}
  </div>
);
}