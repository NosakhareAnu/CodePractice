import React, { useState } from 'react';

function TdeeCalculator() {
  const [gender, setGender] = useState('');
  const [age, setAge] = useState('');
  const [weight, setWeight] = useState('');
  const [height, setHeight] = useState('');
  const [activityLevel, setActivityLevel] = useState('');
  const [tdee, setTdee] = useState(null);
  const [bmi, setBmi] = useState(null);
  const [weightStatus, setWeightStatus] = useState('');
  const [idealWeight, setIdealWeight] = useState(null);

  // Function to calculate TDEE and BMI
  const calculateTdee = () => {
    if (!gender || !age || !weight || !height || !activityLevel) {
      alert('Please fill in all fields');
      return;
    }

    // Calculate TDEE based on Mifflin-St Jeor Equation
    let bmr;
    if (gender === 'male') {
      bmr = 10 * weight + 6.25 * height - 5 * age + 5;
    } else if (gender === 'female') {
      bmr = 10 * weight + 6.25 * height - 5 * age - 161;
    } else {
      alert('Please select a valid gender');
      return;
    }

    // Adjust BMR based on activity level
    let tdeeValue;
    switch (activityLevel) {
      case 'sedentary':
        tdeeValue = bmr * 1.2;
        break;
      case 'lightlyActive':
        tdeeValue = bmr * 1.375;
        break;
      case 'moderatelyActive':
        tdeeValue = bmr * 1.55;
        break;
      case 'veryActive':
        tdeeValue = bmr * 1.725;
        break;
      case 'superActive':
        tdeeValue = bmr * 1.9;
        break;
      default:
        alert('Please select a valid activity level');
        return;
    }

    setTdee(tdeeValue);

    // Calculate BMI
    const heightInM = height / 100; // Convert height from cm to meters
    const bmiValue = weight / (heightInM * heightInM);
    setBmi(bmiValue);

    // Determine weight status based on BMI
    if (bmiValue < 18.5) {
      setWeightStatus('Underweight');
    } else if (bmiValue >= 18.5 && bmiValue <= 24.9) {
      setWeightStatus('Normal weight');
    } else if (bmiValue >= 25 && bmiValue <= 29.9) {
      setWeightStatus('Overweight');
    } else {
      setWeightStatus('Obese');
    }

    // Calculate ideal weight (Normal BMI range is 18.5 to 24.9)
    const idealWeightLower = 18.5 * (heightInM * heightInM);
    const idealWeightUpper = 24.9 * (heightInM * heightInM);
    setIdealWeight({ lower: idealWeightLower, upper: idealWeightUpper });
  };

  return (
    <div className="tdee-calculator">
      <h2>TDEE Calculator</h2>
      <form>
        <div>
          <label>Gender</label>
          <select value={gender} onChange={(e) => setGender(e.target.value)}>
            <option value="">Select</option>
            <option value="male">Male</option>
            <option value="female">Female</option>
          </select>
        </div>
        <div>
          <label>Age</label>
          <input
            type="number"
            value={age}
            onChange={(e) => setAge(e.target.value)}
            placeholder="Age"
          />
        </div>
        <div>
          <label>Weight (kg)</label>
          <input
            type="number"
            value={weight}
            onChange={(e) => setWeight(e.target.value)}
            placeholder="Weight"
          />
        </div>
        <div>
          <label>Height (cm)</label>
          <input
            type="number"
            value={height}
            onChange={(e) => setHeight(e.target.value)}
            placeholder="Height"
          />
        </div>
        <div>
          <label>Activity Level</label>
          <select
            value={activityLevel}
            onChange={(e) => setActivityLevel(e.target.value)}
          >
            <option value="">Select</option>
            <option value="sedentary">Sedentary</option>
            <option value="lightlyActive">Lightly Active</option>
            <option value="moderatelyActive">Moderately Active</option>
            <option value="veryActive">Very Active</option>
            <option value="superActive">Super Active</option>
          </select>
        </div>
        <button type="button" onClick={calculateTdee}>
          Calculate
        </button>
      </form>

      {tdee && (
        <div className="results">
          <h3>Results</h3>
          <p>TDEE: {tdee.toFixed(2)} calories/day</p>
          <p>BMI: {bmi.toFixed(2)}</p>
          <p>Weight Status: {weightStatus}</p>
          <p>
            Ideal Weight: {idealWeight.lower.toFixed(2)} -{' '}
            {idealWeight.upper.toFixed(2)} kg
          </p>
        </div>
      )}
    </div>
  );
}

export default TdeeCalculator;
