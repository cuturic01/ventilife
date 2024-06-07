export interface Patient {
  id: string; // UUID represented as a string in TypeScript
  name: string;
  weight: number;
  conscious: boolean;
  pO2: number;
  pCO2: number;
  participationPercentage: number;
  tidalVolume: number;
  frequency: number;
  minuteVolume: number;
  pressure: number;
  resistance: number;
  gasFlow: number;
  compliance: number;
  FiO2: number;
  respiratorMode: string;
  volumeControlled: boolean;
}

const patients: Patient[] = [
  {
    id: '550e8400-e29b-41d4-a716-446655440000',
    name: 'Pera',
    weight: 70.5,
    conscious: true,
    pO2: 95.0,
    pCO2: 40.0,
    participationPercentage: 80.0,
    tidalVolume: 500.0,
    frequency: 12,
    minuteVolume: 6.0,
    pressure: 20.0,
    resistance: 5.0,
    gasFlow: 40.0,
    compliance: 0.05,
    FiO2: 0.21,
    respiratorMode: 'SIMV',
    volumeControlled: true
  },
  {
    id: '550e8400-e29b-41d4-a716-446655440001',
    name: 'Jovan',
    weight: 65.0,
    conscious: false,
    pO2: 85.0,
    pCO2: 45.0,
    participationPercentage: 60.0,
    tidalVolume: 450.0,
    frequency: 14,
    minuteVolume: 6.3,
    pressure: 22.0,
    resistance: 4.5,
    gasFlow: 38.0,
    compliance: 0.04,
    FiO2: 0.30,
    respiratorMode: 'SIMV',
    volumeControlled: false
  },
  {
    id: '550e8400-e29b-41d4-a716-446655440002',
    name: 'Marko',
    weight: 75.0,
    conscious: true,
    pO2: 90.0,
    pCO2: 38.0,
    participationPercentage: 70.0,
    tidalVolume: 520.0,
    frequency: 13,
    minuteVolume: 6.8,
    pressure: 18.0,
    resistance: 5.5,
    gasFlow: 42.0,
    compliance: 0.06,
    FiO2: 0.25,
    respiratorMode: 'SIMV',
    volumeControlled: true
  }
];

export default patients;


export interface StablePatientParams {
  patientId: string;
  tidalVolume: number;
  minuteVolume: number;
  pressure: number;
  gasFlow: number;
}

export interface ChangeRecord {
  patientId: string;
  deltaPO2: number;
  deltaPCO2: number;
  participationPercentage: number;
  chosenMode: string;
}

export interface RespiratorDecision {
  patientId: string;
  frequencyDecision: boolean;
  tidalVolumeDecision: boolean;
  pCO2Decision: boolean;
  pO2Decision: boolean;
  finalDecision: boolean;
}

export interface PatientData {
  patient: Patient;
  stablePatientParams: StablePatientParams;
  changeRecord: ChangeRecord;
  respiratorDecision: RespiratorDecision;
}

