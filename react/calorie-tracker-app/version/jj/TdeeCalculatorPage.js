import React, { useState } from 'react';
import TdeeCalculator from './TdeeCalculator';  // The component for the TDEE calculator
import './TdeeCalculator.css';

function TdeeCalculatorPage() {
  return (
    <div>
      <h2>TDEE Calculator Page</h2>
      <TdeeCalculator />  {/* The actual calculator component */}
    </div>
  );
}

export default TdeeCalculatorPage;
