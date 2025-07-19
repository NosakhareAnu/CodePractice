import React, { useState } from 'react';
import './App.css';

function App() {
  const [gender, setGender] = useState('');
  const [age, setAge] = useState('');
  const [weight, setWeight] = useState('');
  const [height, setHeight] = useState('');
  const [activityLevel, setActivityLevel] = useState('');
  const [tdee, setTdee] = useState(null); // State to store TDEE result

  // TDEE calculation logic
  const calculateTDEE = (e) => {
    e.preventDefault();

    if (!gender || !age || !weight || !height || !activityLevel) {
      alert('Please fill in all fields');
      return;
    }

    // Calculate BMR based on gender
    let bmr;
    if (gender === 'male') {
      bmr = 10 * weight + 6.25 * height - 5 * age + 5;
    } else if (gender === 'female') {
      bmr = 10 * weight + 6.25 * height - 5 * age - 161;
    }

    // Adjust BMR based on activity level
    let activityFactor;
    switch (activityLevel) {
      case 'sedentary':
        activityFactor = 1.2;
        break;
      case 'light':
        activityFactor = 1.375;
        break;
      case 'moderate':
        activityFactor = 1.55;
        break;
      case 'active':
        activityFactor = 1.725;
        break;
      case 'extra-active':
        activityFactor = 1.9;
        break;
      default:
        activityFactor = 1.2;
    }

    const tdeeResult = bmr * activityFactor;
    setTdee(tdeeResult); // Set the TDEE result to state
  };

  return (
    <div className="App">
      <nav className="nav">
        <div className="nav-left">TDEE Calculator</div>
        <div className="nav-right">by nosa</div>
      </nav>

      <div className="tdee-form">
        <form onSubmit={calculateTDEE}>
          {/* Gender Selection */}
          <div className="form-group gender-selection">
            <label>
              <input type="radio" name="gender" value="male" onChange={() => setGender('male')} /> Male
            </label>
            <label>
              <input type="radio" name="gender" value="female" onChange={() => setGender('female')} /> Female
            </label>
          </div>

          {/* Age */}
          <div className="form-group">
            <label htmlFor="age">Age:</label>
            <input type="number" id="age" name="age" value={age} onChange={(e) => setAge(e.target.value)} />
          </div>

          {/* Weight */}
          <div className="form-group">
            <label htmlFor="weight">Weight (kg):</label>
            <input type="number" id="weight" name="weight" value={weight} onChange={(e) => setWeight(e.target.value)} />
          </div>

          {/* Height */}
          <div className="form-group">
            <label htmlFor="height">Height (cm):</label>
            <input type="number" id="height" name="height" value={height} onChange={(e) => setHeight(e.target.value)} />
          </div>

          {/* Activity Level */}
          <div className="form-group">
            <label htmlFor="activity">Activity Level:</label>
            <select id="activity" name="activity" value={activityLevel} onChange={(e) => setActivityLevel(e.target.value)}>
              <option value="sedentary">Sedentary (little or no exercise)</option>
              <option value="light">Lightly active (light exercise/sports 1-3 days/week)</option>
              <option value="moderate">Moderately active (moderate exercise/sports 3-5 days/week)</option>
              <option value="active">Very active (hard exercise/sports 6-7 days/week)</option>
              <option value="extra-active">Super active (very hard exercise/sports & physical job)</option>
            </select>
          </div>

          {/* Calculate Button */}
          <button className="calculate-btn" type="submit">Calculate TDEE</button>
        </form>

        {/* Show TDEE Result */}
        {tdee && (
          <div className="tdee-result">
            <h3>Your TDEE is: {tdee.toFixed(2)} kcal/day</h3>
          </div>
        )}
      </div>
    </div>
  );
}

export default App;
