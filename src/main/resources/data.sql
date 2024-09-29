CREATE TABLE Patient (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  date_of_birth DATE NOT NULL,
  gender VARCHAR(10),
  address VARCHAR(255),
  phone VARCHAR(50),
  email VARCHAR(100)
);

CREATE TABLE Doctor (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  specialty VARCHAR(100),
  phone VARCHAR(50)
);

CREATE TABLE Appointment (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  date DATE NOT NULL,
  reason VARCHAR(255),
  patient_id BIGINT,
  doctor_id BIGINT,
  CONSTRAINT fk_patient FOREIGN KEY (patient_id) REFERENCES Patient(id),
  CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES Doctor(id)
);

CREATE TABLE MedicalHistory (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  description TEXT NOT NULL,
  patient_id BIGINT,
  doctor_id BIGINT,
  lastUpdated DATE NOT NULL,
  CONSTRAINT fk_patient_history FOREIGN KEY (patient_id) REFERENCES Patient(id),
  CONSTRAINT fk_doctor_history FOREIGN KEY (doctor_id) REFERENCES Doctor(id)
);

CREATE TABLE Invoice (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  totalAmount DOUBLE NOT NULL,
  date DATE NOT NULL,
  patient_id BIGINT,
  appointment_id BIGINT,
  CONSTRAINT fk_patient_invoice FOREIGN KEY (patient_id) REFERENCES Patient(id),
  CONSTRAINT fk_appointment_invoice FOREIGN KEY (appointment_id) REFERENCES Appointment(id)
);

-- Insert data into Patient table
INSERT INTO Patient (name, date_of_birth, gender, address, phone, email) VALUES
('John Doe', '1985-04-12', 'Male', '123 Main St, Springfield', '555-1234', 'john.doe@example.com'),
('Jane Smith', '1990-08-25', 'Female', '456 Elm St, Springfield', '555-5678', 'jane.smith@example.com'),
('Emily Johnson', '1988-01-15', 'Female', '789 Maple St, Springfield', '555-8765', 'emily.johnson@example.com');

-- Insert data into Doctor table
INSERT INTO Doctor (name, specialty, phone) VALUES
('Dr. Alice Brown', 'Pediatrics', '555-1111'),
('Dr. Bob White', 'Cardiology', '555-2222'),
('Dr. Carol Green', 'Dermatology', '555-3333');

-- Insert data into Appointment table
INSERT INTO Appointment (date, reason, patient_id, doctor_id) VALUES
('2024-09-15', 'Routine check-up', 1, 1),
('2024-09-16', 'Skin rash', 3, 3),
('2024-09-17', 'Heart palpitations', 2, 2);

-- Insert data into MedicalHistory table
INSERT INTO MedicalHistory (description, patient_id, doctor_id, lastUpdated) VALUES
('Patient has a history of asthma. Prescribed inhaler.', 1, 1, '2024-09-14'),
('No significant medical history.', 2, 2, '2024-09-16'),
('Allergic to penicillin. Recommended allergy testing.', 3, 3, '2024-09-15');

-- Insert data into Invoice table
INSERT INTO Invoice (totalAmount, date, patient_id, appointment_id) VALUES
(150.00, '2024-09-15', 1, 1),
(200.00, '2024-09-16', 3, 2),
(250.00, '2024-09-17', 2, 3);
